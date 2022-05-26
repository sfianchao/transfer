package idsl.crosschain.transfer.service;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import idsl.crosschain.transfer.contract.Status;
import idsl.crosschain.transfer.dto.NotifyRequest;
import idsl.crosschain.transfer.dto.SendRequest;
import idsl.crosschain.transfer.model.QuorumInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

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
    private ApplicationContext applicationContext;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public JSONObject sendTx(SendRequest sendRequest) {

        QuorumInfo quorumInfo = (QuorumInfo) applicationContext.getBean("destinationChainBuilder");
        Status status = Status.load(DESTINATION_CONTRACT_ADDRESS, quorumInfo.getQuorum(), quorumInfo.getCredentials(), quorumInfo.getGasProvider());
        log.info("current contract address: {}", status.getContractAddress());

        try {
            Map<String, String> map = new HashMap<>(Map.of("msg", objectMapper.writeValueAsString(sendRequest)));
            simpMessagingTemplate.convertAndSend("/topic/tx", map);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }

        return new JSONObject(Map.of("msg", String.format("Transfer Tx[%s] success!", "id")));
    }

    @Override
    public JSONObject notifyTxState(NotifyRequest notifyRequest) {

        QuorumInfo quorumInfo = (QuorumInfo) applicationContext.getBean("relayChainBuilder");
        Status status = Status.load(RELAY_CONTRACT_ADDRESS, quorumInfo.getQuorum(), quorumInfo.getCredentials(), quorumInfo.getGasProvider());
        log.info("current contract address: {}", status.getContractAddress());

        try {
            Map<String, String> map = new HashMap<>(Map.of("msg", objectMapper.writeValueAsString(notifyRequest)));
            simpMessagingTemplate.convertAndSend("/topic/tx", map);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }

        return new JSONObject(Map.of("msg", String.format("notify status[%s] success!", notifyRequest.getTxStatus())));
    }
}
