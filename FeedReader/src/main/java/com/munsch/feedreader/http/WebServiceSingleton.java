/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munsch.feedreader.http;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.HashSet;

/**
 *
 * @author Munsch
 */
public class WebServiceSingleton {

    private static WebServiceSingleton instance;
    private Retrofit retrofit;
    private String host;
    private ReaderService service;
    private HashSet<String> cookies = new HashSet<>();

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

                    OkHttpClient client = new OkHttpClient.Builder()
                            .addInterceptor(new AddCookiesInterceptor())
                            .addInterceptor(new ReceivedCookiesInterceptor())
                            .build();

                    ObjectMapper om = new ObjectMapper();
                    om.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
                    Gson gson = new GsonBuilder()
                            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                            .create();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://localhost:8080")
                            .client(client)
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .addConverterFactory(JacksonConverterFactory.create(om))
                            .build();

                    instance.service = retrofit.create(ReaderService.class);
                }
            }
        }
        return instance;
    }

    public HashSet<String> getCookies() {
        return cookies;
    }

    public void setCookies(HashSet<String> cookies) {
        this.cookies = cookies;
    }
}