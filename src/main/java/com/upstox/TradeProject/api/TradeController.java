package com.upstox.TradeProject.api;

import com.upstox.TradeProject.service.TradeService;
import com.upstox.TradeProject.service.TradeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("trade")
public class TradeController {

    @Autowired
    TradeService tradeService;

    @RequestMapping("/processTradeData")
    public String getTradeDataLineByLine(int numberOfThreads) throws ExecutionException, InterruptedException, TimeoutException {
        long time=Calendar.getInstance().getTimeInMillis();
        String result = tradeService.processTradeData(numberOfThreads);
        result = result + "\nTotal processing time(in ms) :"+ (Calendar.getInstance().getTimeInMillis()-time);
       // System.out.println("EndTime "+(Calendar.getInstance().getTimeInMillis()-time));
        return result;
    }
}
