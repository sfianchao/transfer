package idsl.crosschain.transfer.util;

import com.alibaba.fastjson2.JSONObject;
import idsl.crosschain.transfer.contract.Status;
import idsl.crosschain.transfer.contract.TxStatus;
import idsl.crosschain.transfer.model.QuorumInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;

@Slf4j
@Data
@EqualsAndHashCode(callSuper = false)
@Component
public class StatusContractUtil extends ContractUtil {

    public StatusContractUtil() {

    }

    @Override
    public JSONObject deploy() {
        return null;
    }

    @Override
    public JSONObject load() {
        return null;
    }


    public JSONObject setTxStatus(String chainBuilder, String contractAddress, String chainName, TxStatus txStatus) {

        JSONObject jsonObject = new JSONObject();

        QuorumInfo quorumInfo = (QuorumInfo) applicationContext.getBean(chainBuilder);
        quorumInfo.setGasProvider(new StaticGasProvider(BigInteger.ZERO, DefaultGasProvider.GAS_LIMIT));

        TransactionManager transactionManager = new RawTransactionManager(
                quorumInfo.getQuorum(), quorumInfo.getCredentials(), chainId);
        Status status = Status.load(contractAddress, quorumInfo.getQuorum(), transactionManager, quorumInfo.getGasProvider());
        log.info("current contract address: {}", status.getContractAddress());

        try {
            if (txStatus.equals(TxStatus.prepare)) {
                status.setStatusToPrepare("txId", chainName).send();
                jsonObject.put("msg", String.format("set status[%s] success!", txStatus));
            } else if (txStatus.equals(TxStatus.commit)) {
                status.setStatusToCommit("txId", chainName).send();
                jsonObject.put("msg", String.format("set status[%s] success!", txStatus));
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

    public JSONObject getTxStatus(String chainBuilder, String contractAddress, String chainName) {

        String currentStatus = null;

        QuorumInfo quorumInfo = (QuorumInfo) applicationContext.getBean(chainBuilder);
        quorumInfo.setGasProvider(new StaticGasProvider(BigInteger.ZERO, DefaultGasProvider.GAS_LIMIT));

        TransactionManager transactionManager = new RawTransactionManager(
                quorumInfo.getQuorum(), quorumInfo.getCredentials(), chainId);
        Status status = Status.load(contractAddress, quorumInfo.getQuorum(), transactionManager, quorumInfo.getGasProvider());
        log.info("[{}] contract address: {}", chainBuilder, status.getContractAddress());

        try {
            currentStatus = status.getCurrentStatus(chainName).send();
            log.info("{} current status: {}", chainName, currentStatus);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("txStatus", currentStatus != null ? currentStatus : "unknown");
        jsonObject.put("msg", String.format("current status: %s", currentStatus != null ? currentStatus : "unknown"));

        return jsonObject;
    }

    public JSONObject checkTxStatus(String chainBuilder, String contractAddress) {

        JSONObject jsonObject = new JSONObject();

        QuorumInfo quorumInfo = (QuorumInfo) applicationContext.getBean(chainBuilder);
        quorumInfo.setGasProvider(new StaticGasProvider(BigInteger.ZERO, DefaultGasProvider.GAS_LIMIT));

        TransactionManager transactionManager = new RawTransactionManager(
                quorumInfo.getQuorum(), quorumInfo.getCredentials(), chainId);
        Status status = Status.load(contractAddress, quorumInfo.getQuorum(), transactionManager, quorumInfo.getGasProvider());
        log.info("[{}] contract address: {}", chainBuilder, status.getContractAddress());

        try {
            status.checkTxStatus("txId").send();
            log.info("check tx status success!");
            jsonObject.put("msg", "check tx status success!");
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            jsonObject.put("msg", "status error!");
        }

        return jsonObject;
    }
}
