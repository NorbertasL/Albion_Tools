package com.redspark.albiontools.pricer;

import com.redspark.albiontools.AlbionTools;
import com.redspark.albiontools.helper.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ItemPricer implements ItemRequestCallback {
    private Scanner scanner;
    private ItemPriceRequest priceRequestClass;

    @SuppressWarnings("FieldCanBeLocal")
    private final String QUALITY_KEY = "quality";
    @SuppressWarnings("FieldCanBeLocal")
    private final String SELL_KEY = "sell_price_min";
    @SuppressWarnings("FieldCanBeLocal")
    private final String BUY_KEY = "buy_price_max";
    @SuppressWarnings("FieldCanBeLocal")
    private final String SELL_DATE_KEY = "sell_price_min_date";
    @SuppressWarnings("FieldCanBeLocal")
    private final String BUY_DATE_KEY = "buy_price_max_date";
    @SuppressWarnings("FieldCanBeLocal")
    private final String LOCATION_KEY = "city";

    private Item currentWorkingItem;

    private List<Item> itemList = new ArrayList<>();

    public ItemPricer(Scanner scanner){

        priceRequestClass = new ItemPriceRequest(this);
        ImageScanner imageScanner = new ImageScanner();

        this.scanner = scanner;
        System.out.println("Welcome to ItemPricer");
        System.out.println("For a list of commands type -h or help");
        System.out.println("To go back use -b or -back");
        System.out.println("To close application use -e or -exit");

        //User cmd interface loop
        boolean run = true;
        while(run){
            System.out.println("Select option");
            scanner.reset();
            String input = scanner.nextLine();
            if(input.length() == 0 || (input.length() ==2 && input.charAt(0)=='@')){
                System.out.println("Scanning screen for item");
                String item = imageScanner.getTitle();
                if(item.equalsIgnoreCase("none")){
                    System.out.println("No Item Found!");
                }else {
                    int ench = input.length()>1 ? Integer.parseInt(""+ input.charAt(1)) : 0;
                    System.out.println("Item Found:"+item + " @"+ ench);
                    getPrice(item, ench);

                }

            //command inputs start with '-'
            }else if(input.charAt(0)=='-'){

                switch (input.split(" ")[0]){
                    case "-e": case "-exit":
                        run = false;
                        AlbionTools.stopApp();
                        break;

                    case "-b": case "-back":
                        run = false;
                        break;

                    case "-set":
                        String splitInput[] = input.split(" ");
                        if(splitInput.length < 2){
                            System.out.println("Missing parameter");
                            break;
                        }
                        switch (input.split(" ")[1]){
                            case "loc":
                                setLocation();
                                break;
                            case "ench":
                                //TODO setup a default enchant level
                                break;
                            default:
                                System.out.println("Unknown parameter:"+input.split(" ")[1]);
                        }
                        break;


                    default:
                        System.out.println("Unknown command");
                        System.out.println("Use -h or -help for a list of commands");
                        System.out.println("Use -e or -exit to exit");
                }

            //Normal input
            }else {
                String [] splitInput = input.split("@");
                if(splitInput.length >1){
                    getPrice(splitInput[0], Integer.parseInt(splitInput[1]));
                }else{
                    getPrice(splitInput[0], 0);
                }

            }

        }

        System.out.println("Thank you for using ItemPricer");

    }
    private void setLocation(){

        System.out.println("Location choices are:");
        int index = 1;
        for(ItemPriceRequest.LOCATION loc : ItemPriceRequest.LOCATION.values()){
            System.out.println(index++ + " : "+ loc);
        }

        int choice;
        try {
            choice = scanner.nextInt();
        }catch (InputMismatchException e){
            System.out.println("Bad input");
            setLocation();
            return;
        }

        if(choice > ItemPriceRequest.LOCATION.values().length || choice <=0){
            System.out.println("Bad index");
            setLocation();
            return;
        }else{
            priceRequestClass.setLocation(ItemPriceRequest.LOCATION.values()[choice-1]);
        }

        System.out.println("Location set to "+ priceRequestClass.getLocation().getLocationString());

    }
    private void getPrice(String itemName, int enchant){
        String id = getID(itemName);

        currentWorkingItem = new Item("itemName",id,  enchant);
        priceRequestClass.sendRequest(id, enchant);
    }
    private String getID(String itemName){

        return IdReader.getItemID(itemName);
    }

    private void formatJson(String json){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd[ ]['T']HH:mm:ss");
        JSONArray items = new JSONArray(json);
        for(int i = 0; i < items.length(); i++){
            JSONObject item = items.getJSONObject(i);

            currentWorkingItem = new Item("Item");

            currentWorkingItem.setSell(item.getInt(SELL_KEY));
            currentWorkingItem.setBuy(item.getInt(BUY_KEY));

            currentWorkingItem.setSellDate(LocalDateTime.parse(item.getString(SELL_DATE_KEY), formatter));
            currentWorkingItem.setBuyDate(LocalDateTime.parse(item.getString(BUY_DATE_KEY), formatter));

            currentWorkingItem.setLocation(item.getString(LOCATION_KEY));

            currentWorkingItem.setQuality(Item.QUALITY.findQuality(item.getInt(QUALITY_KEY)));

            itemList.add(currentWorkingItem);

        }
        displayData();
    }



    private void displayData(){
        for(Item i : itemList){
            System.out.println(i.getBasicData());
        }
        System.out.println("------------------------------------------");
        itemList = new ArrayList<>();

    }

    @Override
    public String jsonResponse(String json) {
        formatJson(json);
        return null;
    }
}
