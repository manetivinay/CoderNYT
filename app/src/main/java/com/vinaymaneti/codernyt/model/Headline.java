
package com.vinaymaneti.codernyt.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Headline implements Parcelable {

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

    protected Headline(Parcel in) {
        main = in.readString();
        name = in.readString();
        contentKicker = in.readString();
        kicker = in.readString();
        printHeadline = in.readString();
    }

    public static final Creator<Headline> CREATOR = new Creator<Headline>() {
        @Override
        public Headline createFromParcel(Parcel in) {
            return new Headline(in);
        }

        @Override
        public Headline[] newArray(int size) {
            return new Headline[size];
        }
    };

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
        dest.writeString(main);
        dest.writeString(name);
        dest.writeString(contentKicker);
        dest.writeString(kicker);
        dest.writeString(printHeadline);
    }
}
