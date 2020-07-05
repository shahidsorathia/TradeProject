package com.upstox.TradeProject.service;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

import com.upstox.TradeProject.bo.TradeBarBO;
import com.upstox.TradeProject.bo.TradeBo;
import com.upstox.TradeProject.bo.TradeOHLC;
import com.upstox.TradeProject.util.UpStockUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.stereotype.Service;

@Service
public class TradeServiceImpl implements  TradeService {

    Logger logger = LogManager.getLogger(TradeServiceImpl.class);


    /**
     * Method to get the trade data from the file
     *
     * @param numberOfThreads
     * @return
     */
    @Override
    public String processTradeData(int numberOfThreads) {

        StringBuffer result = new StringBuffer();
        List totalList = new ArrayList();
        long startTime = Calendar.getInstance().getTimeInMillis();
        Map<String, List<TradeBo>> matStock = new HashMap<String, List<TradeBo>>();
        BufferedReader reader = null;
        int count = 0;

        try {

            reader = new BufferedReader(new FileReader("trades.json"));

            String line = reader.readLine();
            Gson gson = new Gson();
            TradeBo trade = gson.fromJson(line, TradeBo.class);

            while (line != null) {
                gson = new Gson();
                trade = gson.fromJson(line, TradeBo.class);
                trade.setTimeInMilli(UpStockUtil.convertUnixTimeToEpocTime(trade.getTS2()));
                //trade.setTimeInMilli((Long.parseUnsignedLong(trade.getTS2()) / 1000000L));
                totalList.add(trade);
                // read next line
                line = reader.readLine();
            }


        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Error in reading trade data : ", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error("Error in reading trade data : ", e);

                }
            }
        }
        result.append("File Read time(in ms) : " + (Calendar.getInstance().getTimeInMillis() - startTime));
        startTime = Calendar.getInstance().getTimeInMillis();
        int barCount = processBarTrades(totalList, numberOfThreads);
        result.append("\nProcess trade time(in ms) : " + (Calendar.getInstance().getTimeInMillis() - startTime));
        return result.toString();
    }

    /**
     * Method to process the trades and generate the bar for each trade
     *
     * @param totalList
     * @param numberOfThreads
     */
    @Override
    public int processBarTrades(List<TradeBo> totalList, int numberOfThreads) {


        ExecutorService exu = Executors.newFixedThreadPool(numberOfThreads);

        //rounding off to epoc time previous second
        long startTimeOfBar = (long) 1000 * (totalList.get(0).getTimeInMilli() / 1000);
        long endTimeOfBar = startTimeOfBar;
        //rounding off to epoc time previous second and adding 1 sec for end of file case
        long endTime = (long) 1000 * (totalList.get(totalList.size() - 1).getTimeInMilli() / 1000) + 1000;
        int barNumber = 1;
        int totalBarCount = 0;
        int forCounter = 0;
        int tempCounter = 0;
        for (; endTimeOfBar < endTime; barNumber++) {
            startTimeOfBar = endTimeOfBar;
            endTimeOfBar = startTimeOfBar + 15000L;
            List tempLst = new ArrayList();
            Map<String, List<TradeBo>> matStock = new HashMap<String, List<TradeBo>>();
            for (; forCounter < totalList.size(); forCounter++) {
                TradeBo tr = totalList.get(forCounter);
                if (tr.getTimeInMilli() > startTimeOfBar && tr.getTimeInMilli() <= endTimeOfBar) {
                    tempLst.add(tr);
                    List<TradeBo> tradeBoList = null;
                    if (matStock.containsKey(tr.getSym())) {
                        tradeBoList = matStock.get(tr.getSym());
                    } else {
                        tradeBoList = new ArrayList<>();
                    }
                    tradeBoList.add(tr);
                    matStock.put(tr.getSym(), tradeBoList);
                } else { //
                    break;
                }
            }
            tempCounter = tempCounter + tempLst.size();
            TradeBarBO trBo = null;
            for (Map.Entry entry : matStock.entrySet()) {
                trBo = new TradeBarBO((String) entry.getKey(), barNumber, startTimeOfBar, endTimeOfBar);
                ProcessOHLC prOhlc = new ProcessOHLC((List<TradeBo>) entry.getValue(), trBo);
                Future<List<TradeOHLC>> future = exu.submit(prOhlc);
                totalBarCount++;
            }
            // empty bar number handling
            if (matStock.size() < 1) {
                trBo = new TradeBarBO("", barNumber, startTimeOfBar, endTimeOfBar);
                ProcessOHLC prOhlc = new ProcessOHLC(null, trBo);
                Future<List<TradeOHLC>> future = exu.submit(prOhlc);
                totalBarCount++;
            }

        }
        //  shut down executor
        exu.shutdown();
        //wait for all tasks to finish
        while (!exu.isTerminated()) {

        }
        return totalBarCount;
    }

}
