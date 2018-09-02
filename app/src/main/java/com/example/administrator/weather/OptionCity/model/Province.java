package com.example.administrator.weather.OptionCity.model;

import com.example.administrator.weather.Util.HttpUtil;

import org.litepal.crud.LitePalSupport;

import java.io.IOException;
import java.util.logging.Handler;

import okhttp3.Call;
import okhttp3.Response;

public class Province extends LitePalSupport{


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    int id;

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }


    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }

    String provinceName;
    int provinceCode;

}
