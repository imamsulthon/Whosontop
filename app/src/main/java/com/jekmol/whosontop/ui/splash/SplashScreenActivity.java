package com.jekmol.whosontop.ui.splash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jekmol.whosontop.R;
import com.jekmol.whosontop.ui.main.MainActivity;

public class SplashScreenActivity extends AppCompatActivity implements SplashScreenContract.View {

    SplashScreenPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        presenter = new SplashScreenPresenter(this);
        presenter.doSplash(2000);

    }

    @Override
    public void goToMainScreen() {
        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
