package idsl.crosschain.transfer.service;

import com.alibaba.fastjson2.JSONObject;
import idsl.crosschain.transfer.contract.Status;
import idsl.crosschain.transfer.model.QuorumInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class ContractServiceImpl implements ContractService {

    public DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    private final ApplicationContext applicationContext;

    @Autowired
    public ContractServiceImpl(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public JSONObject setTxStatus() {

        QuorumInfo quorumInfo = (QuorumInfo) applicationContext.getBean("sourceChainBuilder");
        Status status = Status.load("0x441245cdb936628d0a0b9f9398dd48656646b539", quorumInfo.getQuorum(), quorumInfo.getCredentials(), quorumInfo.getGasProvider());
        log.info("deployed contract address: {}", status.getContractAddress());

        try {
            status.setStatusToPrepare().send();
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", "set Tx status success!");

        return jsonObject;
    }

    @Override
    public Map<String, String> getTxStatus() {

        String currentStatus = null;

        QuorumInfo quorumInfo = (QuorumInfo) applicationContext.getBean("sourceChainBuilder");
        Status status = Status.load("0x441245cdb936628d0a0b9f9398dd48656646b539", quorumInfo.getQuorum(), quorumInfo.getCredentials(), quorumInfo.getGasProvider());
        log.info("deployed contract address: {}", status.getContractAddress());

        try {
            currentStatus = status.getCurrentStatus().send();
            System.out.println(currentStatus);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }

        Map<String, String> map = new HashMap<>();
        map.put("currentStatus", currentStatus);
        return map;
    }
}
