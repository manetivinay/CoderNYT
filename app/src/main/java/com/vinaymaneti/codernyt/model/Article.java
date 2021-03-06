
package com.vinaymaneti.codernyt.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Article implements Parcelable {
    @SerializedName("web_url")
    private String webUrl;
    @SerializedName("snippet")
    private String snippet;
    @SerializedName("lead_paragraph")
    private String leadParagraph;
    @SerializedName("abstract")
    private Object _abstract;
    @SerializedName("print_page")
    private Object printPage;
    @SerializedName("blog")
    private List<Object> blog = new ArrayList<Object>();
    @SerializedName("source")
    private String source;
    @SerializedName("multimedia")
    private List<Multimedia> multimedia;
    @SerializedName("headline")
    private Headline headline;
    @SerializedName("keywords")
    private List<Keyword> keywords = new ArrayList<Keyword>();
    @SerializedName("pub_date")
    private String pubDate;
    @SerializedName("document_type")
    private String documentType;
    @SerializedName("news_desk")
    private String newsDesk;
    @SerializedName("section_name")
    private String sectionName;
    @SerializedName("subsection_name")
    private Object subsectionName;
    @SerializedName("type_of_material")
    private String typeOfMaterial;
    @SerializedName("_id")
    private String id;
    @SerializedName("word_count")
    private Object wordCount;
    @SerializedName("slideshow_credits")
    private Object slideshowCredits;


    protected Article(Parcel in) {
        webUrl = in.readString();
        snippet = in.readString();
        leadParagraph = in.readString();
        source = in.readString();
        multimedia = in.createTypedArrayList(Multimedia.CREATOR);
        headline = in.readParcelable(Headline.class.getClassLoader());
        keywords = in.createTypedArrayList(Keyword.CREATOR);
        pubDate = in.readString();
        documentType = in.readString();
        newsDesk = in.readString();
        sectionName = in.readString();
        typeOfMaterial = in.readString();
        id = in.readString();
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    /**
     * @return The webUrl
     */
    public String getWebUrl() {
        return webUrl;
    }

    /**
     * @param webUrl The web_url
     */
    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    /**
     * @return The snippet
     */
    public String getSnippet() {
        return snippet;
    }

    /**
     * @param snippet The snippet
     */
    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    /**
     * @return The leadParagraph
     */
    public String getLeadParagraph() {
        return leadParagraph;
    }

    /**
     * @param leadParagraph The lead_paragraph
     */
    public void setLeadParagraph(String leadParagraph) {
        this.leadParagraph = leadParagraph;
    }

    /**
     * @return The _abstract
     */
    public Object getAbstract() {
        return _abstract;
    }

    /**
     * @param _abstract The abstract
     */
    public void setAbstract(Object _abstract) {
        this._abstract = _abstract;
    }

    /**
     * @return The printPage
     */
    public Object getPrintPage() {
        return printPage;
    }

    /**
     * @param printPage The print_page
     */
    public void setPrintPage(Object printPage) {
        this.printPage = printPage;
    }

    /**
     * @return The blog
     */
    public List<Object> getBlog() {
        return blog;
    }

    /**
     * @param blog The blog
     */
    public void setBlog(List<Object> blog) {
        this.blog = blog;
    }

    /**
     * @return The source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source The source
     */
    public void setSource(String source) {
        this.source = source;
    }

    public List<Multimedia> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(List<Multimedia> multimedia) {
        this.multimedia = multimedia;
    }

    /**
     * @return The headline
     */
    public Headline getHeadline() {
        return headline;
    }

    /**
     * @param headline The headline
     */
    public void setHeadline(Headline headline) {
        this.headline = headline;
    }

    /**
     * @return The keywords
     */
    public List<Keyword> getKeywords() {
        return keywords;
    }

    /**
     * @param keywords The keywords
     */
    public void setKeywords(List<Keyword> keywords) {
        this.keywords = keywords;
    }

    /**
     * @return The pubDate
     */
    public String getPubDate() {
        return pubDate;
    }

    /**
     * @param pubDate The pub_date
     */
    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    /**
     * @return The documentType
     */
    public String getDocumentType() {
        return documentType;
    }

    /**
     * @param documentType The document_type
     */
    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    /**
     * @return The newsDesk
     */
    public String getNewsDesk() {
        return newsDesk;
    }

    /**
     * @param newsDesk The news_desk
     */
    public void setNewsDesk(String newsDesk) {
        this.newsDesk = newsDesk;
    }

    /**
     * @return The sectionName
     */
    public String getSectionName() {
        return sectionName;
    }

    /**
     * @param sectionName The section_name
     */
    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    /**
     * @return The subsectionName
     */
    public Object getSubsectionName() {
        return subsectionName;
    }

    /**
     * @param subsectionName The subsection_name
     */
    public void setSubsectionName(Object subsectionName) {
        this.subsectionName = subsectionName;
    }

//    /**
//     * @return The byline
//     */
//    public Byline getByline() {
//        return byline;
//    }
//
//    /**
//     * @param byline The byline
//     */
//    public void setByline(Byline byline) {
//        this.byline = byline;
//    }

    /**
     * @return The typeOfMaterial
     */
    public String getTypeOfMaterial() {
        return typeOfMaterial;
    }

    /**
     * @param typeOfMaterial The type_of_material
     */
    public void setTypeOfMaterial(String typeOfMaterial) {
        this.typeOfMaterial = typeOfMaterial;
    }

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The _id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return The wordCount
     */
    public Object getWordCount() {
        return wordCount;
    }

    /**
     * @param wordCount The word_count
     */
    public void setWordCount(Object wordCount) {
        this.wordCount = wordCount;
    }

    /**
     * @return The slideshowCredits
     */
    public Object getSlideshowCredits() {
        return slideshowCredits;
    }

    /**
     * @param slideshowCredits The slideshow_credits
     */
    public void setSlideshowCredits(Object slideshowCredits) {
        this.slideshowCredits = slideshowCredits;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(webUrl);
        dest.writeString(snippet);
        dest.writeString(leadParagraph);
        dest.writeString(source);
        dest.writeTypedList(multimedia);
        dest.writeParcelable(headline, flags);
        dest.writeTypedList(keywords);
        dest.writeString(pubDate);
        dest.writeString(documentType);
        dest.writeString(newsDesk);
        dest.writeString(sectionName);
        dest.writeString(typeOfMaterial);
        dest.writeString(id);
    }
}
