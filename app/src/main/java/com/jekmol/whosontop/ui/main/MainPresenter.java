package com.jekmol.whosontop.ui.main;

import com.jekmol.whosontop.data.ApiClient;
import com.jekmol.whosontop.data.ApiService;
import com.jekmol.whosontop.model.Item;
import com.jekmol.whosontop.model.ItemResponse;
import com.jekmol.whosontop.ui.main.MainContract.Presenter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter implements Presenter {

    private MainContract.View view;
    private ArrayList<Item> itemList = new ArrayList<>();
    private ApiService apiService;

    public MainPresenter(MainContract.View view) {
        this.view = view;
        this.apiService = ApiClient.makeService();
    }

    @Override
    public void initData() {
        view.showLoading();
        Call<ItemResponse> call = apiService.getData();
        call.enqueue(new Callback<ItemResponse>() {
            @Override
            public void onResponse(Call<ItemResponse> call, Response<ItemResponse> response) {
                ItemResponse itemResponse = response.body();
                if (itemResponse != null) {
                    ArrayList<Item> arrayList = itemResponse.getResults();
                    itemList.clear();
                    for (int i = 0; i <= 2; i++) {
                        itemList.add(arrayList.get(i));
                    }
                    view.showResults(itemList);
                } else {
                    view.showError();
                }
            }

            @Override
            public void onFailure(Call<ItemResponse> call, Throwable t) {
                view.showError();
            }
        });
        view.hideLoading();
    }

    @Override
    public void refreshData() {
        view.showLoading();
        Call<ItemResponse> call = apiService.getData();
        call.enqueue(new Callback<ItemResponse>() {
            @Override
            public void onResponse(Call<ItemResponse> call, Response<ItemResponse> response) {
                ItemResponse itemResponse = response.body();
                if (itemResponse != null) {
                    ArrayList<Item> arrayList = itemResponse.getResults();
                    itemList.clear();
                    for (int i = 0; i <= 2; i++) {
                        itemList.add(arrayList.get(i));
                    }
                    view.showResults(itemList);
                    view.hideAddDataButton(false);
                } else {
                    view.showError();
                }
            }

            @Override
            public void onFailure(Call<ItemResponse> call, Throwable t) {
                view.showError();
            }
        });
        view.hideLoading();
    }

    @Override
    public void addData() {
        Call<ItemResponse> call = apiService.getData();
        call.enqueue(new Callback<ItemResponse>() {
            @Override
            public void onResponse(Call<ItemResponse> call, Response<ItemResponse> response) {
                ItemResponse itemResponse = response.body();
                if (itemResponse != null) {
                    ArrayList<Item> arrayList = itemResponse.getResults();
                    itemList.add(arrayList.get(0));
                    view.showResults(itemList);
                    if (itemList.size() >= 5) {
                        view.hideAddDataButton(true);
                    } else {
                        view.hideAddDataButton(false);
                    }
                } else {
                    view.showError();
                }
            }

            @Override
            public void onFailure(Call<ItemResponse> call, Throwable t) {
                view.showError();
            }
        });
    }

}
