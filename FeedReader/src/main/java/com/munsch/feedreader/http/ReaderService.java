/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munsch.feedreader.http;

import com.munsch.feedreader.models.RSSFeed;
import com.munsch.feedreader.models.RespAuth;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;

import java.io.Serializable;

/**
 *
 * @author Munsch
 */
public interface ReaderService {

    class LoginInformation {

      public String email;
      public String password;

       public LoginInformation(String email, String password) {
           this.email = email;
           this.password = password;
       }
   }

    @POST("/auth/login")
    Call<RespAuth> login(@Body LoginInformation creds);

    @PUT("/me/feeds")
    Call<RSSFeed> addFeed(@Body RSSFeed feed);
}