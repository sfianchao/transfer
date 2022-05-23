package idsl.crosschain.transfer.service;

import idsl.crosschain.transfer.dto.NotifyRequest;
import idsl.crosschain.transfer.dto.SendRequest;

public interface TransferService {

    String sendTx(SendRequest sendRequest);

    String notifyTxState(NotifyRequest notifyRequest);
}
