package com.bootexample.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by antosha4e on 11.05.16.
 */
@RestController
public class ConfigurationController {
    @Value("${server.version}")
    protected String serverVersion;

    /**
     * Application version
     * @param request just request
     * @throws Exception
     */
    @RequestMapping(value = "/api/version", method = {RequestMethod.GET})
    public String version(HttpServletRequest request) throws Exception {
        return serverVersion;
    }
}