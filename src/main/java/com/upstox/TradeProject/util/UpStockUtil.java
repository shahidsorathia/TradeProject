package com.upstox.TradeProject.util;

public class UpStockUtil {

    public static  long convertUnixTimeToEpocTime(String unixTime)
    {
        return (Long.parseUnsignedLong(unixTime) / 1000000L);
    }
}
