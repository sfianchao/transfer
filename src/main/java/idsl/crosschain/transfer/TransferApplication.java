package idsl.crosschain.transfer;

import idsl.crosschain.transfer.contract.Status;
import idsl.crosschain.transfer.model.QuorumInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.context.ApplicationContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
//@SpringBootApplication
@SpringBootApplication(exclude = {KafkaAutoConfiguration.class, DataSourceAutoConfiguration.class})
public class TransferApplication implements CommandLineRunner {

    @Autowired
    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(TransferApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        try {
            QuorumInfo quorumInfo = (QuorumInfo) applicationContext.getBean("destinationChainBuilder");

            // deploy contract
//            Status status = Status.deploy(quorumInfo.getQuorum(), quorumInfo.getCredentials(), quorumInfo.getGasProvider()).send();
//            log.info("[{}] deployed contract address: {}", LocalDateTime.now().format(dateTimeFormatter), status.getContractAddress());

            // block listen
//            EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST, "0x9868831d5c4154b70cc2713a9fbd1b59dda7e3bb");
//            quorum.ethLogFlowable(filter).subscribe(log -> System.out.println("transaction log: " + log.getData()));
//            proxy.modifiedEventFlowable(filter).subscribe(log -> System.out.println("transaction: " + log.newGreeting));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
