package com.dicoding.willshuffy.willmovie.mvp.model;

import java.util.List;

public class ResultsItem {

    private String overview;
    private String originalLanguage;
    private String originalTitle;
    private boolean video;
    private String title;
    private List<Integer>genreIds;
    private String posterPath;
    private String backdropPath;
    private String releaseDate;
    private double voteAverage;
    private double popularity;
    private  int id;
    private boolean adult;
    private int voteCount;

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    @Override
    public String toString() {
        return  "ResultsItem{" +
                "overview = '" + overview + '\'' +
                ",original_language = '" + originalLanguage + '\'' +
                ",original_title = '" + originalTitle + '\'' +
                ",video = '" + video + '\'' +
                ",title = '" + title + '\'' +
                ",genre_ids = '" + genreIds + '\'' +
                ",poster_path = '" + posterPath + '\'' +
                ",backdrop_path = '" + backdropPath + '\'' +
                ",release_date = '" + releaseDate + '\'' +
                ",vote_average = '" + voteAverage + '\'' +
                ",popularity = '" + popularity + '\'' +
                ",id = '" + id + '\'' +
                ",adult = '" + adult + '\'' +
                ",vote_count = '" + voteCount + '\'' +
                "}";
    }
}
