package com.example.administrator.weather.Util;

import android.text.TextUtils;

import com.example.administrator.weather.OptionCity.model.City;
import com.example.administrator.weather.OptionCity.model.County;
import com.example.administrator.weather.OptionCity.model.Province;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonUtil {
    public static boolean jsonProvince(String data){
        if(!TextUtils.isEmpty(data)){
            try {
                JSONArray jsonArray=new JSONArray(data);
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    String name=jsonObject.getString("name");
                    int id=jsonObject.getInt("id");
                    Province province=new Province();
                    province.setProvinceCode(id);
                    province.setProvinceName(name);
                    province.save();
                }
            return true;
            }
            catch (Exception e){e.printStackTrace();}
        }
        return false;
    }
    public  static boolean jsonCity(String data,int provinceId){
        if(!TextUtils.isEmpty(data)){
            try {
             JSONArray jsonArray=new JSONArray(data);
             for (int i=0;i<jsonArray.length();i++){
                 JSONObject jsonObject=jsonArray.getJSONObject(i);
                 String name=jsonObject.getString("name");
                 int id=jsonObject.getInt("id");
                 City city=new City();
                 city.setCityCode(id);
                 city.setCityName(name);
                 city.setProvinceId(provinceId);
                 city.save();
             }
                return true;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }
    public static  boolean jsonCounty(String data,int cityId){
        if(!TextUtils.isEmpty(data)){
            try {
                JSONArray jsonArray=new JSONArray(data);
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    String name=jsonObject.getString("name");
                    int id=jsonObject.getInt("id");
                    String weatherId=jsonObject.getString("weather_id");
                    County county=new County();
                    county.setCountyCode(id);
                    county.setCountyName(name);
                    county.setCityId(cityId);
                    county.setWeatherId(weatherId);
                    county.save();
                }
                return true;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }



}
