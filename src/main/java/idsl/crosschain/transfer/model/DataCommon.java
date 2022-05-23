package idsl.crosschain.transfer.model;

import lombok.Data;

@Data
public class DataCommon {

    public NodeInfo source;

    public NodeInfo destination;

    public String txType;

    public String txContent;
}
