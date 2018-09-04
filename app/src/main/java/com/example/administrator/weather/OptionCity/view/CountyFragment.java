package com.example.administrator.weather.OptionCity.view;

import android.annotation.SuppressLint;

import com.example.administrator.weather.OptionCity.model.City;
import com.example.administrator.weather.OptionCity.presenter.IPresenter;
import com.example.administrator.weather.OptionCity.presenter.Presenter;

import org.litepal.LitePal;

import java.util.List;

@SuppressLint("ValidFragment")
public class CountyFragment extends BaseFragment {
    IPresenter iPresenter;
    int provinceId;
    int cityId;
    @SuppressLint("ValidFragment")
    public CountyFragment(int cityId, int provinceId){
        this.cityId=cityId;
        this.provinceId=provinceId;
    }

    @Override
    void init() {
        iPresenter=new Presenter(this);
        iPresenter.exchangeData("http://guolin.tech/api/china/"+provinceId+"/"+cityId,provinceId,cityId);
        City city=(LitePal.where("cityCode=?",String.valueOf(cityId)).find(City.class)).get(0);
        title.setText(city.getCityName());
    }

    @Override
    public void getPic(String data) {

    }

    @Override
    public void showData(List list) {
        cityAdapter=new CityAdapter(this.getActivity(),list,2);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recyclerView.setAdapter(cityAdapter);
                hideLoading();
            }
        });

    }
}
