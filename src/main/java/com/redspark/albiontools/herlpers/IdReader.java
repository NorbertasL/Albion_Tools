package com.redspark.albiontools.herlpers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;



public class IdReader {
    private static final String path = "src/com/redspark/albiontools/data/itemList.json";

    private static HashMap<String, String> itemsHashMap;

    public static void request(){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://raw.githubusercontent.com/broderickhyman/ao-bin-dumps/master/formatted/items.json")).build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(IdReader::parsData)
                .join();
    }

    private static Boolean parsData(String data){
        try{
            JSONArray items = new JSONArray(data);
            HashMap<String, String> temp = new HashMap<>();
            for(int i = 0; i < items.length(); i++){
                JSONObject item = items.getJSONObject(i);
                JSONObject localizedNames;
                try{
                    localizedNames = item.getJSONObject("LocalizedNames");
                }catch (Exception e){
                    System.out.println("LocalizedDescriptions is null for:"+item.getString("UniqueName"));
                    continue;
                }
                //String localizedNames = (String)item.get("LocalizedNames");
                //System.out.println(localizedNames);

                //System.out.println("Name:"+name.getString("EN-US"));
                String name = localizedNames.getString("EN-US");
                String id = item.getString("UniqueName").split("@")[0];
                //System.out.println("ID:"+id +"    Name:"+name);

                temp.put(name.toUpperCase(), id);
            }
            itemsHashMap = temp;
        }catch (JSONException e){
            System.out.println("Load failed...");
            System.out.println(data);
            System.out.println(e);
            return false;
        }



        return true;
    }

    public static void printList(){
        itemsHashMap.entrySet().forEach(entry->{
            System.out.println(entry.getKey()+ " : "+entry.getValue());
        });
        System.out.println("HashSet list lenght:" +itemsHashMap.size());
    }


    public static String getItemID(String name){
        return itemsHashMap.get(name);
    }
}
