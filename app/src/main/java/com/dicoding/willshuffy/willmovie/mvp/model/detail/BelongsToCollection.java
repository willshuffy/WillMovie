package com.dicoding.willshuffy.willmovie.mvp.model.detail;

import com.google.gson.annotations.SerializedName;

public class BelongsToCollection {

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private int id;

    @SerializedName("poster_path")
    private String posterPath;

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    @Override
    public String toString() {
        return "BellongsToColection{"+
                "backdrop_path = '"+backdropPath+'\''+
                ",name = '"+name+'\''+
                ",id = '"+id+'\''+
                ",poster_path = '"+posterPath+'\''+
                "}";
    }
}
