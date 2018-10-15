package com.dicoding.willshuffy.willmovie;

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
import com.dicoding.willshuffy.willmovie.mvp.MainPresenter;
import com.dicoding.willshuffy.willmovie.mvp.MainView;
import com.dicoding.willshuffy.willmovie.mvp.model.upcoming.ResultsItem;
import com.dicoding.willshuffy.willmovie.utils.DateTime;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;


public class MainActivity extends AppCompatActivity implements MainView, MaterialSearchBar.OnSearchActionListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.searchBar)
    MaterialSearchBar searchBar;

    @BindView(R.id.rv_movielist)
    RecyclerView rv_movielist;

    private SearchAdapter adapter;
    private List<ResultsItem> list=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        searchBar.setOnSearchActionListener(this);

        MainPresenter presenter=new MainPresenter(this);

        setupList();
        loadDummyData();
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
        Toast.makeText(this, "Searching: "+text, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onButtonClicked(int buttonCode) {

    }
}
