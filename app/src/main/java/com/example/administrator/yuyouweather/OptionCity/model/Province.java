package com.example.administrator.yuyouweather.OptionCity.model;

import org.litepal.crud.LitePalSupport;

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
