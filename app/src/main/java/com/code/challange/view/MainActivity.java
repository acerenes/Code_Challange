package com.code.challange.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.code.challange.R;
import com.code.challange.adapter.PixabayImageListAdapter;
import com.code.challange.listener.LoadMoreScrollListener;
import com.code.challange.listener.SearchViewTextListener;
import com.code.challange.models.PixabayImage;
import com.code.challange.models.PixabayImageList;
import com.code.challange.services.PixabayImageService;
import com.victor.loading.rotate.RotateLoading;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private RotateLoading loadMoreProgressBar;
    private TextView notFoundTextView;

    private List<PixabayImage> pixabayImageLists;
    private PixabayImageListAdapter imageListAdapter;
    private LoadMoreScrollListener loadMoreScrollListener;
    private String searchedQuery = "fruits";
    private MenuItem menuItem;
    private final Context mainContext = this;
    private static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;
        initView();
        setSupportActionBar(toolbar);
        initRecyclerView();
        loadMoreProgressBar.start();
        loadImages(1,searchedQuery);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        SearchViewTextListener searchViewTextListener = new SearchViewTextListener();
        searchView.setOnQueryTextListener(searchViewTextListener);
        return true;
    }

    public static MainActivity getInstance() {
        return instance;
    }

    public void afterSearchedQuery(String query){
        menuItem.collapseActionView();
        searchedQuery = query;
        emptyImageList();
        loadMoreProgressBar.start();
        notFoundTextView.setVisibility(View.GONE);
        loadImages(1, searchedQuery);
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(mLayoutManager);
        pixabayImageLists = new ArrayList<>();
        imageListAdapter = new PixabayImageListAdapter(pixabayImageLists, mainContext);
        recyclerView.setAdapter(imageListAdapter);
        initLoadMoreScrollListener(mLayoutManager);
    }

    private void initLoadMoreScrollListener(GridLayoutManager mLayoutManager) {
        loadMoreScrollListener = new LoadMoreScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page) {
                loadMoreProgressBar.start();
                loadImages(page, searchedQuery);
            }
        };
        recyclerView.addOnScrollListener(loadMoreScrollListener);
    }

    private void initView() {
        recyclerView = findViewById(R.id.activity_main_item_list_recycler);
        toolbar = findViewById(R.id.activity_main_toolbar);
        loadMoreProgressBar = findViewById(R.id.activity_main_progress);
        notFoundTextView = findViewById(R.id.activity_main_no_results_text);
    }

    private void loadImages(int page, String searchedQuery){
        PixabayImageService.createService(mainContext).getImageResults(getString(R.string.API_KEY), searchedQuery, page, 24).enqueue(new Callback<PixabayImageList>() {
            @Override
            public void onResponse(Call<PixabayImageList> call, Response<PixabayImageList> response) {
                if (response.isSuccessful())
                    addImagesToList(response.body());
                else
                    loadMoreProgressBar.stop();
            }
            @Override
            public void onFailure(Call<PixabayImageList> call, Throwable t) {
                loadMoreProgressBar.stop();
                Toast.makeText(mainContext, mainContext.getString(R.string.connection_error),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addImagesToList(PixabayImageList imageList) {
        loadMoreProgressBar.stop();
        int position = pixabayImageLists.size();
        pixabayImageLists.addAll(imageList.getHits());
        imageListAdapter.notifyItemRangeInserted(position, position + 24);

        if (pixabayImageLists.isEmpty())
            notFoundTextView.setVisibility(View.VISIBLE);
        else
            notFoundTextView.setVisibility(View.GONE);
    }

    private void emptyImageList() {
        pixabayImageLists.clear();
        loadMoreScrollListener.resetCurrentPage();
        imageListAdapter.notifyDataSetChanged();
    }
}