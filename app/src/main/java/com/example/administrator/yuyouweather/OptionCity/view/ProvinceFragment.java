package com.example.administrator.yuyouweather.OptionCity.view;

import com.example.administrator.yuyouweather.OptionCity.presenter.IPresenter;
import com.example.administrator.yuyouweather.OptionCity.presenter.Presenter;

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
    public void getPic(String data) {

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
