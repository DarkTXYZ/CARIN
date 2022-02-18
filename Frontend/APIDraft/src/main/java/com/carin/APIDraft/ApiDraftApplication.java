package com.carin.APIDraft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ApiDraftApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiDraftApplication.class, args);
    }

}
