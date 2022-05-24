package idsl.crosschain.transfer.model;

import lombok.Data;

@Data
public class RoutingCommon {

    private BridgeNode from;

    private BridgeNode to;

    private BridgeNode relayBridge;

    private String timeStamp;

    private String signature;
}
