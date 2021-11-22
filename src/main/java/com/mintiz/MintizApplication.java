package com.mintiz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MintizApplication {

	public static void main(String[] args) {
		SpringApplication.run(MintizApplication.class, args);
	}

}
