package com.example.minty.controllers;

import com.example.minty.dtos.CurrencyResponse;
import com.example.minty.dtos.DollarsByDate;
import com.example.minty.dtos.RateDTO;
import com.example.minty.entities.Dollars;
import com.example.minty.services.CurrencyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/currency")
@CrossOrigin(origins = "https://dollarsapi.netlify.app")
public class CurrencyController {

    @Autowired
    private CurrencyServiceImpl currencyService;

    @GetMapping()
    public Map<String, CurrencyResponse> getCurrencies() {
        return currencyService.getCurrencies();
    }

    @PostMapping("/convert")
    public BigDecimal convertCurrency(@RequestBody RateDTO request) {
        return currencyService.convertRates
                (request.amount(), request.fromConvert(), request.toConvert());
    }

    @GetMapping("/dollars")
    public List<Dollars> getDollars() {
        return currencyService.getDollars();
    }

    @GetMapping("/dollars/yesterday")
    public List<DollarsByDate> getDollarsByDate() {
        return currencyService.getDollarsByDate();
    }
}
