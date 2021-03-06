package com.andresantos.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.andresantos.cursomc.services.DBService;
import com.andresantos.cursomc.services.EmailService;
import com.andresantos.cursomc.services.SmtpEmailService;

@Configuration
@Profile("dev")
public class DevConfig {
	@Autowired
	
	private DBService dbService;
	
	@Value("${spring.jpa.hibernate.ddl-auto}") // pega o valor dessa chave no aplication-dev.properties
	private String strategy;
	
	@Bean
	public Boolean instantiateDatabase() throws ParseException {
		if(! "create".equals(strategy)) {
			return false;
		}
		dbService.instantiateTestDatabase();
		return true;
	}
	
	@Bean 
	public EmailService emailService() {
		return new SmtpEmailService();
	}
}
