package com.abhi41.coronavirustracker.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class DistrictModel extends ArrayList<Parcelable> implements Parcelable {
    String Zone,confirmed,DistrictName;

    public DistrictModel() {
    }

    protected DistrictModel(Parcel in) {
        Zone = in.readString();
        confirmed = in.readString();
        DistrictName = in.readString();
    }

    public static final Creator<DistrictModel> CREATOR = new Creator<DistrictModel>() {
        @Override
        public DistrictModel createFromParcel(Parcel in) {
            return new DistrictModel(in);
        }

        @Override
        public DistrictModel[] newArray(int size) {
            return new DistrictModel[size];
        }
    };

    public String getDistrictName() {
        return DistrictName;
    }

    public void setDistrictName(String districtName) {
        DistrictName = districtName;
    }

    public String getZone() {
        return Zone;
    }

    public void setZone(String zone) {
        Zone = zone;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Zone);
        dest.writeString(confirmed);
        dest.writeString(DistrictName);
    }
}
