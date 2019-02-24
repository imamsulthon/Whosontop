package com.jekmol.whosontop.ui.main;

import com.jekmol.whosontop.model.entity.Item;

import java.util.ArrayList;

public interface MainContract {

    interface View {

        void showProgressLoading();

        void hideProgressLoading();

        void showRefreshLoading();

        void hideRefreshLoading();

        void showResults(ArrayList<Item> items);

        void showError();

        void showMessage(String message);

        void hideAddMoreData(boolean hide);

    }

    interface Presenter {

        void initData();

        void refreshData();

        void addData();

        void deleteDabase();

    }

}
