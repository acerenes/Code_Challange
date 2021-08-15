package com.code.challange.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.code.challange.R;
import com.code.challange.databinding.PixabayImageItemBinding;
import com.code.challange.models.PixabayImage;
import com.code.challange.PixabayImageDetailViewModel;
import com.code.challange.PixabayImageMainViewModel;

import java.util.List;

public class PixabayImageListAdapter extends RecyclerView.Adapter<PixabayImageListAdapter.PixabayImageViewHolder> {

    private final List<PixabayImage> pixabayImageList;
    private Context context;

    public PixabayImageListAdapter(List<PixabayImage> pixabayImageList, Context mainContext) {
        this.pixabayImageList = pixabayImageList;
        this.context = mainContext;
    }

    @NonNull
    @Override
    public PixabayImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PixabayImageListAdapter.PixabayImageViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pixabay_image_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PixabayImageViewHolder holder, int position) {
        holder.pixabayImageItemBinding.setViewmodelMain(new PixabayImageMainViewModel(pixabayImageList.get(position)));
        holder.pixabayImageItemBinding.setViewmodelDetail(new PixabayImageDetailViewModel(pixabayImageList.get(position),context));
    }

    @Override
    public int getItemCount() {
        return pixabayImageList.size();
    }

    public static class PixabayImageViewHolder extends RecyclerView.ViewHolder {

        public final PixabayImageItemBinding pixabayImageItemBinding;

        public PixabayImageViewHolder(View v) {
            super(v);
            pixabayImageItemBinding = PixabayImageItemBinding.bind(v);
        }
    }
}
