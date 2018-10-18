package com.dicoding.willshuffy.willmovie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dicoding.willshuffy.willmovie.api.APIClient;
import com.dicoding.willshuffy.willmovie.mvp.model.detail.DetailModel;
import com.dicoding.willshuffy.willmovie.utils.DateTime;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    public static final String MOVIE_ID = "movie_id";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.img_backdrop)
    ImageView img_backdrop;

    @BindView(R.id.img_poster)
    ImageView img_poster;

    @BindView(R.id.tv_release_date)
    TextView tv_release_date;

    @BindView(R.id.tv_vote)
    TextView tv_vote;

    @BindView(R.id.tv_overview)
    TextView tv_overview;

    @BindViews({
            R.id.img_star1,
            R.id.img_star2,
            R.id.img_star3,
            R.id.img_star4,
            R.id.img_star5
    })
    List<ImageView> img_vote;

    private Call<DetailModel> apiCall;
    private APIClient apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        apiClient = new APIClient();

        String movie_id = getIntent().getStringExtra(MOVIE_ID);
        loadData(movie_id);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (apiCall != null) apiCall.cancel();
    }

    private void loadData(String movie_id){


        apiCall=apiClient.getService().getDetailMovie(movie_id);
        apiCall.enqueue(new Callback<DetailModel>() {
            @Override
            public void onResponse(Call<DetailModel> call, Response<DetailModel> response) {
                if (response.isSuccessful()){
                    DetailModel item = response.body();

                    getSupportActionBar().setTitle(item.getTitle());

                    Glide.with(DetailActivity.this)
                            .load(BuildConfig.BASE_URL_IMG + "w185" + item.getBackdropPath())
                            .apply(new RequestOptions().centerCrop())
                            .into(img_backdrop);

                    Glide.with(DetailActivity.this)
                            .load(BuildConfig.BASE_URL_IMG + "w154" + item.getPosterPath())
                            .apply(new RequestOptions()
                                    .placeholder(R.drawable.placeholder)
                                    .centerCrop()

                            )
                            .into(img_poster);

                    tv_release_date.setText(DateTime.getLongDate(item.getReleaseDate()));
                    tv_vote.setText(String.valueOf(item.getVoteAverage()));
                    tv_overview.setText(item.getOverview());

                    double userRating = item.getVoteAverage() / 2;
                    int integerPart = (int) userRating;

                    // fill stars
                    for (int i =0; i < integerPart; i++){
                        img_vote.get(i).setImageResource(R.drawable.ic_star_black_24dp);
                    }

                    //fill half stars
                    if (Math.round(userRating) > integerPart){
                        img_vote.get(integerPart).setImageResource(R.drawable.ic_star_half_black_24dp);
                    }
                }else loadFailed();
            }

            @Override
            public void onFailure(Call<DetailModel> call, Throwable t) {
                loadFailed();

            }
        });

    }

    private void loadFailed(){
        Toast.makeText(DetailActivity.this, "Cannot fetch Detail Movie.\nPlease check your Internet connections!", Toast.LENGTH_SHORT).show();
    }

}
