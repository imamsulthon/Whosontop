package com.jekmol.whosontop.model.response;

import com.google.gson.annotations.SerializedName;
import com.jekmol.whosontop.model.entity.Item;

import java.util.ArrayList;

public class ItemResponse {

    @SerializedName("type") private String type;
    @SerializedName("value") private ArrayList<Item> results;

    public ItemResponse() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Item> getResults() {
        return results;
    }

    public void setResults(ArrayList<Item> results) {
        this.results = results;
    }
}
