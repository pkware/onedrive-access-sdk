package com.microsoft.onedriveaccess.model;

import org.json.simple.JSONObject;

import java.util.List;

public class SearchResult implements FromJson {
    public List<Item> items;

    @Override
    public void fromJson(JSONObject json) {
        items = (List<Item>) json.get("value");
    }
}
