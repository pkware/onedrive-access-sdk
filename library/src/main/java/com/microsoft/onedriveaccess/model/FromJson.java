package com.microsoft.onedriveaccess.model;

import org.json.simple.JSONObject;

public interface FromJson {
    /**
     * Populates the model from the given json object
     */
    void fromJson(JSONObject json);
}
