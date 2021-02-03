package com.ammarreal.realestates.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.ammarreal.realestates.roomdatabase.DateConverter;

@Entity(tableName = "user")
@TypeConverters({DateConverter.class})

public  class  HomeModel implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "image")
    private String image;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public static Creator<HomeModel> getCREATOR() {
        return CREATOR;
    }

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "desc")
    private String desc;

    @ColumnInfo(name = "price")
    private String price;

    @ColumnInfo(name = "size")
    private String size;

    @ColumnInfo(name = "address")
    private String addresse;

    @ColumnInfo(name = "phone")
    private String phone;

    public HomeModel() {
    }

    public @Ignore HomeModel(String image, String name, String desc, String price, String size, String addresse, String phone) {
        this.image = image;
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.size = size;
        this.addresse = addresse;
        this.phone = phone;
    }

    protected HomeModel(Parcel in) {
        image = in.readString();
        name = in.readString();
        desc = in.readString();
        price = in.readString();
        size = in.readString();
        addresse = in.readString();
        phone = in.readString();
    }
public static final Creator<HomeModel> CREATOR = new Creator<HomeModel>() {
        @Override
        public HomeModel createFromParcel(Parcel in) {
            return new HomeModel(in);
        }

        @Override
        public HomeModel[] newArray(int size) {
            return new HomeModel[size];
        }
    };

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getAddresse() {
        return addresse;
    }

    public void setAddresse(String addresse) {
        this.addresse = addresse;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image);
        dest.writeString(name);
        dest.writeString(desc);
        dest.writeString(price);
        dest.writeString(size);
        dest.writeString(addresse);
        dest.writeString(phone);
    }
}
