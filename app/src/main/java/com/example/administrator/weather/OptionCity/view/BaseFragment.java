package com.example.administrator.weather.OptionCity.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.weather.R;
import com.example.administrator.weather.WeatherPackage.view.IFragment;

public abstract class BaseFragment extends Fragment implements IView {
    protected Context context;
    protected View mRootView;
    CityAdapter cityAdapter;
    RecyclerView recyclerView;
    TextView title;
    LinearLayoutManager linearLayoutManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView=inflater.inflate(R.layout.city_fragment,container,false);
        this.context=getActivity();
        title=(TextView)getActivity().findViewById(R.id.title_city);
        recyclerView=(RecyclerView)mRootView.findViewById(R.id.city_recyclerView);
        linearLayoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        init();
        return mRootView;
    }
    abstract void init();

    @Override
    public void showLoading() {
        checkActivity();
        ((OptionCityActivity)context).showLoading();
    }

    @Override
    public void hideLoading() {
       checkActivity();
        ((OptionCityActivity)context).hideLoading();
    }

    @Override
    public void showToast(String msg) {
        checkActivity();
        ((OptionCityActivity)context).showToast(msg);
    }

    @Override
    public void showErr() {
        checkActivity();
        ((OptionCityActivity)context).showErr();
    }
    public void checkActivity(){
        if(getActivity()==null){
            throw  new ActivityNotFoundException();
        }
    }
    public static class ActivityNotFoundException extends RuntimeException{
        public ActivityNotFoundException(){
            super("Fragment has disconnected from Activity!");
        }

    }
}
