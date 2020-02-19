package org.hqj.extensible.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ExtensibleApplication {

    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(ExtensibleApplication.class, args);

        System.out.println("Server is up and running well!!");
    }
}