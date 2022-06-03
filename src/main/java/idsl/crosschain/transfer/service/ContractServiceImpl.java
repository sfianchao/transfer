package idsl.crosschain.transfer.service;

import com.alibaba.fastjson2.JSONObject;
import idsl.crosschain.transfer.contract.TxStatus;
import idsl.crosschain.transfer.util.ContractUtil;
import idsl.crosschain.transfer.util.StatusContractUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class ContractServiceImpl implements ContractService {

    public DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    @Value("${contract.address.source}")
    private String SOURCE_CONTRACT_ADDRESS;

    @Value("${contract.address.relay}")
    private String RELAY_CONTRACT_ADDRESS;

    @Value("${contract.address.destination}")
    private String DESTINATION_CONTRACT_ADDRESS;

    private final ApplicationContext applicationContext;

    private final ContractUtil contractUtil;
    private final StatusContractUtil statusContractUtil;

    @Autowired
    public ContractServiceImpl(ApplicationContext applicationContext,
                               ContractUtil contractUtil,
                               StatusContractUtil statusContractUtil) {
        this.applicationContext = applicationContext;
        this.contractUtil = contractUtil;
        this.statusContractUtil = statusContractUtil;
    }

    @Override
    public JSONObject setTxStatus(String chainName, String status) {
        String chainBuilder = "quorumBuilder";
        String contractAddress = RELAY_CONTRACT_ADDRESS;
        try {
            JSONObject jsonObject = chainBuilderSelector(chainName);
            if (jsonObject != null) {
                chainBuilder = jsonObject.getString("chainBuilder");
                contractAddress = jsonObject.getString("contractAddress");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        log.info("[{}] contract address: {}", chainBuilder, contractAddress);
        return statusContractUtil.setTxStatus(chainBuilder, contractAddress, chainName, TxStatus.valueOf(status));
    }

    @Override
    public JSONObject getTxStatus(String chainName) {
        String chainBuilder = "quorumBuilder";
        String contractAddress = RELAY_CONTRACT_ADDRESS;
        try {
            JSONObject jsonObject = chainBuilderSelector(chainName);
            if (jsonObject != null) {
                chainBuilder = jsonObject.getString("chainBuilder");
                contractAddress = jsonObject.getString("contractAddress");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        log.info("[{}] contract address: {}", chainBuilder, contractAddress);
        return statusContractUtil.getTxStatus(chainBuilder, contractAddress, chainName);
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
}
