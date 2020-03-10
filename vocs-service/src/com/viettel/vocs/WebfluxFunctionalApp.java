package com.viettel.vocs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@PropertySource("classpath:application.properties")
public class WebfluxFunctionalApp {
	
	private static Logger logger = (Logger) LogManager.getLogger(WebfluxFunctionalApp.class);
	
	public static void main(String[] args) throws Exception {
		logger.info("Start webflux application.");
		SpringApplication app = new SpringApplication(WebfluxFunctionalApp.class);
		app.run(args);
	}
}
