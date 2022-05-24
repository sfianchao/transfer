package idsl.crosschain.transfer.model;

import lombok.Data;

@Data
public class DataCommon {

    private NodeInfo source;

    private NodeInfo destination;

    private String txType;

    private String txContent;

    private String timeStamp;

    private String signature;
}
