package com.code.challange;

import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.code.challange.models.PixabayImage;

import java.util.Locale;

public class PixabayImageMainViewModel extends BaseObservable {

    private PixabayImage pixabayImage;

    public PixabayImageMainViewModel(PixabayImage pixabayImage) {
        this.pixabayImage = pixabayImage;
    }

    public String getTags() {
        return pixabayImage.getTags().toLowerCase(Locale.ROOT);
    }

    public String getImageUrl() {
        return pixabayImage.getPreviewURL();
    }

    public String getUserName() {
        return "user: " + pixabayImage.getUser();
    }

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.photos)
                .into(view);
    }
}
