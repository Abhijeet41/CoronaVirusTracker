package com.abhi41.coronavirustracker.model;

import java.util.ArrayList;

public class StateModel {
    String state,active,confirmed,recovered,deaths,name,zone,district_confirmed;
    ArrayList<DistrictModel> districtModelArrayList;
   // private boolean expanded;
    public StateModel() {
    }

    public StateModel(String state, String active, String confirmed, String recovered, String deaths, String name, String zone,String district_confirmed) {
        this.state = state;
        this.active = active;
        this.confirmed = confirmed;
        this.recovered = recovered;
        this.deaths = deaths;
        this.name = name;
        this.zone = zone;
        this.district_confirmed = district_confirmed;
  //      this.expanded = false;
    }

    public ArrayList<DistrictModel> getDistrictModelArrayList() {
        return districtModelArrayList;
    }

    public void setDistrictModelArrayList(ArrayList<DistrictModel> districtModelArrayList) {
        this.districtModelArrayList = districtModelArrayList;
    }

    public String getDistrict_confirmed() {
        return district_confirmed;
    }

    public void setDistrict_confirmed(String district_confirmed) {
        this.district_confirmed = district_confirmed;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }
}
