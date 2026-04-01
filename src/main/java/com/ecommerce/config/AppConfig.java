package com.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan(basePackages = "com.ecommerce")
@PropertySource("classpath:application.properties")
public class AppConfig {
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfig() {
	    return new PropertySourcesPlaceholderConfigurer();
	}

}


//what it does 
//1- mark class as spring confrigration
//2-Enable component scanning for controller , service etc