package com.cetc.backend.controller;

import com.cetc.security.ddos.persistence.service.IpCityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lb on 2016/5/11.
 */
@Controller
@RequestMapping("/ipLocation")
public class IpLocationController extends BaseController {
    @Autowired
    private IpCityService ipCityService;


    @RequestMapping("getIpCity")
    @ResponseBody
    public String getIpCity(HttpServletRequest request) {


        String json = toJson(ipCityService.getIpCity());

        System.out.println(json);

        return (json);
    }
}
