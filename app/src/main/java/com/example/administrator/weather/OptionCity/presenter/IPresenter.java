package com.example.administrator.weather.OptionCity.presenter;

import com.example.administrator.weather.OptionCity.view.IView;

public interface IPresenter {
       void setPicData(String data);
       void getPicData();
       void exchangeData(String url);
       void exchangeData(String url,int provinceId);
       void exchangeData(String url,int provinceId,int CityId);
}
