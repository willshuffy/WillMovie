package com.dicoding.willshuffy.willmovie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dicoding.willshuffy.willmovie.mvp.MainPresenter;
import com.dicoding.willshuffy.willmovie.mvp.MainView;

public class MainActivity extends AppCompatActivity implements MainView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainPresenter presenter=new MainPresenter(this);
    }
}
