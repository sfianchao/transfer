package idsl.crosschain.transfer.dto;

import lombok.Data;

@Data
public class NotifyRequest {

    public String txId;

    public String txStatus;

    public String proof;

}
