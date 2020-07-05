package com.upstox.TradeProject.service;

import com.upstox.TradeProject.bo.TradeBo;

import java.util.List;

public interface TradeService {
    String processTradeData(int numberOfThreads);

    int processBarTrades(List<TradeBo> totalList, int numberOfThreads);
}
