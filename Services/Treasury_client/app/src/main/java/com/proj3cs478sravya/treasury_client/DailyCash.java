package com.proj3cs478sravya.treasury_client;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sravya on 4/29/2018.
 */

public class DailyCash implements Parcelable {
    int mDay = 25 ;
    int mMonth = 4 ;
    int mYear = 2018 ;
    int mCash = 8988 ;
    String mDayOfWeek = "Wednesday" ;

    public DailyCash() {

    }

    public DailyCash(Parcel in) {
        mDay = in.readInt() ;
        mMonth = in.readInt() ;
        mYear = in.readInt() ;
        mCash = in.readInt() ;
        mDayOfWeek = in.readString() ;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mDay);
        out.writeInt(mMonth) ;
        out.writeInt(mYear) ;
        out.writeInt(mCash) ;
        out.writeString(mDayOfWeek) ;
    }

    public static final Parcelable.Creator<DailyCash> CREATOR
            = new Parcelable.Creator<DailyCash>() {

        public DailyCash createFromParcel(Parcel in) {
            return new DailyCash(in) ;
        }

        public DailyCash[] newArray(int size) {
            return new DailyCash[size];
        }
    };

    public int describeContents()  {
        return 0 ;
    }

}