
package com.vinaymaneti.codernyt.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Headline implements Parcelable{

    @SerializedName("main")
    private String main;
    @SerializedName("name")
    private String name;
    @SerializedName("content_kicker")
    private String contentKicker;
    @SerializedName("kicker")
    private String kicker;
    @SerializedName("print_headline")
    private String printHeadline;

    /**
     * @return The main
     */
    public String getMain() {
        return main;
    }

    /**
     * @param main The main
     */
    public void setMain(String main) {
        this.main = main;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
