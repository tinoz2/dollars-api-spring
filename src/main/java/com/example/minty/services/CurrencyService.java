package com.example.minty.services;

import com.example.minty.dtos.CurrencyResponse;
import com.example.minty.dtos.DollarsByDate;
import com.example.minty.entities.Dollars;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface CurrencyService {

    Map<String, CurrencyResponse> getCurrencies();

    BigDecimal convertRates(BigDecimal amount, String fromConvert, String toConvert);

    List<Dollars> getDollars();

    List<DollarsByDate> getDollarsByDate();
}
