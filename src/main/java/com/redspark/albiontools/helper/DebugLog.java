package com.redspark.albiontools.helper;

/**
 * Class to handle debug logs
 */

public class DebugLog {
    private static DEBUG_LEVEL locDebugLevel = DEBUG_LEVEL.ALL;

    static void printError(Object callerClass, String message){
        String tag = "ERROR from ";
        if(locDebugLevel==DEBUG_LEVEL.ALL || locDebugLevel == DEBUG_LEVEL.ERROR){
           System.out.println(tag+callerClass.getClass()+" :"+message);
        }
    }

    @SuppressWarnings("unused")
    public static void setDebugLevel(DEBUG_LEVEL debugLevel){
        locDebugLevel = debugLevel;
    }

    //Enum of debug levels
    @SuppressWarnings("unused ")
    enum DEBUG_LEVEL {
        ALL,
        ERROR,
        OFF,
        WARNING,
    }
}
