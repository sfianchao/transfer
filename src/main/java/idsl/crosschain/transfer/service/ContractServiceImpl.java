package idsl.crosschain.transfer.service;

import com.alibaba.fastjson2.JSONObject;
import idsl.crosschain.transfer.contract.TxStatus;
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
    private final StatusContractUtil statusContractUtil;

    @Autowired
    public ContractServiceImpl(ApplicationContext applicationContext,
                               StatusContractUtil statusContractUtil) {
        this.applicationContext = applicationContext;
        this.statusContractUtil = statusContractUtil;
    }

    @Override
    public JSONObject setTxStatus(String status) {
        return statusContractUtil.setTxStatus("destinationChainBuilder", DESTINATION_CONTRACT_ADDRESS, TxStatus.valueOf(status));
    }

    @Override
    public JSONObject getTxStatus() {
        return statusContractUtil.getTxStatus("destinationChainBuilder", DESTINATION_CONTRACT_ADDRESS);
    }
}
