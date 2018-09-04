package com.example.administrator.weather.WeatherPackage.model;
import org.litepal.crud.LitePalSupport;

public class WeatherCity  extends LitePalSupport{
    int weatherCountyCode;
    String weatherId;

    public int getWeatherCountyCode() {
        return weatherCountyCode;
    }

    public void setWeatherCountyCode(int weatherCountyCode) {
        this.weatherCountyCode = weatherCountyCode;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }


    public String getWeatherCountyName() {
        return weatherCountyName;
    }

    public void setWeatherCountyName(String weatherCountyName) {
        this.weatherCountyName = weatherCountyName;
    }

    String weatherCountyName;
}
