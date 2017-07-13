package com.cetc.security.ddos.persistence.service;

import com.cetc.security.ddos.persistence.IpCityEntity;
import com.cetc.security.ddos.persistence.dao.IpCityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lb on 2016/5/12.
 */
@Service()
public class IpCityService {
    @Autowired
    private IpCityDao ipCityDao;

    public List<IpCityEntity> getIpCity() {
        return ipCityDao.findOrder();
    }

    public IpCityEntity getIpCity(String city) {
        return ipCityDao.findCity(city);
    }

    public void addCity(IpCityEntity e) {
        ipCityDao.insert(e);
    }

    public void delCityAll() {ipCityDao.deleteAll();}

    public void updateCity(IpCityEntity e){
        ipCityDao.update(e);
    }
}
