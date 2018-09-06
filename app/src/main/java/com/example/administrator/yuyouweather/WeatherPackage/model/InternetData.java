package com.example.administrator.yuyouweather.WeatherPackage.model;

import com.example.administrator.yuyouweather.OptionCity.presenter.IPresenter;
import com.example.administrator.yuyouweather.Util.HttpUtil;
import com.example.administrator.yuyouweather.WeatherPackage.presenter.IWeatherPresenter;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class InternetData implements Imodel{
    IWeatherPresenter iWeatherPresenter;
    IPresenter iPresenter;
    private static final String TAG = "InternetData";
    public InternetData(IWeatherPresenter iWeatherPresenter){
        this.iWeatherPresenter=iWeatherPresenter;
    }
    public InternetData(IPresenter iPresenter){
        this.iPresenter=iPresenter;
    }

    @Override
    public void getBingPic() {
        HttpUtil.setOkHttpRequest("http://guolin.tech/api/bing_pic", new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                iWeatherPresenter.trnsmitFailure();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
               iWeatherPresenter.getPicData(response.body().string());
            }
        });
    }

    @Override
    public void getData(String url){
        HttpUtil.setOkHttpRequest(url, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
              iWeatherPresenter.trnsmitFailure();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
               iWeatherPresenter.getModeldata(response.body().string());
            }
        });
    }

    @Override
    public void getprivince() {
        HttpUtil.setOkHttpRequest("http://guolin.tech/api/china", new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                iWeatherPresenter.trnsmitFailure();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                iWeatherPresenter.getProvince(response.body().string());

            }
        });
    }

    @Override
    public void getcity(final String url) {
        HttpUtil.setOkHttpRequest("http://guolin.tech/api/china/"+url, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                iWeatherPresenter.trnsmitFailure();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                iWeatherPresenter.getCity(response.body().string(),Integer.valueOf(url));
            }
        });
    }

    @Override
    public void getFragmentBingPic() {
        HttpUtil.setOkHttpRequest("http://guolin.tech/api/bing_pic", new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                iWeatherPresenter.trnsmitFailure();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
               iPresenter.setPicData(response.body().string());
            }
        });
    }

    @Override
    public void getcounty(final String url,final int cityId) {
        HttpUtil.setOkHttpRequest("http://guolin.tech/api/china/"+url, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                iWeatherPresenter.trnsmitFailure();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                iWeatherPresenter.getCounty(response.body().string(),cityId);
            }
        });
    }


}
