package com.example.administrator.weather.OptionCity.view;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;

import com.example.administrator.weather.OptionCity.model.City;
import com.example.administrator.weather.OptionCity.model.Province;
import com.example.administrator.weather.OptionCity.presenter.IPresenter;
import com.example.administrator.weather.OptionCity.presenter.Presenter;

import org.litepal.LitePal;

import java.util.List;

@SuppressLint("ValidFragment")
public class CityFragment extends BaseFragment{
    IPresenter iPresenter;
    int provinceId;
    String provinceName;
    @SuppressLint("ValidFragment")
    public CityFragment(int provinceId,String provinceName){
        this.provinceId=provinceId;
        this.provinceName=provinceName;
    }
    @Override
    void init() {
        iPresenter=new Presenter(this);
        iPresenter.exchangeData("http://guolin.tech/api/china/"+provinceId,provinceId);
        title.setText(provinceName);

    }

    @Override
    public void getPic(String data) {

    }

    @Override
    public void showData(List list) {
        cityAdapter=new CityAdapter(this.getActivity(),list,1);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recyclerView.setAdapter(cityAdapter);
                hideLoading();
            }
        });

    }
}
