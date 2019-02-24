package com.jekmol.whosontop.ui.main;

import com.jekmol.whosontop.model.data.DatabaseHelper;
import com.jekmol.whosontop.service.ApiClient;
import com.jekmol.whosontop.service.ApiService;
import com.jekmol.whosontop.model.entity.Item;
import com.jekmol.whosontop.model.response.ItemResponse;
import com.jekmol.whosontop.ui.main.MainContract.Presenter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter implements Presenter {

    private MainContract.View view;
    private ArrayList<Item> itemList = new ArrayList<>();
    private ApiService apiService;
    private DatabaseHelper database;

    public MainPresenter(MainContract.View view) {
        this.view = view;
        this.apiService = ApiClient.makeService();
        this.database = new DatabaseHelper();
    }

    @Override
    public void initData() {
        view.showProgressLoading();
        Call<ItemResponse> call = apiService.getData();
        call.enqueue(new Callback<ItemResponse>() {
            @Override
            public void onResponse(Call<ItemResponse> call, Response<ItemResponse> response) {
                view.hideProgressLoading();
                ItemResponse itemResponse = response.body();
                if (itemResponse != null) {
                    ArrayList<Item> arrayList = itemResponse.getResults();
                    itemList.clear();
                    for (int i = 0; i < arrayList.size(); i++) {
                        if (i <= 2) {
                            itemList.add(arrayList.get(i));
                        } else {
                            database.open();
                            database.addItem(arrayList.get(i));
                            database.close();
                        }
                    }
                    view.showResults(itemList);
                    view.showMessage("Succesfully init data");
                } else {
                    view.showMessage("Null result");
                }
            }

            @Override
            public void onFailure(Call<ItemResponse> call, Throwable t) {
                view.hideProgressLoading();
                view.showError();
            }
        });
    }

    @Override
    public void refreshData() {
        view.showRefreshLoading();
        Call<ItemResponse> call = apiService.getData();
        call.enqueue(new Callback<ItemResponse>() {
            @Override
            public void onResponse(Call<ItemResponse> call, Response<ItemResponse> response) {
                view.hideRefreshLoading();
                ItemResponse itemResponse = response.body();
                if (itemResponse != null) {
                    ArrayList<Item> arrayList = itemResponse.getResults();
                    itemList.clear();
                    database.open();
                    database.deleteAllItem();
                    for (int i = 0; i < arrayList.size(); i++) {
                        if (i <= 2) {
                            itemList.add(arrayList.get(i));
                        } else {
                            database.addItem(arrayList.get(i));
                        }
                    }
                    database.close();
                    view.showResults(itemList);
                    view.showMessage("Succesfully refreshing data");
                    view.hideAddMoreData(false);
                } else {
                    view.showMessage("Null result");
                }
            }

            @Override
            public void onFailure(Call<ItemResponse> call, Throwable t) {
                view.hideRefreshLoading();
                view.showError();
            }
        });

    }

    @Override
    public void addData() {
        try {
            database.open();
            ArrayList<Item> arrayList = new ArrayList<>();
            arrayList.addAll(database.getAllItems());
            if (arrayList.size() != 0) {
                if (itemList.size() <= 5) {
                    itemList.add(arrayList.get(0));
                    database.deleteItem(arrayList.get(0));
                }
                if (itemList.size() >= 5) {
                    view.hideAddMoreData(true);
                }
                view.showResults(itemList);
                view.showMessage("Succesfully added data");
            } else {
                view.showMessage("No more data");
            }
            database.close();
        } catch (Exception e) {
            e.printStackTrace();
            view.showError();
        }
    }

    @Override
    public void deleteDabase() {
        try {
            database.open();
            database.deleteAllItem();
            database.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
