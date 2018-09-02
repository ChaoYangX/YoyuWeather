package com.example.administrator.weather.OptionCity.view;

import android.content.Context;

import com.example.administrator.weather.OptionCity.model.Province;

import java.util.List;

public interface IView {
    void showLoading();
    void hideLoading();
    void showToast(String msg);
    void showErr();

}
