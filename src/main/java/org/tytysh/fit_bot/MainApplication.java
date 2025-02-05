package org.tytysh.fit_bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.tytysh.fit_bot"})
public class MainApplication {

	public static void main(String [] args) {
		SpringApplication.run(MainApplication.class, args);
	}

}
