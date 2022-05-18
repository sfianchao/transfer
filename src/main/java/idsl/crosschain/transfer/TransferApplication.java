package idsl.crosschain.transfer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;

//@SpringBootApplication
@SpringBootApplication(exclude = {KafkaAutoConfiguration.class, DataSourceAutoConfiguration.class})
public class TransferApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(TransferApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("test");
    }
}
