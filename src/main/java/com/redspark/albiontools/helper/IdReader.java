package com.redspark.albiontools.helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;


public class IdReader {
    private final static String ITEM_ID_URI = "https://raw.githubusercontent.com/broderickhyman/ao-bin-dumps/master/formatted/items.json";

    //Using HasMap for easy storage and to avoid duplicates
    private static HashMap<String, String> itemsHashMap;

    public static void request() {
        HttpClient client = HttpClient.newHttpClient();

        //Asynchronous HTTP request
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(ITEM_ID_URI)).build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(IdReader::parsData)
                .join();
    }

    private static Boolean parsData(String data) {
        try {
            JSONArray items = new JSONArray(data);

            HashMap<String, String> temp = new HashMap<>();

            //Looping trough the json and extracting ItemNames and ItemIDs
            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                JSONObject localizedNames;
                try {
                    localizedNames = item.getJSONObject("LocalizedNames");
                } catch (Exception e) {
                    System.out.println("LocalizedDescriptions is null for:" + item.getString("UniqueName"));
                    continue;
                }

                //Using only english names for now
                String name = localizedNames.getString("EN-US");

                //@Stuff after @ in the String is the enchant level
                //I don't want it in my item list, because i can add than manually when I need it.
                String id = item.getString("UniqueName").split("@")[0];

                temp.put(name.toLowerCase(), id);
            }
            itemsHashMap = temp;
        } catch (JSONException e) {
            System.out.println("Load failed...");
            System.out.println(data);
            e.printStackTrace();
            return false;
        }


        return true;
    }

    //printList methods is used for debugging
    @SuppressWarnings("unused")
    private static void printList() {
        itemsHashMap.forEach((key, value) -> System.out.println(key + " : " + value));
        System.out.println("HashSet list length:" + itemsHashMap.size());
    }

    //Returns item ID based on the ItemName
    public static String getItemID(String name) {
        return itemsHashMap.get(name.toLowerCase());
    }
}
