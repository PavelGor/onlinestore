package com.gordeev.onlinestore.service;

public class ExchangeRate {
    private String ccy;
    private String base_ccy;
    private String buy;
    private String sale;

    public String getCcy() {
        return ccy;
    }

    public void setCcy(String ccy) {
        this.ccy = ccy;
    }

    public String getBase_ccy() {
        return base_ccy;
    }

    public void setBase_ccy(String base_ccy) {
        this.base_ccy = base_ccy;
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
        sb.append(", base_ccy='").append(base_ccy).append('\'');
        sb.append(", buy='").append(buy).append('\'');
        sb.append(", sale='").append(sale).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
