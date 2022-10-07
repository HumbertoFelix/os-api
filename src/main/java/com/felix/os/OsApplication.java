package com.felix.os;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(basePackages = "com.felix.os.domain")
@SpringBootApplication
public class OsApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(OsApplication.class, args);
	}

	
}

	
	

