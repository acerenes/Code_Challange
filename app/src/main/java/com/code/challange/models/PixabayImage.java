package com.code.challange.models;

import android.text.TextUtils;

public class PixabayImage {
    private int likes;
    private int favorites;
    private String tags;
    private long views;
    private int comments;
    private String pageURL;
    private String previewURL;
    private String webformatURL;
    private int imageWidth;
    private int imageHeight;
    private String user;
    private long id;

    public PixabayImage(int likes, int favorites, String tags, long views, int comments, String pageURL, String previewURL,
                        String webformatURL, int imageWidth, String user, long id, int imageHeight) {

        this.likes = likes;
        this.favorites = favorites;
        this.tags = tags;
        this.views = views;
        this.comments = comments;
        this.pageURL = pageURL;
        this.previewURL = previewURL;
        this.webformatURL = webformatURL;
        this.imageWidth = imageWidth;
        this.user = user;
        this.id = id;
        this.imageHeight = imageHeight;
    }

    public int getLikes() {
        return likes;
    }

    public int getFavorites() {
        return favorites;
    }

    public long getViews() {
        return views;
    }

    public int getComments() {
        return comments;
    }

    public String getUser() {
        return user;
    }

    public String getTags() {
        if (tags == null) return "";
        if (tags.contains(", ")) {
            String[] splitTags = tags.toUpperCase().split(", ");
            return TextUtils.join(" - ", splitTags);
        } else return tags;
    }

    public String getPreviewURL() {
        return previewURL;
    }

    public String getWebformatURL() {
        return webformatURL;
    }
}