package com.felix.os.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.felix.os.service.DBService;




@Configuration
@Profile("test")
public class TesteConfig {
	@Autowired
	private DBService dbService;

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String ddl;

	@Bean
	public boolean instanciaDB() {

	 if(ddl.equals("create")) {
		 this.dbService.instanciaDB();
	 }
	  return false; 
	 
	}
	}