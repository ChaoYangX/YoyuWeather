package com.example.administrator.weather.WeatherPackage.view;

import android.view.View;

import com.example.administrator.weather.OptionCity.model.County;
import com.example.administrator.weather.OptionCity.view.IView;
import com.example.administrator.weather.WeatherPackage.model.WeatherCity;
import com.example.administrator.weather.WeatherPackage.model.gson.Weather;

import java.util.List;

public interface IFragment extends IView {
 void getWeatherId( County county);
 void showWeatherInfo(Weather weather,String data);
 void showFloating();
 void hideFloading();
 void setFragment(WeatherCity weatherCity);
 void delete(String countyName);
 void showSnakebar(View view,String content);
}
