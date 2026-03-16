package com.ecommerce.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.ecommerce")
public class AppConfig {

}


//what it does 
//1- mark class as spring confrigration
//2-Enable component scanning for controller , service etc