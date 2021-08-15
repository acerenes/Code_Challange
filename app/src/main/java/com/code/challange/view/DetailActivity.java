package com.code.challange.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.code.challange.R;
import com.code.challange.databinding.ActivityDetailsBinding;
import com.code.challange.models.PixabayImage;
import com.code.challange.PixabayImageDetailViewModel;
import com.google.gson.Gson;

public class DetailActivity extends AppCompatActivity {

    ActivityDetailsBinding activityDetailsBinding;
    private PixabayImage image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_details);
        initImage();
        setViewModel();
    }

    private void setViewModel() {
        activityDetailsBinding.setViewmodelDetail(new PixabayImageDetailViewModel(image, DetailActivity.this));
    }

    private void initImage() {
        image = new Gson().fromJson(getIntent().getStringExtra("IMAGE"), PixabayImage.class);
    }
}