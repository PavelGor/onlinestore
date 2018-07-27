package com.gordeev.onlinestore.service;

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
    private int EXCHANGE_RATE_MAX_LIFE_TIME;
    private String URL_FOR_EXCHANGE_RATE;

    public ExchangeRateService() {
        expireTime = LocalDateTime.now();
        exchangeRate = getExchangeRateFromBank();
    }

    public Optional<Double> getExchangeRate() {
        LocalDateTime time = LocalDateTime.now();
        if (time.isAfter(expireTime.plusHours(EXCHANGE_RATE_MAX_LIFE_TIME))
                || !exchangeRate.isPresent()) {
            exchangeRate = getExchangeRateFromBank();
            expireTime = time;
        }
        return exchangeRate;
    }

    private Optional<Double> getExchangeRateFromBank() {


        try {
            URL urlObject = new URL(URL_FOR_EXCHANGE_RATE);
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlObject.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");

            BufferedReader inputStream = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

            ExchangeRate[] exchangeRates = mapper.readValue(inputStream, ExchangeRate[].class);

            for (ExchangeRate rate : exchangeRates) {
                if ("USD".equals(rate.getCcy()) && "UAH".equals(rate.getBase_ccy())) {
                    return Optional.of(Double.parseDouble(rate.getSale()));
                }
            }

        } catch (IOException e) {
            LOG.error("Cannot get exchange rate from Bank", e);
        }

        return Optional.empty();
    }

    public void setEXCHANGE_RATE_MAX_LIFE_TIME(int EXCHANGE_RATE_MAX_LIFE_TIME) {
        this.EXCHANGE_RATE_MAX_LIFE_TIME = EXCHANGE_RATE_MAX_LIFE_TIME;
    }
}
