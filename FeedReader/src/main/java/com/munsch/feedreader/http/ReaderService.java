/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munsch.feedreader.http;

import com.munsch.feedreader.models.RespAuth;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 *
 * @author Munsch
 */
public interface ReaderService {

  public class LoginInformation {
      String email;
      String password;

      public LoginInformation(String email, String password){
          email = email;
          password = password;
      }
  }
    @Headers("Content-Type: application/json")
    @POST("auth/login")
  Call<RespAuth> login(@Body LoginInformation logsinInformation);
}