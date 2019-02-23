package com.jekmol.whosontop.ui.splash;

import android.os.Handler;

public class SplashScreenPresenter implements SplashScreenContract.Presenter {

    SplashScreenContract.View view;

    public SplashScreenPresenter(SplashScreenContract.View view) {
        this.view = view;
    }

    @Override
    public void doSplash(Integer delay) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                view.goToMainScreen();
            }
        };
        new Handler().postDelayed(runnable, delay);
    }

}
