package com.example.administrator.weather.OptionCity.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.example.administrator.weather.OptionCity.model.Province;
import com.example.administrator.weather.OptionCity.presenter.IPresenter;
import com.example.administrator.weather.OptionCity.presenter.Presenter;
import com.example.administrator.weather.R;

import java.util.List;
public class ProvinceFragment extends BaseFragment {
        IPresenter iPresenter;
        CityAdapter cityAdapter;
    @Override
    void init() {
        iPresenter=new Presenter(this);
        iPresenter.exchangeData("http://guolin.tech/api/china");
        title.setText("中国");

    }

    @Override
    public void showData(List list) {
        cityAdapter=new CityAdapter(this.getActivity(),list,0);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recyclerView.setAdapter(cityAdapter);
                hideLoading();
            }
        });

    }
}
