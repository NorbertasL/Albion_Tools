package com.redspark.albiontools.herlpers;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ItemPriceRequest {


    ItemRequestCallback callerClass;

    //Default search values are pre-set.
    private LOCATION location = LOCATION.Caerleon;
    private Item.QUALITY quality = Item.QUALITY.UNKNOWN;
    private int enchant= 0;


    public ItemPriceRequest(ItemRequestCallback callerClass){
        if(!(callerClass instanceof ItemRequestCallback)){
            DebugLog.printError(this, "Class "+ callerClass.toString()
                    +" does not implement ItemRequestCallback");
        }else{
            this.callerClass = callerClass;
        }


    }

    private final String baseURL ="https://www.albion-online-data.com/api/v2/stats/";
    private final String requestType = "prices/";
    private final String enchantTag = "@";
    private final String locationTag = "?locations=";
    private final String qualityTag = "&qualities=";
    public void sendRequest(String itemID, int enchant){
        this.enchant =enchant;
        HttpClient client = HttpClient.newHttpClient();
        //String url = "https://www.albion-online-data.com/api/v2/stats/prices/"+itemID+"?locations="+location.name()+"&qualities=";
        //System.out.println("Sending request with this URL:");
        //System.out.println(url);
        HttpRequest request = HttpRequest.newBuilder().uri(buildUri(itemID)).build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(callerClass::jsonResponse)
                .join();
    }
    private URI buildUri(String itemID){
        String uri = baseURL;
        uri+= requestType;

        uri+=itemID;

        if(enchant != 0){
            uri+=enchantTag+enchant;
        }
        uri+=locationTag+location.getLocationString();
        uri+=qualityTag;
        if(quality != Item.QUALITY.UNKNOWN){
            uri+=quality.getQualityIndex();
        }
        URI returnURI = URI.create(uri);
        System.out.println("URI:"+returnURI);
        return returnURI;
    }

    public void setLocation(LOCATION location){
        this.location = location;
    }
    public LOCATION getLocation(){
        return location;
    }




    public enum LOCATION{
        Caerleon("Caerleon"),
        Thetford("Thetford"),
        Lymhurst("Lymhurst"),
        Bridgewatch("Bridgewatch"),
        Martlock("Martlock");

        private String string;
        LOCATION(String string){
            this.string = string;
        }
        public String getLocationString(){
            return string;
        }
    }



}
