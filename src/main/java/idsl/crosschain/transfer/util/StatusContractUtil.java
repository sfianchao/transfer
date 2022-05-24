package idsl.crosschain.transfer.util;

import com.alibaba.fastjson2.JSONObject;
import idsl.crosschain.transfer.contract.Status;
import idsl.crosschain.transfer.contract.TxStatus;
import idsl.crosschain.transfer.model.QuorumInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Data
@EqualsAndHashCode(callSuper = false)
@Component
public class StatusContractUtil extends ContractUtil {

    public StatusContractUtil() {

    }

    public JSONObject setTxStatus(String chainBuilder, String contractAddress, TxStatus txStatus) {

        JSONObject jsonObject = new JSONObject();

        QuorumInfo quorumInfo = (QuorumInfo) applicationContext.getBean(chainBuilder);
        Status status = Status.load(contractAddress, quorumInfo.getQuorum(), quorumInfo.getCredentials(), quorumInfo.getGasProvider());
        log.info("current contract address: {}", status.getContractAddress());

        try {
            if (txStatus.equals(TxStatus.prepare)) {
                status.setStatusToPrepare().send();
                jsonObject.put("msg", String.format("set status[%s] success!", txStatus.toString()));
            } else if (txStatus.equals(TxStatus.commit)) {
                status.setStatusToCommit().send();
                jsonObject.put("msg", String.format("set status[%s] success!", txStatus.toString()));
            } else {
                log.info("tx status error!");
                jsonObject.put("msg", "tx status error!");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }

        return jsonObject;
    }

    public JSONObject getTxStatus(String chainBuilder, String contractAddress) {

        String currentStatus = null;

        QuorumInfo quorumInfo = (QuorumInfo) applicationContext.getBean(chainBuilder);
        Status status = Status.load(contractAddress, quorumInfo.getQuorum(), quorumInfo.getCredentials(), quorumInfo.getGasProvider());
        log.info("current contract address: {}", status.getContractAddress());

        try {
            currentStatus = status.getCurrentStatus().send();
            log.info("current status: {}", currentStatus);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }

        return new JSONObject(Map.of("currentStatus", currentStatus != null ? currentStatus : "unknown"));
    }
}
