package com.dicoding.willshuffy.willmovie.api;

import com.dicoding.willshuffy.willmovie.mvp.model.detail.DetailModel;
import com.dicoding.willshuffy.willmovie.mvp.model.search.SearchModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APICall {

    @GET("movie/popular?")
    Call<SearchModel> getPopularMovie(@Query("page")int page);

    @GET("search/movie")
    Call<SearchModel> getSearchMovie(@Query("page") int page, @Query("query") String query);

    @GET("movie/{movie_id}")
    Call<DetailModel> getDetailMovie(@Path("movie_id") String movie_id);

    @GET("movie/upcoming")
    Call<SearchModel> getUpComingMovie();
}
