package com.jekmol.whosontop.ui.main;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jekmol.whosontop.R;
import com.jekmol.whosontop.model.Item;
import com.jekmol.whosontop.adapater.JokeAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    @BindView(R.id.recyclerview) RecyclerView recyclerView;
    @BindView(R.id.layout_content) RelativeLayout layoutContent;
    @BindView(R.id.layout_error) RelativeLayout layoutError;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.add_more_data) TextView addMoreData;
    @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout swipeRefreshLayout;

    MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new MainPresenter(this);
        presenter.initData();

        swipeRefreshLayout.setOnRefreshListener(() -> presenter.refreshData());

        addMoreData.setOnClickListener(v -> presenter.addData());
    }

    @Override
    public void showProgressLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressLoading() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showRefreshLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideRefreshLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showResults(ArrayList<Item> items) {
        layoutContent.setVisibility(View.VISIBLE);
        layoutError.setVisibility(View.INVISIBLE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        JokeAdapter adapter = new JokeAdapter(this, items);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showError() {
        layoutContent.setVisibility(View.INVISIBLE);
        layoutError.setVisibility(View.VISIBLE);
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideAddMoreData(boolean hide) {
        if (hide) {
            addMoreData.setVisibility(View.INVISIBLE);
        } else {
            addMoreData.setVisibility(View.VISIBLE);
        }
    }

}
