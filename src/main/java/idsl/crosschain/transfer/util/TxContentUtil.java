package idsl.crosschain.transfer.util;

import com.alibaba.fastjson2.JSONObject;
import idsl.crosschain.transfer.model.DataCommon;
import idsl.crosschain.transfer.model.RoutingCommon;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Data
@Component
public class TxContentUtil {

    public TxContentUtil() {
    }

    public JSONObject getResTxContent(DataCommon dataCommon, RoutingCommon routingCommon) {
        String res = routingCommon.getTo().getIp() + dataCommon.getTxContent();
        log.info("tx content response: {}", res);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", res);
        return jsonObject;
    }
}
