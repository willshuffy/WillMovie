package com.dicoding.willshuffy.willmovie;

import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.attr.format;
import static android.support.v7.widget.DividerItemDecoration.VERTICAL;


public class MainActivity extends AppCompatActivity
        implements MainView,
        MaterialSearchBar.OnSearchActionListener,
        SwipeRefreshLayout.OnRefreshListener,
        PopupMenu.OnMenuItemClickListener{

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipe_refresh;

    @BindView(R.id.search_bar)
    MaterialSearchBar search_bar;

    @BindView(R.id.rv_movielist)
    RecyclerView rv_movielist;


    private SearchAdapter adapter;
    private List<ResultsItem> list=new ArrayList<>();

    private Call<SearchModel> apiCall;
    private APIClient apiClient;

    private String movie_title= "";
    private int currentPage =1;
    private int totalPages =1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(   this);
        setSupportActionBar(toolbar);
        search_bar.setOnSearchActionListener(this);
        swipe_refresh.setOnRefreshListener(this);
        search_bar.inflateMenu(R.menu.main);
        search_bar.getMenu().setOnMenuItemClickListener(this);

        apiClient = new APIClient();
        MainPresenter presenter=new MainPresenter(this);

        setupList();
        setupListScrollListener();
        loadData("");

    }

    private void setupListScrollListener() {
        rv_movielist.addOnScrollListener(new RecyclerView.OnScrollListener() {
            /**
             * Callback method to be invoked when the RecyclerView has been scrolled. This will be
             * called after the scroll has completed.
             * <p>
             * This callback will also be called if visible item range changes after a layout
             * calculation. In that case, dx and dy will be 0.
             *
             * @param recyclerView The RecyclerView which scrolled.
             * @param dx           The amount of horizontal scroll.
             * @param dy           The amount of vertical scroll.
             */
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager=(LinearLayoutManager)recyclerView.getLayoutManager();

                int totalItems=layoutManager.getItemCount();
                int visibleItems=layoutManager.getChildCount();
                int pastVisibleItems=layoutManager.findFirstCompletelyVisibleItemPosition();

                if (pastVisibleItems + visibleItems >= totalItems){
                    if (currentPage < totalPages) currentPage++;
                    startRefreshing();
                }

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (apiCall != null) apiCall.cancel();
    }

    private void loadDummyData(){
        list.clear();
        for (int i = 0; i<= 10; i++){
            ResultsItem item=new ResultsItem();
            item.setPosterPath("/cGOPbv9wA5gEejkUN892JrveARt.jpg");
            item.setTitle("ini untuk judul movie yang paling panjang karakternya"+i);
            item.setOverview("ini untuk overview movie yang paling panjang karakternya"+i);
            item.setReleaseDate(DateTime.getLongDate("2018-03-19"+i));
            list.add(item);
        }
        adapter.replaceAll(list);
    }


    private void loadData(final String movie_title) {
        getSupportActionBar().setSubtitle("");

        if (movie_title.isEmpty()) apiCall=apiClient.getService().getPopularMovie(currentPage);
        else apiCall=apiClient.getService().getSearchMovie(currentPage, movie_title);

        apiCall.enqueue(new Callback<SearchModel>() {
            @Override
            public void onResponse(Call<SearchModel> call, Response<SearchModel> response) {
                if (response.isSuccessful()){
                    totalPages=response.body().getTotalPages();
                    List<ResultsItem> items = response.body().getResults();
                    showResults(response.body().getTotalResults());


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



    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        //Toast.makeText(this, "Searching: "+text, Toast.LENGTH_SHORT).show();

        movie_title= String.valueOf(text);

        onRefresh();
    }


    private void loadFailed(){
        stopRefreshing();
        Toast.makeText(MainActivity.this, "Failed to load data.\nPlease check your Internet connections!", Toast.LENGTH_SHORT).show();
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
        totalPages=1;

        stopRefreshing();
        startRefreshing();

    }

    private void startRefreshing() {

        if (swipe_refresh.isRefreshing()) return;
        swipe_refresh.setRefreshing(true);

        loadData(movie_title);

    }

    private void stopRefreshing(){
        if (swipe_refresh.isRefreshing()) swipe_refresh.setRefreshing(false);
    }

    /**
     * This method will be invoked when a menu item is clicked if the item
     * itself did not already handle the event.
     *
     * @param item the menu item that was clicked
     * @return {@code true} if the event was handled, {@code false}
     * otherwise
     */
    @Override
    public boolean onMenuItemClick(MenuItem item) {

       switch (item.getItemId()){
           case R.id.mn_refresh:
               onRefresh();
               break;
       }

        return false;
    }

    private void showResults(int totalResults){
        String results;

        String formatResults= NumberFormat.getIntegerInstance().format(totalResults);

        if (totalResults>0){
            results= "I found " + formatResults + " movie" + (totalResults > 1 ? "s" : "") + " for you :)";
        }else results= "Sorry! I can't find" + movie_title + "everywhere :(";

        getSupportActionBar().setSubtitle(results);
    }

}
