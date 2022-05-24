package idsl.crosschain.transfer.service;

import com.alibaba.fastjson2.JSONObject;

public interface ContractService {

    JSONObject setTxStatus(String string);

    JSONObject getTxStatus();
}
