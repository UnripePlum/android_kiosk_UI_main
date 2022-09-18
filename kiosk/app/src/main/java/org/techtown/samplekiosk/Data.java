package org.techtown.samplekiosk;

import android.os.Parcelable;
import android.os.Parcel;

import java.util.SimpleTimeZone;

public class Data implements Parcelable {
    String name;
    int cost;
    int count;
    int size;

    public Data(String n, int count, int cost){
        name = n;
        this.count = count;
        this.cost = cost;
    }

    public Data(Parcel src){
        name = src.readString();
        count = src.readInt();
        cost = src.readInt();
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
      public Data createFromParcel(Parcel in){
          return new Data(in);
      }
      public Data[] newArray(int size){
          return new Data[size];
      }
    };
    public  int describeContents(){
        return 0;
    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(count);
        parcel.writeInt(cost);

    }
}
