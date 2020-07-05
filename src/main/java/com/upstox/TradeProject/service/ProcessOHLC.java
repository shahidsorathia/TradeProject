package com.upstox.TradeProject.service;

import com.upstox.TradeProject.bo.TradeBarBO;
import com.upstox.TradeProject.bo.TradeBo;
import com.upstox.TradeProject.bo.TradeOHLC;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class ProcessOHLC implements Callable<List<TradeOHLC>> {

    Logger logger = LogManager.getLogger(TradeServiceImpl.class);

    private final List<TradeBo> tradeList;
    private final int barNumber;
    private final String symbol;

    public ProcessOHLC(List<TradeBo> tradeList, TradeBarBO tradeBarBO) {
        this.tradeList = tradeList;
        this.barNumber = tradeBarBO.getBarNumber();
        this.symbol=tradeBarBO.getSymbol();
    }
/*
    compute ohlc.
    this logic is based on my assumptions
 */
    private List<TradeOHLC> computeOHLC(List<TradeBo> tradeList, int barNum) {
        double open = 0d;
        double close = 0d;
        double high = 0d;
        double low = 0d;
        double volume = 0d;
        List<TradeOHLC> list = new ArrayList<TradeOHLC>();
        try {

            if(tradeList==null)
            {
                TradeOHLC  tObj=new TradeOHLC("notify",symbol,barNum);
                list.add(tObj);
            }
            for (int i = 0; tradeList!=null && i <tradeList.size() ; i++) {
                TradeBo obj = tradeList.get(i);

                if (i == 0) {
                    open = obj.getP();
                    high = obj.getP();
                    low = obj.getP();
                }
                if (i == tradeList.size() - 1) {
                    close = obj.getP();
                }
                if (high < obj.getP()) {
                    high = obj.getP();
                }
                if (low > obj.getP()) {
                    low = obj.getP();
                }
                volume = (volume + obj.getQ()) / ((i + 1));
                TradeOHLC tObj = new TradeOHLC("notify", obj.getSym(), barNum, open, high, low, close, volume);
                list.add(tObj);

            }
        } catch (Exception e) {
            logger.error("Error in computing OHLC packets.. ", e);
        }
        logger.error(list.get(list.size()-1));
        return list;
    }

    @Override
    public List<TradeOHLC> call() throws Exception {

        return computeOHLC(tradeList, barNumber);
    }
}
