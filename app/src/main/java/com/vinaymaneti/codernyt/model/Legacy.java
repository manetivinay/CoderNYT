
package com.vinaymaneti.codernyt.model;

import com.google.gson.annotations.SerializedName;

public class Legacy {

    @SerializedName("wide")
    private String wide;

    @SerializedName("wideheight")
    private String wideHeight;

    @SerializedName("widewidth")
    private String wideWidth;

    @SerializedName("xlargewidth")
    private String xLargeWidth;

    @SerializedName("xlarge")
    private String xLarge;

    @SerializedName("xlargeheight")
    private String xLargeHeight;

    @SerializedName("thumbnailheight")
    private String thumbnailHeight;

    @SerializedName("thumbnail")
    private String thumbnail;

    @SerializedName("thumbnailwidth")
    private String thumbnailWidth;


    public String getWide() {
        return wide;
    }

    public String getWideHeight() {
        return wideHeight;
    }

    public String getWideWidth() {
        return wideWidth;
    }

    public String getxLargeWidth() {
        return xLargeWidth;
    }

    public String getxLarge() {
        return xLarge;
    }

    public String getxLargeHeight() {
        return xLargeHeight;
    }

    public String getThumbnailHeight() {
        return thumbnailHeight;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getThumbnailWidth() {
        return thumbnailWidth;
    }
}
