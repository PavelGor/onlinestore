package com.gordeev.onlinestore.service;

import com.gordeev.onlinestore.service.entity.ExchangeRate;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;

public class ExchangeRateService {
    private static final Logger LOG = LoggerFactory.getLogger(ExchangeRateService.class);
    private final static ObjectMapper mapper = new ObjectMapper();
    private Optional<Double> exchangeRate;
    private LocalDateTime expireTime;
    private int exchangeRateMaxLifeTime;
    private String urlForExchangeRate;

    public ExchangeRateService() {
        expireTime = LocalDateTime.now();

    }

    private void init(){
        exchangeRate = getExchangeRateFromBank();
    }

    public Optional<Double> getExchangeRate() {
        LocalDateTime time = LocalDateTime.now();
        if (time.isAfter(expireTime.plusHours(exchangeRateMaxLifeTime))
                || !exchangeRate.isPresent()) {
            exchangeRate = getExchangeRateFromBank();
            expireTime = time;
        }
        return exchangeRate;
    }

    private Optional<Double> getExchangeRateFromBank() {


        try {
            URL urlObject = new URL(urlForExchangeRate);
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlObject.openConnection();
            httpURLConnection.setRequestMethod("GET");

            BufferedReader inputStream = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

            ExchangeRate[] exchangeRates = mapper.readValue(inputStream, ExchangeRate[].class);

            for (ExchangeRate rate : exchangeRates) {
                if ("USD".equals(rate.getCcy()) && "UAH".equals(rate.getBaseCurrency())) {
                    return Optional.of(Double.parseDouble(rate.getSale()));
                }
            }

        } catch (IOException e) {
            LOG.error("Cannot get exchange rate from Bank", e);
        }

        return Optional.empty();
    }

    public void setExchangeRateMaxLifeTime(int exchangeRateMaxLifeTime) {
        this.exchangeRateMaxLifeTime = exchangeRateMaxLifeTime;
    }

    public void setUrlForExchangeRate(String urlForExchangeRate) {
        this.urlForExchangeRate = urlForExchangeRate;
    }
}
