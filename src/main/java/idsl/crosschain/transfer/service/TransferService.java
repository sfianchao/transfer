package idsl.crosschain.transfer.service;

import com.alibaba.fastjson2.JSONObject;
import idsl.crosschain.transfer.dto.NotifyRequest;
import idsl.crosschain.transfer.dto.SendRequest;

public interface TransferService {

    JSONObject sendTx(SendRequest sendRequest);

    JSONObject notifyTxState(NotifyRequest notifyRequest);
}
