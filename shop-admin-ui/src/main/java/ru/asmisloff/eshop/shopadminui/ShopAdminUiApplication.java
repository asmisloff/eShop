package ru.asmisloff.eshop.shopadminui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("ru.asmisloff.eshop.*")
@EntityScan("ru.asmisloff.eshop.shopdatabase.entities")
@EnableJpaRepositories("ru.asmisloff.eshop.shopdatabase.repositories")
public class ShopAdminUiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopAdminUiApplication.class, args);
	}

}
