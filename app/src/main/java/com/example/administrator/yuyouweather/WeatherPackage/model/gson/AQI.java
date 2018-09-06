package com.example.administrator.yuyouweather.WeatherPackage.model.gson;

public class AQI {
    public AQICity city;

    public class AQICity{
        public String aqi;
        public String pm25;
    }
}
