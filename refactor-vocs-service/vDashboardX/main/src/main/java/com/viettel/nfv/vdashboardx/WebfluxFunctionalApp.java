package com.viettel.nfv.vdashboardx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.viettel.nfv.vdashboardx.*" })
public class WebfluxFunctionalApp {

	private static final Logger logger = LoggerFactory.getLogger(WebfluxFunctionalApp.class.getName());

	public static void main(String[] args) {
		logger.info("Start webflux application.");
		SpringApplication app = new SpringApplication(WebfluxFunctionalApp.class);
		app.run(args);
	}
}
