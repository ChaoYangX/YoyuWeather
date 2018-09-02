package com.example.administrator.weather.OptionCity.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.weather.OptionCity.model.Province;
import com.example.administrator.weather.R;

import java.util.List;

public class OptionCityActivity extends AppCompatActivity implements IView,View.OnClickListener {
     TextView visibility;
     ImageButton back;
    private static final String TAG = "OptionCityActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_city);
        ActionBar actionBar=getSupportActionBar();
        visibility=(TextView)findViewById(R.id.visibility_text);
        back=(ImageButton)findViewById(R.id.back_button);
        back.setOnClickListener(this);
        if(actionBar!=null)
            actionBar.hide();
         replaceFragment(new ProvinceFragment());
    }
    private void  replaceFragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.search_city_frame,fragment);
        fragmentTransaction.commit();
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
                Toast.makeText(OptionCityActivity.this,"网络错误",Toast.LENGTH_SHORT).show();
            }
        });

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
