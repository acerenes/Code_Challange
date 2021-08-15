package com.code.challange.services;

import android.content.Context;

import com.code.challange.R;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PixabayImageService {
    public static PixaBayApi createService(Context mainContext) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(mainContext.getString(R.string.pixabay_url));

        return builder.build().create(PixaBayApi.class);
    }
}
