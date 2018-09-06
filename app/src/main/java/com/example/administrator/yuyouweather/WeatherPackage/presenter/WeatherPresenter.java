package com.example.administrator.yuyouweather.WeatherPackage.presenter;
import android.view.View;

import com.example.administrator.yuyouweather.OptionCity.model.City;
import com.example.administrator.yuyouweather.OptionCity.model.County;
import com.example.administrator.yuyouweather.OptionCity.model.Province;
import com.example.administrator.yuyouweather.Util.JsonUtil;
import com.example.administrator.yuyouweather.WeatherPackage.model.WeatherCity;
import com.example.administrator.yuyouweather.WeatherPackage.model.gson.Weather;
import com.example.administrator.yuyouweather.WeatherPackage.view.IFragment;
import com.example.administrator.yuyouweather.WeatherPackage.model.Imodel;
import com.example.administrator.yuyouweather.WeatherPackage.model.InternetData;

import org.litepal.LitePal;

public class WeatherPresenter implements IWeatherPresenter {
    String provinceName;
    String cityName;
    String countyName;
    IFragment iFragment;
    Imodel imodel=new InternetData(this);
    private static final String TAG = "WeatherPresenter";
    public WeatherPresenter(IFragment iFragment){this.iFragment=iFragment;}


    @Override
    public void setPic() {
        imodel.getBingPic();
    }

    @Override
    public void getPicData(String data) {
        iFragment.getPic(data);
    }

    @Override
    public void getModeldata(String data) {
        Weather weather = JsonUtil.handleWeatherResponse(data);
         iFragment.showWeatherInfo(weather,data);
    }



    @Override
    public void gsonData(String url) {
        iFragment.showLoading();
        imodel.getData(url);
    }

    @Override
    public void setProvince(String provinceName,String cityName,String countyName) {
        this.provinceName=provinceName;
        this.cityName=cityName;
        this.countyName=countyName;
        if ((LitePal.findAll(Province.class)).size()==0){
        imodel.getprivince();
        }else {
            Province province= (LitePal.where("provinceName=?",provinceName).find(Province.class)).get(0);
                  if((LitePal.where("cityName=?",cityName).find(City.class)).size()==0) {
                         imodel.getcity(String.valueOf(province.getProvinceCode())); }
                  else { City city=(LitePal.where("cityName=?",cityName).find(City.class)).get(0);

                           if((LitePal.where("countyName=?",countyName).find(County.class)).size()==0) {
                                 imodel.getcounty(province.getProvinceCode()+"/"+city.getCityCode(),city.getCityCode()); }

                           else { County county=(LitePal.where("countyName=?",countyName).find(County.class)).get(0);
                                    iFragment.getWeatherId(county); }
                      }
               }
    }

    @Override
    public void trnsmitFailure() {
        iFragment.showErr();
    }

    @Override
    public void getProvince(String data) {
        JsonUtil.jsonProvince(data);
        Province province= (LitePal.where("provinceName=?",provinceName).find(Province.class)).get(0);
        if((LitePal.where("cityName=?",cityName).find(City.class)).size()==0){
            imodel.getcity(String.valueOf(province.getProvinceCode()));
               }  else { City city=(LitePal.where("cityName=?",cityName).find(City.class)).get(0);

            if((LitePal.where("countyName=?",countyName).find(County.class)).size()==0) {
                imodel.getcounty(province.getProvinceCode()+"/"+city.getCityCode(),city.getCityCode()); }

            else { County county=(LitePal.where("countyName=?",countyName).find(County.class)).get(0);
                iFragment.getWeatherId(county); }
        }

    }

    @Override
    public void getCity(String data,int provinceId) {
        JsonUtil.jsonCity(data,provinceId);
        City city=(LitePal.where("cityName=?",cityName).find(City.class)).get(0);
        if((LitePal.where("countyName=?",countyName).find(County.class)).size()==0){
            imodel.getcounty(provinceId+"/"+city.getCityCode(),city.getCityCode());
        } else { County county=(LitePal.where("countyName=?",countyName).find(County.class)).get(0);
            iFragment.getWeatherId(county); }
    }

    @Override
    public void getCounty(String data,int cityId) {
           JsonUtil.jsonCounty(data,cityId);
           County county;
           if((LitePal.where("countyName=?",countyName).find(County.class)).size()==0){
               if((LitePal.where("countyName=?",cityName).find(County.class)).size()==0){
                   county=LitePal.findFirst(County.class);
               }else {
           county=(LitePal.where("countyName=?",cityName).find(County.class)).get(0);}
           }
           else {
           county=(LitePal.where("countyName=?",countyName).find(County.class)).get(0);}
           iFragment.getWeatherId(county);
    }

    @Override
    public void down(String url) {
        imodel.getData(url);
    }

    @Override
    public void addWeatherCity(String weatherId, String countyName, int countyCode,View v) {
        WeatherCity weatherCity=new WeatherCity();
        weatherCity.setWeatherCountyCode(countyCode);
        weatherCity.setWeatherId(weatherId);
        weatherCity.setWeatherCountyName(countyName);
        weatherCity.save();
        iFragment.showSnakebar(v,"保存成功");
        WeatherCity weatherCity1=LitePal.where("weatherCountyName=?",countyName).find(WeatherCity.class).get(0);
        iFragment.setFragment(weatherCity1);
    }

    @Override
    public void hide(County county) {
      if((LitePal.where("weatherCountyName=?",county.getCountyName()).find(WeatherCity.class)).size()==0)
          iFragment.showFloating();
      else iFragment.hideFloading();
    }

    @Override
    public void deleteWeatherCity(String countyName,County county) {
         WeatherCity weatherCity=(LitePal.where("weatherCountyName=?",countyName).find(WeatherCity.class)).get(0);
          weatherCity.delete();
          if (county.getCountyName().equals(countyName)){
              iFragment.showFloating();
          }
    }

}
