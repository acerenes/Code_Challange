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

import com.code.challange.PixabayApplication;
import com.code.challange.R;
import com.code.challange.adapter.PixabayImageListAdapter;
import com.code.challange.listeners.LoadMoreScrollListener;
import com.code.challange.listeners.SearchViewTextListener;
import com.code.challange.models.PixabayImage;
import com.code.challange.models.PixabayImageList;
import com.code.challange.API.PixaBayApi;
import com.victor.loading.rotate.RotateLoading;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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

    //injecting retrofit
    @Inject
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        injectDagger();
        initView();
        setSupportActionBar(toolbar);
        initRecyclerView();
        loadMoreProgressBar.start();
        loadImages(1,searchedQuery);
    }

    private void injectDagger() {
        //Inject dagger here to be able to use retrofit instance.
        ((PixabayApplication) getApplication()).getNetComponent().inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //To show search icon on the menu, we define it here.
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        SearchViewTextListener searchViewTextListener = new SearchViewTextListener(MainActivity.this);
        searchView.setOnQueryTextListener(searchViewTextListener);
        return true;
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
        //spanCount=3 --> shows how many elements will be in each row.
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(mLayoutManager);
        pixabayImageLists = new ArrayList<>();
        imageListAdapter = new PixabayImageListAdapter(pixabayImageLists, mainContext);
        recyclerView.setAdapter(imageListAdapter);
        initLoadMoreScrollListener(mLayoutManager);
    }

    private void initLoadMoreScrollListener(GridLayoutManager mLayoutManager) {
        //init this listener to be able to get images 24 by 24.

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
        //init all components to be able to use them.
        recyclerView = findViewById(R.id.activity_main_item_list_recycler);
        toolbar = findViewById(R.id.activity_main_toolbar);
        loadMoreProgressBar = findViewById(R.id.activity_main_progress);
        notFoundTextView = findViewById(R.id.activity_main_no_results_text);
    }

    private void loadImages(int page, String searchedQuery){
        PixaBayApi pixaBayApi = retrofit.create(PixaBayApi.class);

        //perPage=24 --> shows how many elements will be searched on each request.
        pixaBayApi.getSearchedQueryResults(getString(R.string.API_KEY), searchedQuery, page, 24).enqueue(new Callback<PixabayImageList>() {
            @Override
            public void onResponse(Call<PixabayImageList> call, Response<PixabayImageList> response) {
                if (response.isSuccessful())
                    if (response.body() != null) {
                        addImagesToList(response.body());
                    }
                else
                    loadMoreProgressBar.stop();
            }
            @Override
            public void onFailure(Call<PixabayImageList> call, Throwable t) {
                loadMoreProgressBar.stop();
                Toast.makeText(mainContext, mainContext.getString(R.string.connection_error),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void addImagesToList(PixabayImageList imageList) {
        //We add all images to the list before send it to the adapter.
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
        //We have to empty all list before search new word not to show old results.
        int lastSize = pixabayImageLists.size();
        pixabayImageLists.clear();
        loadMoreScrollListener.resetCurrentPage();
        imageListAdapter.notifyItemRangeRemoved(0,lastSize);
    }
}