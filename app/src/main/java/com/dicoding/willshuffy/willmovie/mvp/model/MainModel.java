package com.dicoding.willshuffy.willmovie.mvp.model;

import java.util.List;

public class MainModel {
    private int page;
    private int totalPages;
    private List<ResultsItem> result;
    private int totalResult;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<ResultsItem> getResult() {
        return result;
    }

    public void setResult(List<ResultsItem> result) {
        this.result = result;
    }

    public int getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(int totalResult) {
        this.totalResult = totalResult;
    }

    @Override
    public String toString() {
        return "MainModel{"+
                            "page='"+page+'\''+
                            ",total_pages = '"+totalPages+'\''+
                            ",results ='"+result+'\''+
                            ",total_result='"+totalResult+'\''+
                            "}";
    }
}
