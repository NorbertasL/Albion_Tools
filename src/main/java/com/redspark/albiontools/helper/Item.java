package com.redspark.albiontools.helper;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

import com.redspark.albiontools.Constants.QUALITY;

/**
 * Class that encapsulates item data including market data
 */

public class Item {

    private String name, id, location;
    private QUALITY quality;
    private int sell, buy, enchant;
    private LocalDateTime sellDate, buyDate;

    private long lastSellUpdate, lastBuyUpdate;

    public Item(String name) {
        this.name = name;
    }

    public Item(String name, String id, int enchant) {
        this.name = name;
        this.id = id;
        this.enchant = enchant;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return Objects.requireNonNullElse(location, "UNKNOWN");
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public QUALITY getQuality() {
        return Objects.requireNonNullElse(quality, QUALITY.UNKNOWN);
    }

    public void setQuality(QUALITY quality) {
        this.quality = quality;
    }

    public int getSell() {
        return Objects.requireNonNullElse(sell, -1);
    }

    public void setSell(int sell) {
        this.sell = sell;
    }

    public int getBuy() {
        return Objects.requireNonNullElse(buy, -1);
    }

    public void setBuy(int buy) {
        this.buy = buy;
    }

    public int getEnchant() {
        return Objects.requireNonNullElse(enchant, -1);
    }

    public void setEnchant(int enchant) {
        this.enchant = enchant;
    }

    public LocalDateTime getSellDate() {
        return sellDate;
    }

    public void setSellDate(LocalDateTime sellDate) {
        this.sellDate = sellDate;
        lastSellUpdate = Duration.between(sellDate, LocalDateTime.now()).toMinutes();
    }

    public LocalDateTime getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(LocalDateTime buyDate) {
        this.buyDate = buyDate;
        lastBuyUpdate = Duration.between(buyDate, LocalDateTime.now()).toMinutes();
    }


    public String getBasicData() {
        String s = "";
        //s+=name;
        s += "Q:" + quality.toString();
        s += " E:" + enchant;
        s += " Sell:" + sell;
        s += " Updated " + lastSellUpdate + " min ago, ";
        s += " Buy:" + buy;
        s += " Updated " + lastBuyUpdate + " min ago, ";
        return s;
    }

}
