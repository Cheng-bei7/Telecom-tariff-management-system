package sale.ljw.electronictariffmanagementsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("sale.ljw.electronictariffmanagementsystem.backend.dao")
@PropertySource(value={"classpath:/token.properties"})
public class ElectronicTariffManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElectronicTariffManagementSystemApplication.class, args);
    }

}
