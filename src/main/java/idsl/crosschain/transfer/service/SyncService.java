package idsl.crosschain.transfer.service;

import com.alibaba.fastjson2.JSONObject;

public interface SyncService {

    public JSONObject checkTxStatus();

    public JSONObject getTxStatus(String chainName);

}
