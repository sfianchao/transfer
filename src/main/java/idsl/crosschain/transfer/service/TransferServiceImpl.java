package idsl.crosschain.transfer.service;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import idsl.crosschain.transfer.contract.Status;
import idsl.crosschain.transfer.contract.TxStatus;
import idsl.crosschain.transfer.dto.NotifyRequest;
import idsl.crosschain.transfer.dto.SendRequest;
import idsl.crosschain.transfer.model.QuorumInfo;
import idsl.crosschain.transfer.util.StatusContractUtil;
import idsl.crosschain.transfer.util.TxContentUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class TransferServiceImpl implements TransferService {

    @Value("${contract.address.source}")
    private String SOURCE_CONTRACT_ADDRESS;

    @Value("${contract.address.relay}")
    private String RELAY_CONTRACT_ADDRESS;

    @Value("${contract.address.destination}")
    private String DESTINATION_CONTRACT_ADDRESS;

    private String deployServicePort = "9191";
    private String routingServicePort = "9292";
    private String transferServicePort = "9393";

    private String srcIDSLInternalIp = "http://192.168.66.73";
    private String relayIDSLInternalIp = "http://192.168.66.74";
    private String destIDSLInternalIp = "http://192.168.66.75";

    private String srcIp = "http://140.118.9.225";
    private String relayIp = "http://140.118.9.226";
    private String destIp = "http://140.118.9.227";

    @Autowired
    private StatusContractUtil statusContractUtil;

    @Autowired
    private TxContentUtil txContentUtil;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public JSONObject sendTx(SendRequest sendRequest) {

        String destChainName = sendRequest.getRoutingCommon().getTo().getChainName();
        JSONObject chainBuilder = chainBuilderSelector(destChainName);

        QuorumInfo quorumInfo = (QuorumInfo) applicationContext.getBean(chainBuilder.getString("chainBuilder"));
        Status status = Status.load(chainBuilder.getString("contractAddress"), quorumInfo.getQuorum(), quorumInfo.getCredentials(), quorumInfo.getGasProvider());
        log.info("[{}] contract address: {}", chainBuilder.getString("chainBuilder"), status.getContractAddress());

        // broadcast tx send msg to ws
        webSocketBroadcast(String.format("Transfer Tx[%s] success!", "TxId"));

        // set dest tx status to "prepare"
        String dest_tx_url = srcIp + ":" + transferServicePort + "/contract/status/set/" + destChainName + "/" + TxStatus.prepare;
        JSONObject jsonObject = restTemplate.postForObject(dest_tx_url, null, JSONObject.class);
        log.info("dest tx status: {}", jsonObject.getString("msg"));

        // notify status to relay chain
        NotifyRequest notifyRequest = new NotifyRequest("txId", destChainName, TxStatus.prepare.toString(), "proof");
        String notify_relay_url = srcIp + ":" + transferServicePort + "/transfer/status/notify";
        JSONObject relayTxStatus = restTemplate.postForObject(notify_relay_url, notifyRequest, JSONObject.class);
        log.info("[relay] {} tx status: {}", destChainName, relayTxStatus.getString("msg"));

        return txContentUtil.getResTxContent(sendRequest.getDataCommon(), sendRequest.getRoutingCommon());
    }

    @Override
    public JSONObject notifyTxStatus(NotifyRequest notifyRequest) {

        QuorumInfo quorumInfo = (QuorumInfo) applicationContext.getBean("relayChainBuilder");
        Status status = Status.load(RELAY_CONTRACT_ADDRESS, quorumInfo.getQuorum(), quorumInfo.getCredentials(), quorumInfo.getGasProvider());
        log.info("[{}] contract address: {}", "relayChainBuilder", status.getContractAddress());

        // set src tx status to "prepare"
        JSONObject jsonObject = statusContractUtil.setTxStatus("relayChainBuilder", RELAY_CONTRACT_ADDRESS, notifyRequest.getChainName(), TxStatus.valueOf(notifyRequest.getTxStatus()));

        // broadcast tx notify msg to ws
        String msg = String.format("Notify Tx[%s] %s status[%s] success!", notifyRequest.getTxId(), notifyRequest.getChainName(), notifyRequest.getTxStatus());
        webSocketBroadcast(msg);

        JSONObject res = new JSONObject();
        jsonObject.put("msg", msg);
        return res;
    }

    private JSONObject chainBuilderSelector(String chainName) {

        JSONObject jsonObject = new JSONObject();
        if (chainName.equalsIgnoreCase("src")) {
            jsonObject.put("chainBuilder", "sourceChainBuilder");
            jsonObject.put("contractAddress", SOURCE_CONTRACT_ADDRESS);
            return jsonObject;
        } else if (chainName.equalsIgnoreCase("dest")) {
            jsonObject.put("chainBuilder", "destinationChainBuilder");
            jsonObject.put("contractAddress", DESTINATION_CONTRACT_ADDRESS);
            return jsonObject;
        } else if (chainName.equalsIgnoreCase("relay")) {
            jsonObject.put("chainBuilder", "relayChainBuilder");
            jsonObject.put("contractAddress", RELAY_CONTRACT_ADDRESS);
            return jsonObject;
        } else {
            return null;
        }
    }

    private void webSocketBroadcast(String webSocketMsg) {
        try {
            Map<String, String> msg = new HashMap<>();
            msg.put("msg", webSocketMsg);
//            Map<String, String> map = new HashMap<>(Map.of("msg", webSocketMsg));
            simpMessagingTemplate.convertAndSend("/topic/tx", msg);
            log.info("broadcast ws msg: {}", webSocketMsg);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }
}
