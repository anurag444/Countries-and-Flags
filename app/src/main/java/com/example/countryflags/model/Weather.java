package com.example.countryflags.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Weather {

    @SerializedName("conditions")
    @Expose
    private String conditions;
    @SerializedName("high")
    @Expose
    private String high;
    @SerializedName("imageUrl")
    private String imageUrl;
    @SerializedName("low")
    private String low;
    @SerializedName("temp")
    private String temp;
    @SerializedName("tempUnits")
    private String tempUnits;

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getTempUnits() {
        return tempUnits;
    }

    public void setTempUnits(String tempUnits) {
        this.tempUnits = tempUnits;
    }
}
