package idsl.crosschain.transfer.model;

import lombok.Data;

@Data
public class RoutingCommon {

    private String txId;

    private NodeInfo fromBridge;

    private NodeInfo toBridge;

    private NodeInfo relayBridge;
}
