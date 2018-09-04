package com.example.administrator.weather.OptionCity.presenter;

import android.util.Log;

import com.example.administrator.weather.OptionCity.model.City;
import com.example.administrator.weather.OptionCity.model.County;
import com.example.administrator.weather.OptionCity.model.Province;
import com.example.administrator.weather.OptionCity.view.IView;
import com.example.administrator.weather.WeatherPackage.model.Imodel;
import com.example.administrator.weather.WeatherPackage.model.InternetData;
import com.example.administrator.weather.WeatherPackage.view.IFragment;
import com.example.administrator.weather.Util.HttpUtil;
import com.example.administrator.weather.Util.JsonUtil;

import org.litepal.LitePal;
import java.io.IOException;
import java.util.List;
import okhttp3.Call;
import okhttp3.Response;
public class Presenter implements IPresenter {
    IView iView;
    List list;
    Imodel imodel=new InternetData(this);
    private static final String TAG = "Presenter";
    public Presenter(IView iView){
        this.iView=iView;
    }

    @Override
    public void setPicData(String data) {
        iView.getPic(data);
    }

    @Override
    public void getPicData() {
        imodel.getFragmentBingPic();
    }

    @Override
    public void exchangeData(String url) {
        list=LitePal.findAll(Province.class);
        if (list.size()==0){
            iView.showLoading();
          HttpUtil.setOkHttpRequest(url, new okhttp3.Callback() {
              @Override
              public void onFailure(Call call, IOException e) {
                     iView.showErr();
              }
              @Override
              public void onResponse(Call call, Response response) throws IOException {
                  String data= response.body().string();
                  Boolean b=false;
                  b=JsonUtil.jsonProvince(data);
                  list=LitePal.findAll(Province.class);
                  if(b){
                      iView.showData(list);
                  }
              }
          });}
          else{
            iView.showData(list);
        }
    }

    @Override
    public void exchangeData(String url, final int provinceId) {
        list=LitePal.where("provinceId=?",String.valueOf(provinceId)).find(City.class);
        if(list.size()==0){
            iView.showLoading();
        HttpUtil.setOkHttpRequest(url, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                iView.showErr();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data= response.body().string();
                Log.d(TAG, "onResponse: "+data);
                Boolean b=false;
                b=JsonUtil.jsonCity(data,provinceId);
                list=LitePal.where("provinceId=?",String.valueOf(provinceId)).find(City.class);
                if(b){
                    iView.showData(list);
                }
            }
        });}
        else {
            iView.showData(list);
        }
    }

    @Override
    public void exchangeData(final String url, final int provinceId, final int cityId) {
        list=LitePal.where("cityId=?",String.valueOf(cityId)).find(County.class);
        if(list.size()==0){
            iView.showLoading();
        HttpUtil.setOkHttpRequest(url, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                iView.showErr();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data= response.body().string();
                Boolean b=false;
                b=JsonUtil.jsonCounty(data,cityId);
                list=LitePal.where("cityId=?",String.valueOf(cityId)).find(County.class);
                if(b){
                    iView.showData(list);
                }
            }
        });}
        else {iView.showData(list);}
    }
}
