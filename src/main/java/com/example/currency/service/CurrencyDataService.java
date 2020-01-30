package com.example.currency.service;

import com.example.currency.model.CurrencyData;
import com.example.currency.repository.CurrencyDataRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class CurrencyDataService {

    @Value("${currency.api.base_URL}")
    private String service_URL;

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CurrencyDataRepo repo;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    ServletContext context;

    public boolean preapareData() throws IOException {
        logger.info("preparing data...");
        ResponseEntity<CurrencyData> result = restTemplate.getForEntity(service_URL, CurrencyData.class);
        logger.info(result.toString());
        String home = System.getProperty("user.home");
        String fileLocation = FilenameUtils.normalize(home + "/Desktop");
        String md5Hex = DigestUtils.md5Hex(mapper.writeValueAsString(result.getBody())).toUpperCase();
        String file = fileLocation+"/"+md5Hex+".json";
        logger.info("saving file to -----------"+file);
        mapper.writeValue(new File(file), result.getBody());
        if (repo.save(result.getBody()) != null) {
            return true;
        }
        return false;
    }

    public Set<String> getExchangeRatesDsc(boolean asc) {
        CurrencyData currencyData = repo.findAll().get(0);
        Map<String, Double> exchangeRates = currencyData.getRates();
        Set<String> sortedexchangeRates = asc ? new TreeSet<>() : new TreeSet<>(Collections.reverseOrder());
        sortedexchangeRates.addAll(exchangeRates.keySet());
        return sortedexchangeRates;
    }

    public List<Map.Entry<String, Double>> getMaxMinRates() {
        CurrencyData currencyData = repo.findAll().get(0);
        Map.Entry<String, Double> maxEntry = Collections.max(currencyData.getRates().entrySet(), Comparator.comparing(Map.Entry::getValue));
        Map.Entry<String, Double> minEntry = Collections.min(currencyData.getRates().entrySet(), Comparator.comparing(Map.Entry::getValue));
        List<Map.Entry<String, Double>> maxMinlist = new ArrayList<>();
        maxMinlist.add(maxEntry);
        maxMinlist.add(minEntry);
        return maxMinlist;
    }
}
