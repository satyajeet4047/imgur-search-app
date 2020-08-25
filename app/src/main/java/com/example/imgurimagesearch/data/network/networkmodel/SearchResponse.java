package com.example.imgurimagesearch.data.network.networkmodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponse {

    @SerializedName("data")
    private List<ImageDataResponse> data;
    @SerializedName("success")
    private boolean success;
    @SerializedName("status")
    private String status;



    public List<ImageDataResponse> getData() {
        return data;
    }

    public void setData(List<ImageDataResponse> data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

