package idsl.crosschain.transfer.service;

import idsl.crosschain.transfer.contract.Status;
import idsl.crosschain.transfer.dto.NotifyRequest;
import idsl.crosschain.transfer.dto.SendRequest;
import idsl.crosschain.transfer.model.DataCommon;
import idsl.crosschain.transfer.model.QuorumInfo;
import idsl.crosschain.transfer.model.RoutingCommon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransferServiceImpl implements TransferService {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public String sendTx(SendRequest sendRequest) {

        QuorumInfo quorumInfo = (QuorumInfo) applicationContext.getBean("destinationChainBuilder");
        Status status = Status.load("0x1ee47e98db45bbcbc4a51b4048bad4a061f0f4c0", quorumInfo.getQuorum(), quorumInfo.getCredentials(), quorumInfo.getGasProvider());
        log.info("deployed contract address: {}", status.getContractAddress());


        if (!sendRequest.getTxStatus().isEmpty()) {

        }

        return null;
    }

    @Override
    public String notifyTxState(NotifyRequest notifyRequest) {
        return null;
    }
}
