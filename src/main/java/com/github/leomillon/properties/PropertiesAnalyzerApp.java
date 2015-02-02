package com.github.leomillon.properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class PropertiesAnalyzerApp {

    public static void main(String[] args) {
        SpringApplication.run(PropertiesAnalyzerApp.class, args);
    }

}
