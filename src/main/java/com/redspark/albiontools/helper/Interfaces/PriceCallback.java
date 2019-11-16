package com.redspark.albiontools.helper.Interfaces;

import com.redspark.albiontools.helper.Item;

import java.util.List;

public interface PriceCallback {
    Item priceResponse(List<Item> itemsList);
}
