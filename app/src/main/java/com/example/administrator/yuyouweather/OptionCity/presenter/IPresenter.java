package com.example.administrator.yuyouweather.OptionCity.presenter;

public interface IPresenter {
       void setPicData(String data);
       void getPicData();
       void exchangeData(String url);
       void exchangeData(String url,int provinceId);
       void exchangeData(String url,int provinceId,int CityId);
}
