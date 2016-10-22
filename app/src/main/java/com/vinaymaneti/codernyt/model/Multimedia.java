
package com.vinaymaneti.codernyt.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import static com.vinaymaneti.codernyt.util.Constants.KEY_BASE_IMAGE_URL;

public class Multimedia implements Serializable {

    @SerializedName("width")
    private Integer width;
    @SerializedName("url")
    private String url;
    @SerializedName("height")
    private Integer height;
    @SerializedName("subtype")
    private String subtype;
//    @SerializedName("legacy")
//    private Legacy[] legacy;
//    @SerializedName("legacy")
//    private JSONArray legacyArray; //for array case
    @SerializedName("type")
    private String type;
    @SerializedName("credit")
    private String credit;
    @SerializedName("rank")
    private String rank;
    @SerializedName("caption")
    private String caption;


    /**
     * @return The width
     */
    public Integer getWidth() {
        return width;
    }

    /**
     * @param width The width
     */
    public void setWidth(Integer width) {
        this.width = width;
    }

    /**
     * @return The url
     */
    public String getUrl() {
        return KEY_BASE_IMAGE_URL + url;
    }

    /**
     * @param url The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return The height
     */
    public Integer getHeight() {
        return height;
    }

    /**
     * @param height The height
     */
    public void setHeight(Integer height) {
        this.height = height;
    }

    /**
     * @return The subtype
     */
    public String getSubtype() {
        return subtype;
    }

    /**
     * @param subtype The subtype
     */
    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

//    /**
//     * @return The legacy
//     */
//    public Legacy getLegacy() {
//        return legacy;
//    }
//
//    /**
//     * @param legacy The legacy
//     */
//    public void setLegacy(Legacy legacy) {
//        this.legacy = legacy;
//    }


//    public Legacy[] getLegacy() {
//        return legacy;
//    }
//
//    public void setLegacy(Legacy[] legacy) {
//        this.legacy = legacy;
//    }

    /**
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(String type) {
        this.type = type;
    }

}
