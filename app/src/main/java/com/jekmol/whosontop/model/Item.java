package com.jekmol.whosontop.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Item implements Parcelable {

    @SerializedName("id") private int id;
    @SerializedName("joke") private String joke;
    @SerializedName("categories") private String[] category;

    public Item() {
    }

    public Item(int id, String joke, String[] category) {
        this.id = id;
        this.joke = joke;
        this.category = category;
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

    public String[] getCategory() {
        return category;
    }

    public void setCategory(String[] category) {
        this.category = category;
    }

    protected Item(Parcel in) {
        id = in.readInt();
        joke = in.readString();
        category = in.createStringArray();
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
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(joke);
        parcel.writeStringArray(category);
    }

}
