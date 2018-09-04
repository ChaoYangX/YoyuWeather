package com.example.administrator.weather.WeatherPackage.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.administrator.weather.OptionCity.model.County;
import com.example.administrator.weather.R;
import com.example.administrator.weather.WeatherPackage.model.WeatherCity;
import com.example.administrator.weather.WeatherPackage.model.gson.Weather;
import com.example.administrator.weather.WeatherPackage.presenter.IWeatherPresenter;
import com.example.administrator.weather.WeatherPackage.presenter.WeatherPresenter;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class WeatherFragment extends Fragment{
    public ImageView pic;
    TextView lbsText;
    RecyclerView lbsRecycleView;
    WeatherAdapter weatherAdapter;
    List<WeatherCity> weatherCityList=new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    private static final String TAG = "WeatherFragment";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.weather_fragment,container,false);
        pic=(ImageView)view.findViewById(R.id.bing_pic_fragment);

        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getActivity());
        String bingpic=preferences.getString("bing_pic",null);
       if(bingpic!=null) {
           Glide.with(this).load(bingpic).into(pic);
       }
       lbsRecycleView=(RecyclerView)view.findViewById(R.id.weather_recyclerView);
       lbsText=(TextView)view.findViewById(R.id.lbs_city_text);
       weatherCityList=LitePal.findAll(WeatherCity.class);
       weatherAdapter=new WeatherAdapter(weatherCityList);
       linearLayoutManager=new LinearLayoutManager(this.getActivity());
       lbsRecycleView.setLayoutManager(linearLayoutManager);
       lbsRecycleView.setAdapter(weatherAdapter);
        return view;
    }
    public void update(WeatherCity weatherCity) {
        weatherCityList.add(weatherCity);
        weatherAdapter.notifyItemInserted(weatherCityList.size()-1);
    }
}
