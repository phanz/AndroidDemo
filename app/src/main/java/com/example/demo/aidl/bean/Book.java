package com.example.demo.aidl.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by phanz on 2017/4/27.
 */

public class Book implements Parcelable{
    private String name;
    private int price;

    protected Book(Parcel in) {
        name = in.readString();
        price = in.readInt();
    }

    public Book(String name,int price){
        this.name = name;
        this.price = price;
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(price);
    }

    public void readFromParcel(Parcel dest){
        name = dest.readString();
        price = dest.readInt();
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
