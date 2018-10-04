package com.dicoding.willshuffy.willmovie.mvp.model.upcoming;

import com.google.gson.annotations.SerializedName;

public class Dates {

    @SerializedName("maximum")
    private String maximum;
    @SerializedName("minimum")
    private String minimum;

    public String getMaximum() {
        return maximum;
    }

    public void setMaximum(String maximum) {
        this.maximum = maximum;
    }

    public String getMinimum() {
        return minimum;
    }

    public void setMinimum(String minimum) {
        this.minimum = minimum;
    }

    @Override
    public String toString() {
        return "Dates{" +
                "maximum = '" + maximum + '\'' +
                ",minimum = '" + minimum + '\'' +
                "}";
    }
}
