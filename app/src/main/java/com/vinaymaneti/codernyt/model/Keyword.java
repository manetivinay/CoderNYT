
package com.vinaymaneti.codernyt.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Keyword implements Parcelable {

    @SerializedName("rank")
    private String rank;
    @SerializedName("is_major")
    private String isMajor;
    @SerializedName("name")
    private String name;
    @SerializedName("value")
    private String value;

    protected Keyword(Parcel in) {
        rank = in.readString();
        isMajor = in.readString();
        name = in.readString();
        value = in.readString();
    }

    public static final Creator<Keyword> CREATOR = new Creator<Keyword>() {
        @Override
        public Keyword createFromParcel(Parcel in) {
            return new Keyword(in);
        }

        @Override
        public Keyword[] newArray(int size) {
            return new Keyword[size];
        }
    };

    /**
     * @return The rank
     */
    public String getRank() {
        return rank;
    }

    /**
     * @param rank The rank
     */
    public void setRank(String rank) {
        this.rank = rank;
    }

    /**
     * @return The isMajor
     */
    public String getIsMajor() {
        return isMajor;
    }

    /**
     * @param isMajor The is_major
     */
    public void setIsMajor(String isMajor) {
        this.isMajor = isMajor;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value The value
     */
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(rank);
        dest.writeString(isMajor);
        dest.writeString(name);
        dest.writeString(value);
    }
}
