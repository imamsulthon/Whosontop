package com.jekmol.whosontop.ui.splash;

public interface SplashScreenContract {

    interface View {

        void goToMainScreen();

    }

    interface Presenter {

        void doSplash(Integer delay);

    }

}
