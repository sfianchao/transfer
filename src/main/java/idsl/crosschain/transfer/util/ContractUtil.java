package idsl.crosschain.transfer.util;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Data
@Component
public class ContractUtil {

    @Value("${contract.address.source}")
    public String SOURCE_CONTRACT_ADDRESS;

    @Value("${contract.address.relay}")
    public String RELAY_CONTRACT_ADDRESS;

    @Value("${contract.address.destination}")
    public String DESTINATION_CONTRACT_ADDRESS;

    @Autowired
    public ApplicationContext applicationContext;

    public void deploy() {

    }

    public void laod() {

    }

}
