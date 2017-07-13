package com.cetc.security.ddos.persistence;

import javax.persistence.*;

/**
 * Created by lb on 2016/5/12.
 */
@Entity
@Table(name="ipcity")
public class IpCityEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO) //表示id自动产生，采用自加的方式
    @Column(name="id")
    private int id;
    @Column(name="city")
    private String city;
    @Column(name="lng")
    private String lng;
    @Column(name="lat")
    private String lat;
    @Column(name="count")
    private int count;

    public IpCityEntity(String city, String lng, String lat, int count) {
        this.city = city;
        this.lng = lng;
        this.lat = lat;
        this.count = count;
    }


    public IpCityEntity() {
        this.city = "";
        this.lng = "";
        this.lat = "";
        this.count = 0;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
