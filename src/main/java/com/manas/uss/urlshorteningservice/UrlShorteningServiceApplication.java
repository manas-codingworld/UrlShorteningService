package com.manas.uss.urlshorteningservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * 
 * @author manasranjan
 * start of springboot and scanning of base packages of component and entity
 *
 */
@SpringBootApplication
@ComponentScan(basePackages= {"com.manas.uss.*"})
@EntityScan(basePackages= {"com.manas.uss.datamodel"})
public class UrlShorteningServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrlShorteningServiceApplication.class, args);
	}


}
