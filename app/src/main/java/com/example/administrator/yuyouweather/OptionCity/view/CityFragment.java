package com.example.administrator.yuyouweather.OptionCity.view;

import android.annotation.SuppressLint;

import com.example.administrator.yuyouweather.OptionCity.presenter.IPresenter;
import com.example.administrator.yuyouweather.OptionCity.presenter.Presenter;

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
