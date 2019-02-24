package com.jekmol.whosontop.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Item extends RealmObject implements Parcelable {

    @PrimaryKey
    @SerializedName("id") private int id;
    @SerializedName("joke") private String joke;
    @SerializedName("categories") private RealmList<String> category;

    public Item() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJoke() {
        return joke;
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }

    public RealmList<String> getCategory() {
        return category;
    }

    public void setCategory(RealmList<String> category) {
        this.category = category;
    }

    protected Item(Parcel in) {
        id = in.readInt();
        joke = in.readString();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(joke);
    }
}
