package com.code.challange;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.databinding.BaseObservable;

import com.code.challange.models.PixabayImage;
import com.code.challange.view.DetailActivity;
import com.google.gson.Gson;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class PixabayImageDetailViewModel extends BaseObservable {
    private PixabayImage pixabayImage;
    private Context context;

    public PixabayImageDetailViewModel(PixabayImage pixabayImage, Context context) {
        this.pixabayImage = pixabayImage;
        this.context = context;
    }

    public String getTags() {
        return pixabayImage.getTags();
    }

    public String getHighResImageUrl() {
        return pixabayImage.getWebformatURL();
    }

    public String getLikes() {
        return String.valueOf(pixabayImage.getLikes());
    }

    public String getComments() {
        return String.valueOf(pixabayImage.getComments());
    }

    public String getFavorites() {
        return String.valueOf(pixabayImage.getFavorites());
    }

    public String getUserName() {
        return pixabayImage.getUser();
    }

    public View.OnClickListener openDetails() {
        return this::createDialogBox;
    }

    private void startActivity(View v) {
        Intent i = new Intent(v.getContext(), DetailActivity.class);
        String serialized = new Gson().toJson(pixabayImage);
        i.putExtra(context.getString(R.string.image_intent), serialized);
        v.getContext().startActivity(i);
    }

    private void createDialogBox(View v) {
        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(context.getString(R.string.sure_dialog))
                .setConfirmText(context.getString(R.string.yes_dialog))
                .setCancelText(context.getString(R.string.no_dialog))
                .setConfirmClickListener(sDialog -> {
                    sDialog.dismiss();
                    startActivity(v);
                })
                .setCancelClickListener(Dialog::dismiss)
                .show();
    }
}
