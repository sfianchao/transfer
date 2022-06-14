package idsl.crosschain.transfer.service;

import com.alibaba.fastjson2.JSONObject;
import idsl.crosschain.transfer.util.StatusContractUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SyncServiceImpl implements SyncService {

//    @Value("${contract.address.source}")
//    private String SOURCE_CONTRACT_ADDRESS;

    @Value("${contract.address.relay}")
    private String RELAY_CONTRACT_ADDRESS;

//    @Value("${contract.address.destination}")
//    private String DESTINATION_CONTRACT_ADDRESS;

    @Autowired
    private StatusContractUtil statusContractUtil;

    @Override
    public JSONObject checkTxStatus() {
        return statusContractUtil.checkTxStatus("relayChainBuilder", RELAY_CONTRACT_ADDRESS);
    }

    @Override
    public JSONObject getTxStatus(String chainName) {
        return statusContractUtil.getTxStatus("relayChainBuilder", RELAY_CONTRACT_ADDRESS, chainName);
    }

}
