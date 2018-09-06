package com.example.administrator.yuyouweather.OptionCity.view;

import java.util.List;

public interface IView {
    void   getPic(String data);
    void showLoading();
    void hideLoading();
    void showToast(String msg);
    void showErr();
    void showData(List list);

}
