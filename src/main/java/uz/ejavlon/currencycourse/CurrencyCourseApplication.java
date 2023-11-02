package uz.ejavlon.currencycourse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CurrencyCourseApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyCourseApplication.class, args);
	}

}
