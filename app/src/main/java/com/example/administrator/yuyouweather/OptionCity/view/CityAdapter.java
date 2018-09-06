package com.example.administrator.yuyouweather.OptionCity.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.yuyouweather.OptionCity.model.City;
import com.example.administrator.yuyouweather.OptionCity.model.County;
import com.example.administrator.yuyouweather.OptionCity.model.Province;
import com.example.administrator.weather.R;
import com.example.administrator.yuyouweather.WeatherPackage.view.weatherActivity;

import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {
    List<Province> provinceList;
    List<City> cityList;
    List<County> countyList;
    Province province;
    City city;
    County county;
    int currentLevel;
    Context context;
    private static final String TAG = "CityAdapter";
    public  static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        View view;
        public ViewHolder(View itemView) {
            super(itemView);
            view=itemView;
            textView=(TextView)itemView.findViewById(R.id.item_data);
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.city_item,parent,false);
        final   ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if(currentLevel==0){
            province=provinceList.get(position);
            holder.textView.setText(province.getProvinceName());
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OptionCityActivity activity;
                    activity= (OptionCityActivity) v.getContext();
                    CityFragment cityFragment=new CityFragment(provinceList.get(position).getProvinceCode(),provinceList.get(position).getProvinceName());
                   activity.replaceFragment(cityFragment);
                }
            });
        }
        else if(currentLevel==1){
            city=cityList.get(position);
            holder.textView.setText(city.getCityName());
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OptionCityActivity activity;
                    activity= (OptionCityActivity) v.getContext();
                    CountyFragment countyFragment=new CountyFragment(cityList.get(position).getCityCode(),cityList.get(position).getProvinceId());
                    activity.replaceFragment(countyFragment);
                }
            });
        }
        else  if(currentLevel==2){
            county=countyList.get(position);
            holder.textView.setText(county.getCountyName());
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OptionCityActivity  activity= (OptionCityActivity) v.getContext();
                    weatherActivity.getUrl(activity,countyList.get(position).getWeatherId());
                }
            });


        }
    }

    @Override
    public int getItemCount() {
        if(currentLevel==0)
            return provinceList.size();
        else if(currentLevel==1)
            return  cityList.size();
        else
            return  countyList.size();

    }
    public  CityAdapter(Context context, List list, int currentLevel){
        if(currentLevel==0)
        this.provinceList=list;
        else if(currentLevel==1){
            this.cityList=list;
        }
        else if(currentLevel==2)
            this.countyList=list;
        this.context=context;
        this.currentLevel=currentLevel;
    }
}
