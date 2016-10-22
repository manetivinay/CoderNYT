package com.vinaymaneti.codernyt.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vinay on 21/10/16.
 */

public class SearchRequest implements Parcelable {
    private int page = 0;
    private String query;
    private String beginDate;
    private String order = "Newest";
    private boolean hasArt;
    private boolean hasFashionAndStyle;
    private boolean hasSports;

    public SearchRequest(){

    }

    public SearchRequest(Parcel in) {
        page = in.readInt();
        query = in.readString();
        beginDate = in.readString();
        order = in.readString();
        hasArt = in.readByte() != 0;
        hasFashionAndStyle = in.readByte() != 0;
        hasSports = in.readByte() != 0;
    }

    public static final Creator<SearchRequest> CREATOR = new Creator<SearchRequest>() {
        @Override
        public SearchRequest createFromParcel(Parcel in) {
            return new SearchRequest(in);
        }

        @Override
        public SearchRequest[] newArray(int size) {
            return new SearchRequest[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(page);
        dest.writeString(query);
        dest.writeString(beginDate);
        dest.writeString(order);
        dest.writeByte((byte) (hasArt ? 1 : 0));
        dest.writeByte((byte) (hasFashionAndStyle ? 1 : 0));
        dest.writeByte((byte) (hasSports ? 1 : 0));
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String newQuery) {
        this.query = newQuery;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public boolean isHasArt() {
        return hasArt;
    }

    public void setHasArt(boolean hasArt) {
        this.hasArt = hasArt;
    }

    public boolean isHasFashionAndStyle() {
        return hasFashionAndStyle;
    }

    public void setHasFashionAndStyle(boolean hasFashionAndStyle) {
        this.hasFashionAndStyle = hasFashionAndStyle;
    }

    public boolean isHasSports() {
        return hasSports;
    }

    public void setHasSports(boolean hasSports) {
        this.hasSports = hasSports;
    }

    public void nextPage() {
        page += 1;
    }

    public void resetPage() {
        page = 0;
    }

    public Map<String, String> toQueryMap() {
        Map<String, String> options = new HashMap<>();
        if (query != null) options.put("q", query);
        if (beginDate != null) options.put("begin_date", beginDate);
        if (order != null) options.put("sort", order.toLowerCase());
        if (getNewsDesk() != null) options.put("fq", "news_desk:(" + getNewsDesk() + ")");
        options.put("page", String.valueOf(page));
        return options;
    }

    private String getNewsDesk() {
        if (!hasArt && !hasFashionAndStyle && !hasSports)
            return null;
        String value = "";
        if (hasArt) value += " \"Arts\" "; // to surround in a Quotes "Arts" --> \"Arts\"
        if (hasFashionAndStyle) value += " \"Fashion & Style\" ";
        if (hasSports) value += " \"Sports\" ";
        return value.trim();
    }
}
