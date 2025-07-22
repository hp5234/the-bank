package com.jeon.bank;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Slf4j
@EnableJpaAuditing
@SpringBootApplication
public class BankApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(BankApplication.class, args);
//        String[] iocNames = context.getBeanDefinitionNames();
//        for (String iocName : iocNames) {
//            log.debug("ioc name : {}", iocName);
//        }
    }

}
