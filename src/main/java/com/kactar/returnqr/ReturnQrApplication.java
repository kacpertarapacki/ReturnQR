package com.kactar.returnqr;

import com.kactar.returnqr.model.User;
import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReturnQrApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReturnQrApplication.class, args);
		System.out.println("Hello World from console!");

	}


}
