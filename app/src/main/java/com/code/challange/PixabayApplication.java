package com.code.challange;

import android.app.Application;

public class PixabayApplication extends Application {
    private ApiComponent mApiComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mApiComponent = DaggerApiComponent.builder()
                .appModule(new AppModule(this))
                .apiModule(new ApiModule("https://pixabay.com/"))
                .build();
    }

    public ApiComponent getNetComponent() {
        return mApiComponent;
    }
}
