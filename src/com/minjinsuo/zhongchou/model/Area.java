package com.minjinsuo.zhongchou.model;

import java.io.Serializable;

public class Area implements Serializable {
    private String code;
    private String cityName, provinceName;
    private String pcode;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public Area() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public String toString() {
        return "Area [code=" + code + ", provinceName=" + provinceName + ",cityName=" + cityName + ", pcode=" + pcode + "]";
    }

}
