package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {

    public String name;
    public String userName;
    public String profileImageUrl;

    public static User fromJson(JSONObject jsonObject){
        User user = new User();
        try {
            user.name = jsonObject.getString("name");
            user.userName = jsonObject.getString("screen_name");
            user.profileImageUrl = jsonObject.getString("profile_image_url_https");

        } catch (JSONException e) {
            e.printStackTrace();
        }
      return user;
}}
