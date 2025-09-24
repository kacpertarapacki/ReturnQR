package com.kactar.returnqr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ReturnQrApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReturnQrApplication.class, args);
	}


}
