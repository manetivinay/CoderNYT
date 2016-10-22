
package com.vinaymaneti.codernyt.model;

import com.google.gson.annotations.SerializedName;

public class Headline {

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

}
