package com.example.imgurimagesearch.data.network.networkmodel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ImageDataResponse implements Serializable {

    /**
     * The ID for the image
     */

    @SerializedName("id")
    private String id;

    /**
     * The ID for the image
     */
    public String getId() {
        return id;
    }

    /**
     * The title of the image.
     */
    @SerializedName("title")
    private String title;

    /**
     * The title of the image.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Description of the image.
     */
    @SerializedName("description")
    private String description;

    /**
     * Description of the image.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Url of the image
     */
    @SerializedName("link")
    private String link;

    /**
     * Url of the image
     */
    public String getLink() {
        return link;
    }


    /**
     * Image MIME type.
     */
    @SerializedName("type")
    private String type;

    /**
     * Image MIME type.
     */
    public String getType() {
        return type;
    }

    /**
     * Image mp4 type.
     */
    @SerializedName("mp4")
    private String mp4;

    /**
     * Image MIME type.
     */
    public String getMp4() {
        return mp4;
    }







}
