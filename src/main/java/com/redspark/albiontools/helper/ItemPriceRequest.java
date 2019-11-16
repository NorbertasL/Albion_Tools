package com.redspark.albiontools.helper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.redspark.albiontools.Constants.LOCATION;
import com.redspark.albiontools.Constants.QUALITY;
import com.redspark.albiontools.helper.Interfaces.ItemRequestCallback;


/**
 * Class that does a asynchronous price request for an item
 * After it receives the data it does a callback to the caller class with the json response
 */

public class ItemPriceRequest {

    private ItemRequestCallback callerClass;

    //Default search values are pre-set.
    private LOCATION location = LOCATION.Caerleon;
    private QUALITY quality = QUALITY.UNKNOWN;
    private int enchant = 0;


    public ItemPriceRequest(ItemRequestCallback callerClass) {
        this.callerClass = callerClass;
    }

    public void sendRequest(String itemID, int enchant) {
        this.enchant = enchant;
        HttpClient client = HttpClient.newHttpClient();

        //Asynchronous data request
        HttpRequest request = HttpRequest.newBuilder().uri(buildUri(itemID)).build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(callerClass::jsonResponse)
                .join();
    }

    //Builds URI based on the pre-defined settings
    private URI buildUri(String itemID) {
        final String baseURL = "https://www.albion-online-data.com/api/v2/stats/";
        final String requestType = "prices/";
        final String enchantTag = "@";
        final String locationTag = "?locations=";
        final String qualityTag = "&qualities=";

        StringBuilder uri = new StringBuilder(baseURL);
        uri.append(requestType);
        uri.append(itemID);

        if (enchant != 0) {
            uri.append(enchantTag).append(enchant);
        }
        uri.append(locationTag).append(location.getLocationString());
        uri.append(qualityTag);
        if (quality != QUALITY.UNKNOWN) {
            uri.append(quality.getQualityIndex());
        }
        URI returnURI = URI.create(uri.toString());
        System.out.println("URI:" + returnURI);
        return returnURI;
    }

    public void setLocation(LOCATION location) {
        this.location = location;
    }

    public LOCATION getLocation() {
        return location;
    }




}
