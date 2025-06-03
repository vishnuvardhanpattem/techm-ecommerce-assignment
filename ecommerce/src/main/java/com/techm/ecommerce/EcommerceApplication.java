package com.techm.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class EcommerceApplication {

	public static void main(String[] args) {
//		 PasswordEncoder encoder = new BCryptPasswordEncoder();
//		 String rawPassword = "Admin@1234";
//		 String encodedPassword = encoder.encode(rawPassword);
//		 System.out.println("Hashed Password: " + encodedPassword);

		SpringApplication.run(EcommerceApplication.class, args);
	}

}
