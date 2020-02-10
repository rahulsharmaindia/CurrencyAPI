package com.example.currency.controller;

import com.example.currency.service.CurrencyDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class CurrencyController {

    @Autowired
    CurrencyDataService service;

    /**
     * Get the currency with exRates in asc or dsc order on the basis of parameter value "asc" or "dsc".
     * @param sort
     * @return
     */
    @GetMapping("/rates")
    public ResponseEntity<Set<String>> getExchangeRates(@RequestParam String sort) {
        return new ResponseEntity<>(service.getExchangeRatesDsc("asc".equals(sort) ? true : false), HttpStatus.OK);
    }

    /**
     * Fetches the min and max rates currency
     * @return
     */
    @GetMapping("/rates/minmax")
    public ResponseEntity<List<Map.Entry<String, Double>>> getMaxMinRates() {
        return new ResponseEntity<>(service.getMaxMinRates(), HttpStatus.OK);
    }
}
