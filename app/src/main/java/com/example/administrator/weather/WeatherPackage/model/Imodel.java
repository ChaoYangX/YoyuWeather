package com.example.administrator.weather.WeatherPackage.model;

public interface Imodel {
    void getBingPic();
    void getData(String url);
    void getprivince();
    void getcity(String url);
    void getFragmentBingPic();
    void getcounty(String url,int cityId);
}
