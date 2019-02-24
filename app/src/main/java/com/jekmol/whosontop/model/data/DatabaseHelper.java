package com.jekmol.whosontop.model.data;

import com.jekmol.whosontop.model.entity.Item;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class DatabaseHelper {

    private Realm realm;

    public DatabaseHelper() {
    }

    public void open() {
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("jokedb.realm")
                .build();
        realm = Realm.getInstance(configuration);
    }

    public void close() {
        realm.close();
    }

    public void addItem(Item item) {
        realm.executeTransaction(realm1 -> realm.insertOrUpdate(item));
    }

    public void deleteItem(Item item) {
        realm.executeTransaction(realm -> {
            Item object = realm.where(Item.class).equalTo("id", item.getId()).findFirst();
            object.deleteFromRealm();
        });
    }

    public void deleteAllItem() {
        realm.executeTransaction(realm -> {
            RealmResults<Item> results = realm.where(Item.class).findAll();
            results.deleteAllFromRealm();
        });
    }

    public ArrayList<Item> getAllItems() {
        RealmResults<Item> items = realm.where(Item.class).findAll();
        ArrayList<Item> itemArrayList = new ArrayList<>();
        itemArrayList.addAll(realm.copyFromRealm(items));
        return itemArrayList;
    }

}
