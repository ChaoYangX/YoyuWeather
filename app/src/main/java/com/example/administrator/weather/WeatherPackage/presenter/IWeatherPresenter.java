package com.example.administrator.weather.WeatherPackage.presenter;

import android.view.View;

import com.example.administrator.weather.OptionCity.model.County;

public interface IWeatherPresenter {
    void setPic();
    void getPicData(String data);
    void getModeldata(String data);
    void gsonData(String url);
    void setProvince(String provinceName,String cityName,String conutyName);
    void trnsmitFailure();
    void getProvince(String data);
    void getCity(String data,int provinceId);
    void  getCounty(String data,int cityI);
    void down(String url);
    void addWeatherCity(String weatherId, String countyName, int countyCode, View v);
    void hide(County county);
    void deleteWeatherCity(String countyName,County county);
}
