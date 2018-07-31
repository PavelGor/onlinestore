package com.gordeev.onlinestore.service.entity;

import org.codehaus.jackson.annotate.JsonProperty;

public class ExchangeRate {
    private String ccy;
    @JsonProperty("base_ccy")
    private String baseCurrency;
    private String buy;
    private String sale;

    public String getCcy() {
        return ccy;
    }

    public void setCcy(String ccy) {
        this.ccy = ccy;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public String getBuy() {
        return buy;
    }

    public void setBuy(String buy) {
        this.buy = buy;
    }

    public String getSale() {
        return sale;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ExchangeRate{");
        sb.append("ccy='").append(ccy).append('\'');
        sb.append(", baseCurrency='").append(baseCurrency).append('\'');
        sb.append(", buy='").append(buy).append('\'');
        sb.append(", sale='").append(sale).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
