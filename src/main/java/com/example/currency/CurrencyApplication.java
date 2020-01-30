package com.example.currency;

import com.example.currency.service.CurrencyDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@SpringBootApplication
public class CurrencyApplication {

    @Autowired
    private CurrencyDataService service;

    Logger logger = LoggerFactory.getLogger(getClass());

    public static void main(String[] args) {
        SpringApplication.run(CurrencyApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void applicationReady() {
        try {
            if (service.preapareData()) {
                logger.info("Latest Currency Rates Updated");
            } else {
                logger.info("Error while updating latest Currency Rates.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
