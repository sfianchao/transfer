package idsl.crosschain.transfer.dto;

import idsl.crosschain.transfer.model.DataCommon;
import idsl.crosschain.transfer.model.RoutingCommon;
import lombok.Data;

@Data
public class SendRequest {

    public String txStatus;

    public DataCommon dataCommon;

    public RoutingCommon routingCommon;

    public String proof;
}
