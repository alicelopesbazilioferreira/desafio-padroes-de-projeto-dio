package one.digitalinnovation.lppsp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class LabPadroesProjetoSpringPraticaApplication {

	public static void main(String[] args) {
		SpringApplication.run(LabPadroesProjetoSpringPraticaApplication.class, args);
	}

}
