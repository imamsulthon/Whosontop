package com.jekmol.whosontop;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApp extends Application{

    RealmConfiguration realmConfiguration;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        realmConfiguration = new RealmConfiguration.Builder()
                .name("jokedb.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

}
