package com.code.challange.listeners;

import androidx.appcompat.widget.SearchView;
import com.code.challange.view.MainActivity;

public class SearchViewTextListener implements SearchView.OnQueryTextListener {

    MainActivity activity;

    public SearchViewTextListener(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        activity.afterSearchedQuery(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
