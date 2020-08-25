package com.example.imgurimagesearch.data.network;

import com.example.imgurimagesearch.data.network.networkmodel.SearchResponse;


import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServiceApiManager {

    @GET("3/gallery/search/1")
    Single<SearchResponse> getImages(@Query("q") String searchInput);


}
