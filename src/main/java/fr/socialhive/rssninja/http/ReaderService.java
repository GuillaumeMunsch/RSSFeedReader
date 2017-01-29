/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.socialhive.rssninja.http;

import fr.socialhive.rssninja.models.JSendResp;
import fr.socialhive.rssninja.models.Feed;
import fr.socialhive.rssninja.models.RSSFeed;
import fr.socialhive.rssninja.models.RespAuth;
import retrofit2.Call;
import retrofit2.http.*;

import java.io.Serializable;
import java.util.List;

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

    @DELETE("/me/feeds/{id}")
    Call<Void> removeFeed(@Path("id") int id);

    @POST("/auth/logout")
    Call<JSendResp> logout();

    @GET("/me/feeds")
    Call<List<RSSFeed>> getFeeds();

    @GET("/me/feeds/{id}")
    Call<Feed> getFeedItems(@Path("id") int feedId);
}