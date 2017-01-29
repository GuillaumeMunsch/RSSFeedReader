/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munsch.feedreader.http;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 * @author Munsch
 */
public class WebServiceSingleton {

    private static WebServiceSingleton instance;
    private String host;
    private ReaderService service;
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public ReaderService getService() {
        return service;
    }

    public static WebServiceSingleton getInstance() {
        if (instance == null) {
            synchronized (WebServiceSingleton.class) {
                if (instance == null) {
                    instance = new WebServiceSingleton();
                    Retrofit retrofit = new Retrofit.Builder().baseUrl("http://www.socialhive.fr:4242/").addConverterFactory(GsonConverterFactory.create()).build();
                    instance.service = retrofit.create(ReaderService.class);
                }
            }
        }
        return instance;
    }

}