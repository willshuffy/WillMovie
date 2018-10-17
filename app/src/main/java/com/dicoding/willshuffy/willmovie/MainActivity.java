package com.dicoding.willshuffy.willmovie;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dicoding.willshuffy.willmovie.adapter.SearchAdapter;
import com.dicoding.willshuffy.willmovie.api.APIClient;
import com.dicoding.willshuffy.willmovie.mvp.MainPresenter;
import com.dicoding.willshuffy.willmovie.mvp.MainView;
import com.dicoding.willshuffy.willmovie.mvp.model.search.ResultsItem;
import com.dicoding.willshuffy.willmovie.mvp.model.search.SearchModel;

import com.dicoding.willshuffy.willmovie.utils.DateTime;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;


public class MainActivity extends AppCompatActivity implements MainView, MaterialSearchBar.OnSearchActionListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipe_refresh;

    @BindView(R.id.search_bar)
    MaterialSearchBar search_bar;

    @BindView(R.id.rv_movielist)
    RecyclerView rv_movielist;

    private String movie_title= "";
    private SearchAdapter adapter;
    private List<ResultsItem> list=new ArrayList<>();

    private Call<SearchModel> apiCall;
    private APIClient apiClient;
    private int currentPage =1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        search_bar.setOnSearchActionListener(this);
        swipe_refresh.setOnRefreshListener(this);

        apiClient = new APIClient();
        MainPresenter presenter=new MainPresenter(this);

        setupList();
        loadData();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (apiCall != null) apiCall.cancel();
    }



    private void loadData() {
        getSupportActionBar().setSubtitle("");

        apiCall=apiClient.getService().getPopularMovie(currentPage);
        apiCall.enqueue(new Callback<SearchModel>() {
            @Override
            public void onResponse(Call<SearchModel> call, Response<SearchModel> response) {
                if (response.isSuccessful()){
                    List<ResultsItem> items = response.body().getResults();

                    if (currentPage>1)adapter.updateData(items);
                    else adapter.replaceAll(items);

                    stopRefreshing();
                }else loadFailed();
            }

            @Override
            public void onFailure(Call<SearchModel> call, Throwable t) {
                loadFailed();
            }
        });

    }

    private void setupList(){
        adapter=new SearchAdapter();
        rv_movielist.addItemDecoration(new DividerItemDecoration(this, VERTICAL));
        rv_movielist.setLayoutManager(new LinearLayoutManager(this));
        rv_movielist.setAdapter(adapter);
    }

    private void loadDummyData(){
        list.clear();
        for (int i = 0; i<= 10; i++){
            ResultsItem item=new ResultsItem();
            item.setPosterPath("/cGOPbv9wA5gEejkUN892JrveARt.jpg");
            item.setTitle("Title"+i);
            item.setOverview("Overview"+i);
            item.setReleaseDate("2018-03-19");
            item.setTitle("ini untuk judul movie yang paling panjang karakternya"+i);
            item.setOverview("ini untuk overview movie yang paling panjang karakternya"+i);
            item.setReleaseDate(DateTime.getLongDate("2018-03-19"+i));
            list.add(item);
        }
        adapter.replaceAll(list);
    }

    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        //Toast.makeText(this, "Searching: "+text, Toast.LENGTH_SHORT).show();

        movie_title= String.valueOf(text);

        if (movie_title.equals("")) loadData();
        else loadData(String.valueOf(text));

    }

    private void loadData(String movie_title) {
        getSupportActionBar().setSubtitle("Searching: "+ movie_title);
        adapter.clearAll();
        stopRefreshing();

    }

    private void loadFailed(){
        stopRefreshing();
        Toast.makeText(MainActivity.this, "Failed to load data!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onButtonClicked(int buttonCode) {

    }

    /**
     * Called when a swipe gesture triggers a refresh.
     */

    @Override
    public void onRefresh() {

        currentPage=1;

        if (movie_title.equals("")) loadData();
        else loadData(movie_title);

    }

    private void stopRefreshing(){
        if (swipe_refresh.isRefreshing()) swipe_refresh.setRefreshing(false);
    }

}
