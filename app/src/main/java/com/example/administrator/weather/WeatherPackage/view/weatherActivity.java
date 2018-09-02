package com.example.administrator.weather.WeatherPackage.view;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.administrator.weather.OptionCity.view.OptionCityActivity;
import com.example.administrator.weather.R;

public class weatherActivity extends AppCompatActivity implements View.OnClickListener {
private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null)
            actionBar.hide();
        ImageButton add=(ImageButton)findViewById(R.id.add_button);
        ImageButton side=(ImageButton)findViewById(R.id.side_button);
        drawerLayout=(DrawerLayout)findViewById(R.id.draw);
        add.setOnClickListener(this);
        side.setOnClickListener(this);
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
        }
    }
}
