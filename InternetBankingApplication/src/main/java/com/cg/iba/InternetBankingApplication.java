package com.cg.iba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/*(scanBasePackages={
        "com.cg.iba.entities", "com.cg.iba.service","com.cg.iba.repository","com.cg.iba.exception","com.cg.iba.controller"}
)*/
@SpringBootApplication
public class InternetBankingApplication {
 
    public static void main(String[] args) {
        SpringApplication.run(InternetBankingApplication.class, args); 
    }

}
