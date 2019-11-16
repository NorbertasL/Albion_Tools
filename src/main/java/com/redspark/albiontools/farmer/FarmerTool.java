package com.redspark.albiontools.farmer;

import com.redspark.albiontools.Constants;
import com.redspark.albiontools.helper.Interfaces.PriceCallback;
import com.redspark.albiontools.helper.Item;
import com.redspark.albiontools.pricer.ItemPricer;

import java.io.BufferedReader;
import java.util.List;

public class FarmerTool implements PriceCallback {
    private Item[][] crops;

    public FarmerTool(BufferedReader bufferedReader) {
        //Item[0] = Crop
        //Item[1] = Seed
        crops = new Item[2][Constants.CROP.values().length];
        ItemPricer pricer = new ItemPricer();


        for (Constants.CROP crop : Constants.CROP.values()) {
            pricer.getPrice(crop.getCropName(), 0, this);
            pricer.getPrice(crop.getCropSeedName(), 0, this);
        }


    }

    @Override
    public Item priceResponse(List<Item> itemsList) {
        for (Item item : itemsList) {
            String name = item.getName();
            for (int i = 0; i < Constants.CROP.values().length; i++) {
                if (name.equalsIgnoreCase(Constants.CROP.values()[i].getCropName())) {
                    crops[0][i] = item;
                    System.out.println(name + " added to Crop index [" + i + "]");
                    break;
                } else if (name.equalsIgnoreCase(Constants.CROP.values()[i].getCropSeedName())) {
                    crops[1][i] = item;
                    System.out.println(name + " added to Seed index [" + i + "]");
                    break;
                }
            }
        }
        return null;
    }
}
