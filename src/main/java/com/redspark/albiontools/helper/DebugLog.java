package com.redspark.albiontools.helper;

/**
 * Class to handle debug logs
 */

public class DebugLog {
    private static DEBUG_LEVEL debugLevel = DEBUG_LEVEL.ALL;

    public static void printError(Object callerClass, String message){
        String tag = "ERROR from ";
        if(debugLevel==DEBUG_LEVEL.ALL || debugLevel == DEBUG_LEVEL.ERROR){
           System.out.println(tag+callerClass.getClass()+" :"+message);
        }
    }

    public static void setDebugLevel(DEBUG_LEVEL debugLevel){
        debugLevel = debugLevel;
    }

    //Enum of debug levels
    enum DEBUG_LEVEL{
        ALL,
        OFF,
        WARNING,
        ERROR,
    }
}
