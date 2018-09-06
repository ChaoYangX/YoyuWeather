package com.example.administrator.yuyouweather.WeatherPackage.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bumptech.glide.Glide;
import com.example.administrator.yuyouweather.OptionCity.model.County;
import com.example.administrator.yuyouweather.OptionCity.view.OptionCityActivity;
import com.example.administrator.weather.R;
import com.example.administrator.yuyouweather.WeatherPackage.model.WeatherCity;
import com.example.administrator.yuyouweather.WeatherPackage.model.gson.Forecast;
import com.example.administrator.yuyouweather.WeatherPackage.model.gson.Weather;
import com.example.administrator.yuyouweather.WeatherPackage.presenter.IWeatherPresenter;
import com.example.administrator.yuyouweather.WeatherPackage.presenter.WeatherPresenter;
import com.example.administrator.yuyouweather.WeatherService;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class weatherActivity extends AppCompatActivity implements View.OnClickListener,IFragment{
    private ImageView bingPicImage;
public DrawerLayout drawerLayout;
public ProgressBar progressBar;
private SwipeRefreshLayout swipeRefreshLayout;
private FloatingActionButton floatingActionButton;
public IWeatherPresenter iWeatherPresenter=new WeatherPresenter(this);
private ImageView lbs;
private ScrollView weatherLayout;
private TextView titleCity;
private TextView updataTime;
private TextView degreeText;
private TextView weatherInfoText;
private LinearLayout forecastLayout;
private TextView aqiText;
private TextView pm25Text;
private TextView comfortText;
private TextView carWashText;
private TextView sportText;
public LocationClient locationClient;
public County county;
    WeatherFragment weatherFragment;
    private static final String TAG = "weatherActivity";
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0){
                    for(int result:grantResults){
                        if(result!= PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(weatherActivity.this,"必须同意全部权限才能运行本程序",Toast.LENGTH_SHORT);
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                }else {Toast.makeText(weatherActivity.this,"发生未知错误",Toast.LENGTH_SHORT); finish(); }
                break;
            default:
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationClient=new LocationClient(getApplicationContext());
        locationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                   String province=bdLocation.getProvince().substring(0,bdLocation.getProvince().indexOf("省"));
                   String city=bdLocation.getCity().substring(0,bdLocation.getCity().indexOf("市"));
                   String county;
                   if(bdLocation.getDistrict().indexOf("市")==-1)
                       county=bdLocation.getDistrict();
                   else
                       county=bdLocation.getDistrict().substring(0,bdLocation.getDistrict().indexOf("市"));
                     iWeatherPresenter.setProvince(province,city,county);
                     lbs.setVisibility(View.VISIBLE);
            }
        });
        if(Build.VERSION.SDK_INT>=21){
            View decorView=getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_weather);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null)
            actionBar.hide();
        bingPicImage=(ImageView)findViewById(R.id.bing_pic);
        floatingActionButton=(FloatingActionButton)findViewById(R.id.floating);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
        progressBar=(ProgressBar)findViewById(R.id.progress);
        lbs=(ImageView)findViewById(R.id.lbs_view);
        ImageButton add=(ImageButton)findViewById(R.id.add_button);
        ImageButton side=(ImageButton)findViewById(R.id.side_button);
        weatherLayout=(ScrollView)findViewById(R.id.weather_layout);
        updataTime=(TextView)findViewById(R.id.update_time);
        degreeText=(TextView)findViewById(R.id.degree_text);
        weatherInfoText=(TextView)findViewById(R.id.weather_info_text);
        forecastLayout=(LinearLayout)findViewById(R.id.forecast_Lineal);
        aqiText=(TextView)findViewById(R.id.aqi_text);
        pm25Text=(TextView)findViewById(R.id.pm25_text);
        comfortText=(TextView)findViewById(R.id.comfort_text);
        carWashText=(TextView)findViewById(R.id.car_wash_text);
        sportText=(TextView)findViewById(R.id.sport_text);
        drawerLayout=(DrawerLayout)findViewById(R.id.draw);
        add.setOnClickListener(this);
        side.setOnClickListener(this);
        floatingActionButton.setOnClickListener(this);
        titleCity=(TextView)findViewById(R.id.title_view);
        String weatherId=getIntent().getStringExtra("weatherId");

        if(weatherId!=null){
        County intentCounty=(LitePal.where("weatherId=?",weatherId).find(County.class)).get(0);
        lbs.setVisibility(View.GONE);
        this.getWeatherId(intentCounty);
        iWeatherPresenter.hide(intentCounty);
        }
        List<String> list=new ArrayList<>();
        if(ContextCompat.checkSelfPermission(weatherActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            list.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(weatherActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            list.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(ContextCompat.checkSelfPermission(weatherActivity.this,Manifest.permission.READ_PHONE_STATE)!=PackageManager.PERMISSION_GRANTED){
            list.add(   Manifest.permission.READ_PHONE_STATE);
        }
        if(!list.isEmpty()){
            String[] p=list.toArray(new String[list.size()]);
            ActivityCompat.requestPermissions(weatherActivity.this,p,1);
        }else {
            if(weatherId==null){
            requestLocation();}
        }
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                weatherLayout.setVisibility(View.GONE);
                iWeatherPresenter.down("http://guolin.tech/api/weather?cityid="+county.getWeatherId()+"&key=7717685dffb14d7c8b019337efe79463");
            }
        });


    }
    @Override
    public void getPic(final String data) {
        weatherFragment=(WeatherFragment)getSupportFragmentManager().findFragmentById(R.id.side_weather_fragment);
        SharedPreferences.Editor editor= PreferenceManager.getDefaultSharedPreferences(weatherActivity.this).edit();
       editor.putString("bing_pic",data);
       editor.apply();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Glide.with(weatherActivity.this).load(data).into(bingPicImage);
            }
        });
    }




    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.add_button:
                Intent intent=new Intent(weatherActivity.this, OptionCityActivity.class);
                startActivity(intent);
                break;
            case  R.id.side_button:
                 drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.floating:
                 iWeatherPresenter.addWeatherCity(county.getWeatherId(),county.getCountyName(),county.getCountyCode(),v);
                 this.hideFloading();
                break;
        }
    }




    @Override
    public void showLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
                weatherLayout.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        weatherLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void  showToast(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErr() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(weatherActivity.this,"网络错误",Toast.LENGTH_LONG).show();
            }
        });

    }
    @Override
    public void showSnakebar(View view,String content){
        Snackbar.make(view,content,Snackbar.LENGTH_SHORT).show();
    }


    @Override
    public void showData(List list) {

    }
    public static void getUrl(Context context ,String weatherId){
        Intent intent=new Intent(context,weatherActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("weatherId",weatherId);
        context.startActivity(intent);
    }


    @Override
    public void   getWeatherId(County county) {
        this.county=county;
        SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(this);
        final String bingpic=preferences.getString("bing_pic",null);
        if(bingpic==null){
            iWeatherPresenter.setPic();
        }else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Glide.with(weatherActivity.this).load(bingpic).into(bingPicImage);
                }
            });

       }
       // SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(this);
       // String weatherData=preferences.getString("weather"+countyName,null);
       // if(weatherData==null){
        iWeatherPresenter.gsonData("http://guolin.tech/api/weather?cityid="+county.getWeatherId()+"&key=7717685dffb14d7c8b019337efe79463");


        //   }
     //   else {
          //  Weather weather= JsonUtil.handleWeatherResponse(weatherData);
        //   showWeatherInfo(weather,weatherData);
     //  }
    }

    public void show(Weather weather){
        String cityName=weather.basic.cityName;
        String updateTimeData=weather.basic.update.updateTime.split(" ")[1];
        Log.d(TAG, "show: "+weather.basic.update.updateTime);
        String degree=weather.now.temperature+"℃";
        String weatherInfo=weather.now.more.info;
        titleCity.setText(cityName);
        updataTime.setText(updateTimeData);
        degreeText.setText(degree);
        weatherInfoText.setText(weatherInfo);
        forecastLayout.removeAllViews();
        for (Forecast forecast:weather.forecastList){
            View view= LayoutInflater.from(weatherActivity.this).inflate(R.layout.layout_forecast_item,forecastLayout,false);
            TextView dataText=(TextView)view.findViewById(R.id.data_text);
            TextView infoText=(TextView)view.findViewById(R.id.info_text);
            TextView maxText=(TextView)view.findViewById(R.id.max_text);
            TextView minText=(TextView)view.findViewById(R.id.min_text);
            dataText.setText(forecast.date);
            infoText.setText(forecast.more.info);
            maxText.setText(forecast.temperature.max);
            minText.setText(forecast.temperature.min);
            forecastLayout.addView(view);
        }
        if(weather.aqi!=null){
            aqiText.setText(weather.aqi.city.aqi);
            pm25Text.setText(weather.aqi.city.pm25);
        }
        String comfort="舒适度："+weather.suggestion.comfort.info;
        String carWash="洗车指数："+weather.suggestion.carWash.info;
        String sport="运动建议："+weather.suggestion.sport.info;
        comfortText.setText(comfort);
        carWashText.setText(carWash);
        sportText.setText(sport);
        swipeRefreshLayout.setRefreshing(false);
        this.hideLoading();
    }

    @Override
    public void showWeatherInfo(final Weather weather, final String data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(weather!=null&&"ok".equals(weather.status)){
                   // SharedPreferences.Editor editor= PreferenceManager.getDefaultSharedPreferences(weatherActivity.this).edit();
                  //  editor.putString("weather"+weather.basic.cityName,data);
                 //   editor.apply();
                    show(weather);
                    Intent intent=new Intent(weatherActivity.this,WeatherService.class);
                    startService(intent);
                }else {
                    Toast.makeText(weatherActivity.this,"获取天气信息失败",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public void showFloating() {
        floatingActionButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideFloading() {
        floatingActionButton.setVisibility(View.GONE);
    }

    @Override
    public void setFragment(WeatherCity weatherCity) {
        weatherFragment=(WeatherFragment)getSupportFragmentManager().findFragmentById(R.id.side_weather_fragment);
        weatherFragment.update(weatherCity);
    }

    @Override
    public void delete(String countyName) {
        iWeatherPresenter.deleteWeatherCity(countyName,county);
    }

    private void requestLocation(){
        initLocation();
        locationClient.start();}

    private void initLocation(){
        LocationClientOption option=new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);//网络模式定位
        option.setIsNeedAddress(true);  //获取当前位置详细信息
        locationClient.setLocOption(option);
    }


}
