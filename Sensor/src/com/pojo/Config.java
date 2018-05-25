package com.pojo;

public class Config {
    private String username;
    private float temp_min;
    private float temp_max;
    private float ph_min;
    private float ph_max;


    public float getPh_max() {
        return ph_max;
    }

    public void setPh_max(float ph_max) {
        this.ph_max = ph_max;
    }

    private float hum_min;
    private float hum_max;
    private float oxy_min;
    private float oxy_max;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public float getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(float temp_min) {
        this.temp_min = temp_min;
    }

    public float getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(float temp_max) {
        this.temp_max = temp_max;
    }

    public float getPh_min() {
        return ph_min;
    }

    public void setPh_min(float ph_min) {
        this.ph_min = ph_min;
    }


    public float getHum_min() {
        return hum_min;
    }

    public void setHum_min(float hum_min) {
        this.hum_min = hum_min;
    }

    public float getHum_max() {
        return hum_max;
    }

    public void setHum_max(float hum_max) {
        this.hum_max = hum_max;
    }

    public float getOxy_min() {
        return oxy_min;
    }

    public void setOxy_min(float oxy_min) {
        this.oxy_min = oxy_min;
    }

    public float getOxy_max() {
        return oxy_max;
    }

    public void setOxy_max(float oxy_max) {
        this.oxy_max = oxy_max;
    }
}
