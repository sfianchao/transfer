package idsl.crosschain.transfer.service;

import com.alibaba.fastjson2.JSONObject;

import java.util.Map;

public interface ContractService {

    JSONObject setTxStatus();

    Map<String, String> getTxStatus();
}
