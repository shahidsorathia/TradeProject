package com.upstox.TradeProject.bo;

public class TradeOHLC {

    private String event;
    private String symbol;
    private int bar_num;
    private String o;
    private String h;
    private String l;
    private String c;
    private String volume;

    public TradeOHLC(String event, String symbol, int bar_num) {
        this.event = event;
        this.symbol = symbol;
        this.bar_num = bar_num;
    }

    public TradeOHLC(String event, String symbol, int bar_num, double o, double h, double l, double c, double volume) {
        this.event = event;
        this.symbol = symbol;
        this.bar_num = bar_num;

        if (o > 0)
            this.o = o + "";
        if (h > 0)
            this.h = h + "";
        if (l > 0)
            this.l = l + "";
        if (c > 0)
            this.c = c + "";
        if (volume > 0)
            this.volume = volume + "";
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getBar_num() {
        return bar_num;
    }

    public void setBar_num(int bar_num) {
        this.bar_num = bar_num;
    }

    public String getO() {
        return o;
    }

    public void setO(String o) {
        this.o = o;
    }

    public String getH() {
        return h;
    }

    public void setH(String h) {
        this.h = h;
    }

    public String getL() {
        return l;
    }

    public void setL(String l) {
        this.l = l;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {

        if (symbol == null || symbol=="") {

            return "TreadBar{" +
                    "event='" + event + '\'' +
                    ", bar_num=" + bar_num +
                    '}';
        }
        else if (o == null || o=="") {

            return "TreadBar{" +
                    "event='" + event + '\'' +
                    ", symbol='" + symbol + '\'' +
                    ", bar_num=" + bar_num +
                    '}';
        } else
            return "TreadBar{" +
                    "event='" + event + '\'' +
                    ", symbol='" + symbol + '\'' +
                    ", bar_num=" + bar_num +
                    ", o='" + o + '\'' +
                    ", h='" + h + '\'' +
                    ", l='" + l + '\'' +
                    ", c='" + c + '\'' +
                    ", volume='" + volume + '\'' +
                    '}';
    }
}
