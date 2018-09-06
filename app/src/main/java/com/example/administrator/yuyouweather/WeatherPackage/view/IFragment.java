package com.example.administrator.yuyouweather.WeatherPackage.view;

import android.view.View;

import com.example.administrator.yuyouweather.OptionCity.model.County;
import com.example.administrator.yuyouweather.OptionCity.view.IView;
import com.example.administrator.yuyouweather.WeatherPackage.model.WeatherCity;
import com.example.administrator.yuyouweather.WeatherPackage.model.gson.Weather;

public interface IFragment extends IView {
 void getWeatherId( County county);
 void showWeatherInfo(Weather weather, String data);
 void showFloating();
 void hideFloading();
 void setFragment(WeatherCity weatherCity);
 void delete(String countyName);
 void showSnakebar(View view,String content);
}
