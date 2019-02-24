package com.jekmol.whosontop.service;

import com.jekmol.whosontop.model.response.ItemResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("jokes/random/10-")
    Call<ItemResponse> getData();

}
