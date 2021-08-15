package com.code.challange.listeners;

import androidx.appcompat.widget.SearchView;
import com.code.challange.view.MainActivity;

public class SearchViewTextListener implements SearchView.OnQueryTextListener {

    @Override
    public boolean onQueryTextSubmit(String query) {
        MainActivity.getInstance().afterSearchedQuery(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
