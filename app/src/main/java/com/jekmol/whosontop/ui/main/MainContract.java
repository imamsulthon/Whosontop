package com.jekmol.whosontop.ui.main;

import com.jekmol.whosontop.model.Item;

import java.util.ArrayList;

public interface MainContract {

    interface View {

        void showLoading();

        void hideLoading();

        void showResults(ArrayList<Item> items);

        void showError();

        void hideAddDataButton(boolean hide);

    }

    interface Presenter {

        void initData();

        void refreshData();

        void addData();

    }

}
