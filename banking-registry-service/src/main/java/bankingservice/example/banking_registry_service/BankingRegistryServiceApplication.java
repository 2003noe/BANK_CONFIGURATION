package bankingservice.example.banking_registry_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class BankingRegistryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingRegistryServiceApplication.class, args);
	}

}
