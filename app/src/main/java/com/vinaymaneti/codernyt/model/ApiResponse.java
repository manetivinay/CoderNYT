package com.vinaymaneti.codernyt.model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vinay on 20/10/16.
 */

public class ApiResponse {
    @SerializedName("response")
    private JsonObject response;

    @SerializedName("status")
    private String mStatus;

    public JsonObject getResponse() {
        if (response == null)
            return new JsonObject();
        return response;
    }

    public String getStatus() {
        return mStatus;
    }
}
