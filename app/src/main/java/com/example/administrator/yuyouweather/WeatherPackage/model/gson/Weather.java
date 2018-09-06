package com.example.administrator.yuyouweather.WeatherPackage.model.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class                                                                                                                                                                  Weather {
    public String status;
    public Basic basic;
    public Suggestion suggestion;
    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;
    public Now now;
    public AQI aqi;
}
