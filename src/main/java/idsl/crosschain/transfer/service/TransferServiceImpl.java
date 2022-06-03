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
        String dest_tx_url = "http://localhost:8082/contract/status/set/" + destChainName + "/" + TxStatus.prepare;
        JSONObject jsonObject = restTemplate.postForObject(dest_tx_url, null, JSONObject.class);
        log.info("dest tx status: {}", jsonObject.getString("msg"));

        // notify status to relay chain
        NotifyRequest notifyRequest = new NotifyRequest("txId", destChainName, TxStatus.prepare.toString(), "proof");
        String notify_relay_url = "http://localhost:8082/transfer/status/notify";
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
        webSocketBroadcast(String.format("Notify Tx[%s] %s status[%s] success!", notifyRequest.getTxId(), notifyRequest.getChainName(), notifyRequest.getTxStatus()));

        return new JSONObject(Map.of("msg", String.format("Notify Tx[%s] %s status[%s] success!", notifyRequest.getTxId(), notifyRequest.getChainName(), notifyRequest.getTxStatus())));
    }

    private JSONObject chainBuilderSelector(String chainName) {

        JSONObject jsonObject = new JSONObject();
        if (chainName.equalsIgnoreCase("A")) {
            jsonObject.put("chainBuilder", "sourceChainBuilder");
            jsonObject.put("contractAddress", SOURCE_CONTRACT_ADDRESS);
            return jsonObject;
        } else if (chainName.equalsIgnoreCase("B")) {
            jsonObject.put("chainBuilder", "destinationChainBuilder");
            jsonObject.put("contractAddress", DESTINATION_CONTRACT_ADDRESS);
            return jsonObject;
        } else if (chainName.equalsIgnoreCase("Relay")) {
            jsonObject.put("chainBuilder", "relayChainBuilder");
            jsonObject.put("contractAddress", RELAY_CONTRACT_ADDRESS);
            return jsonObject;
        } else {
            jsonObject.put("chainBuilder", "quorumBuilder");
            jsonObject.put("contractAddress", RELAY_CONTRACT_ADDRESS);
            return jsonObject;
        }
    }

    private void webSocketBroadcast(String webSocketMsg) {
        try {
            Map<String, String> map = new HashMap<>(Map.of("msg", webSocketMsg));
            simpMessagingTemplate.convertAndSend("/topic/tx", map);
            log.info("broadcast ws msg: {}", webSocketMsg);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }
}
