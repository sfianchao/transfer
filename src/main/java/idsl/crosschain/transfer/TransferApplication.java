package idsl.crosschain.transfer;

import idsl.crosschain.transfer.config.QuorumConfig;
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
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
//@SpringBootApplication
@SpringBootApplication(exclude = {KafkaAutoConfiguration.class, DataSourceAutoConfiguration.class})
//@ComponentScan(excludeFilters  = {@ComponentScan.Filter(
//        type = FilterType.ASSIGNABLE_TYPE, classes = {QuorumConfig.class})})
public class TransferApplication implements CommandLineRunner {

    @Autowired
    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(TransferApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    }
}
