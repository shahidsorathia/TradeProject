package com.upstox.TradeProject.bo;

import java.io.Serializable;
import java.sql.Timestamp;

public class TradeBo implements Serializable {

    private String sym;
    private String T;
    private double P;
    private double Q;
    private double TS;
    private String side;
    private String TS2;
    private long timeInMilli;
    public long getTimeInMilli() {
        return timeInMilli;
    }

    public void setTimeInMilli(long timeInMilli) {
        this.timeInMilli = timeInMilli;
    }

    public String getSym() {
        return sym;
    }

    public void setSym(String sym) {
        this.sym = sym;
    }

    public String getT() {
        return T;
    }

    public void setT(String t) {


        T = t;
        ;
    }

    public double getP() {
        return P;
    }

    public void setP(double p) {
        P = p;
    }

    public double getQ() {
        return Q;
    }

    public void setQ(double q) {
        Q = q;
    }

    public double getTS() {
        return TS;
    }

    public void setTS(double TS) {
        this.TS = TS;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getTS2() {
        return TS2;
    }

    public void setTS2(String TS2) {
        this.TS2 = TS2;
    }
}
