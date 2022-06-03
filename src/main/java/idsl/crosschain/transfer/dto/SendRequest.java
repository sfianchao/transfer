package idsl.crosschain.transfer.dto;

import idsl.crosschain.transfer.model.DataCommon;
import idsl.crosschain.transfer.model.RoutingCommon;
import lombok.Data;

@Data
public class SendRequest {

    public DataCommon dataCommon;

    public RoutingCommon routingCommon;

    public String txStatus;

    public String proof;
}
