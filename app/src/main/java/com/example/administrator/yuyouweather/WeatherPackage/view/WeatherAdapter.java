package com.example.administrator.yuyouweather.WeatherPackage.view;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.yuyouweather.OptionCity.model.County;
import com.example.administrator.weather.R;
import com.example.administrator.yuyouweather.WeatherPackage.model.WeatherCity;

import org.litepal.LitePal;

import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder>{
    List<WeatherCity> weatherCityList;
    private static final String TAG = "WeatherAdapter";
    public WeatherAdapter(List<WeatherCity> weatherCityList){
        this.weatherCityList=weatherCityList;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_item,parent,false);
       ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
          final WeatherCity weatherCity=weatherCityList.get(position);
          holder.weatherItemText.setText(weatherCity.getWeatherCountyName());
          holder.weatherItemImage.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  weatherActivity weather=(weatherActivity)v.getContext();
                  weather.delete(weatherCityList.get(position).getWeatherCountyName());
                  weather.showSnakebar(v,"删除成功");
                  weatherCityList.remove(position);
                  notifyItemRemoved(position);
                  notifyItemRangeChanged(position,weatherCityList.size());

              }
          });
          holder.view.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                 weatherActivity weather=(weatherActivity)v.getContext();
                 weather.drawerLayout.closeDrawers();
                 weather.showLoading();
                  County county=(LitePal.where("countyName=?",weatherCityList.get(position).getWeatherCountyName()).find(County.class)).get(0);
                 weather.getWeatherId(county);

              }
          });
          holder.view.setOnLongClickListener(new View.OnLongClickListener() {
              @Override
              public boolean onLongClick(View v) {
                  final weatherActivity weather=(weatherActivity)v.getContext();
                  holder.weatherItemImage.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                        }catch (Exception e){e.printStackTrace();}
                        Log.d(TAG, "run1: 1");
                        weather.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d(TAG, "run1: 2");
                                holder.weatherItemImage.setVisibility(View.GONE);
                            }
                        });
                    }
                }).start();
                  return true;
              }
          });

    }

    @Override
    public int getItemCount() {
        return weatherCityList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView weatherItemText;
        ImageButton weatherItemImage;
        View view;
       public ViewHolder(View itemView) {
           super(itemView);
           view=itemView;
           weatherItemText=(TextView)itemView.findViewById(R.id.weather_item_text);
           weatherItemImage=(ImageButton)itemView.findViewById(R.id.weather_item_image);

       }
   }


}
