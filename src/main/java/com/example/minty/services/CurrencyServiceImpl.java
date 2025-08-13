package com.example.minty.services;

import com.example.minty.dtos.CurrencyResponse;
import com.example.minty.dtos.DollarsByDate;
import com.example.minty.dtos.RateResult;
import com.example.minty.entities.Dollars;
import com.example.minty.exceptions.CurrencyNotFoundException;
import com.example.minty.exceptions.InvalidAmountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    @Value("${api_url}")
    private String API_URL;
    @Value("${api_dolar_url}")
    private String API_DOLAR_URL;
    @Value("${api_dolar_url_bydate}")
    private String API_DOLAR_URL_BYDATE;

    @Autowired
    private RestTemplate restTemplate;

    public Map<String, CurrencyResponse> getCurrencies() {
        return restTemplate.exchange
                (API_URL + "/currencies",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<Map<String, CurrencyResponse>>() {
                        }).getBody();

        // utilizamos exchange para poder tipar nuestro Map, ya que Jackson
        // pierde el valor de nuestro Map y nos crea uno nuevo sin valores ni tipados
        // el null seria lo que 'enviamos' pero al hacer GET no enviamos nada
        // exchange devuelve un ResponseEntity<T> para mayor control
        // por ende debemos usar .getBody()
    }

    public BigDecimal convertRates(BigDecimal amount, String fromConvert, String toConvert) {
        Set<String> setOfCurrencyCodes = this.getCurrencyCodes();

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new InvalidAmountException("Amount must be greater than zero");
        if (!setOfCurrencyCodes.contains(fromConvert))
            throw new CurrencyNotFoundException("Currency code '" + fromConvert + "' not found in available list");
        if (!setOfCurrencyCodes.contains(toConvert))
            throw new CurrencyNotFoundException("Currency code '" + toConvert + "' not found in available list");

        String urlToConvert = UriComponentsBuilder.fromUriString(API_URL + "/convert")
                .queryParam("from", fromConvert)
                .queryParam("to", toConvert)
                .queryParam("amount", amount)
                .toUriString();

        RateResult response = restTemplate.getForObject(urlToConvert, RateResult.class);
        return response != null ? response.result() : null;
    }

    private Set<String> getCurrencyCodes() {
        Map<String, CurrencyResponse> currencyCodes = this.getCurrencies();
        return currencyCodes.keySet();
    }

    public List<Dollars> getDollars() {
        return restTemplate.exchange(
                API_DOLAR_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Dollars>>() {
                }).getBody();
    }

    public List<DollarsByDate> getDollarsByDate() {
        final LocalDate yesterday = LocalDate.now().minusDays(1);
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        final String formattedDate = yesterday.format(formatter);

        return restTemplate.exchange(
                API_DOLAR_URL_BYDATE + formattedDate,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<DollarsByDate>>() {
                }).getBody();
    }
}