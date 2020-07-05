package com.upstox.TradeProject.bo;

import java.io.Serializable;

public class TradeBarBO implements Serializable {

    private String symbol;
    int barNumber;
    private long startTime;
    private long endTime;

    public TradeBarBO(String symbol, int barNumber, long startTime, long endTime) {
        this.symbol = symbol;
        this.barNumber = barNumber;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getBarNumber() {
        return barNumber;
    }

    public void setBarNumber(int barNumber) {
        this.barNumber = barNumber;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}
