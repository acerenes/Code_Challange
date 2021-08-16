package com.code.challange.API;

import com.code.challange.models.PixabayImageList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PixaBayApi {
    //PixaBay get image results api
    @GET("/api/")
    Call<PixabayImageList> getSearchedQueryResults(@Query("key") String key, @Query("q") String query, @Query("page") int page, @Query("per_page") int perPage);
}
