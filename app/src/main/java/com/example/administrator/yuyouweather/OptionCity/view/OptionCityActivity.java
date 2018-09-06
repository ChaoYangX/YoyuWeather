package com.example.administrator.yuyouweather.OptionCity.view;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.yuyouweather.OptionCity.presenter.IPresenter;
import com.example.administrator.yuyouweather.OptionCity.presenter.Presenter;
import com.example.administrator.weather.R;

import java.util.List;

public class OptionCityActivity extends AppCompatActivity implements IView,View.OnClickListener {
     TextView visibility;
     ImageButton back;
     ImageView ping;
     IPresenter iPresenter=new Presenter(this);
    private static final String TAG = "OptionCityActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>=21){
            View decorView=getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_option_city);
        ActionBar actionBar=getSupportActionBar();
        visibility=(TextView)findViewById(R.id.visibility_text);
        ping=(ImageView)findViewById(R.id.bing_pic_option);
        back=(ImageButton)findViewById(R.id.back_button);
        back.setOnClickListener(this);
        if(actionBar!=null)
            actionBar.hide();
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.search_city_frame,new ProvinceFragment());
        fragmentTransaction.commit();
        SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(this);
        String bingpic=preferences.getString("bing_pic",null);
        if(bingpic==null){
            iPresenter.getPicData();
        }else {
            Glide.with(this).load(bingpic).into(ping);
        }


    }
    public void  replaceFragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.search_city_frame,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
}

    @Override
    public void getPic(final String data) {
        SharedPreferences.Editor editor= PreferenceManager.getDefaultSharedPreferences(OptionCityActivity.this).edit();
        editor.putString("bing_pic",data);
        editor.apply();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Glide.with(OptionCityActivity.this).load(data).into(ping);
            }
        });
    }

    @Override
    public void showLoading() {
        visibility.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        visibility.setVisibility(View.GONE);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErr() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(OptionCityActivity.this,"网络错误",Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void showData(List list) {

    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){
          case R.id.back_button:
             onBackPressed();
           break;

       }
    }
}
