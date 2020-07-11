package com.example.instagramclone;

import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;

@ParseClassName("Post")
public class Post extends ParseObject {
    //define getters and setters based on keys (names of columns in parse dashboard) we've defined
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_USER = "user";
    public static final String KEY_CREATED_KEY = "createdAt";
    public static final String KEY_PROFILE_PIC = "profilepic";
    public static final String KEY_LIKES = "likes";
    private static final String TAG = Post.class.getSimpleName();

    public String getDescription(){
        //getString is a method defined in ParseObject class
        //looks inside post parseobject and pulls out attribute w/ key name of 'description'
        return getString(KEY_DESCRIPTION);
    }
    public void setDescription(String description){
        put(KEY_DESCRIPTION, description);
    }

    public ParseFile getImage(){
        return getParseFile(KEY_IMAGE);
    }
    public void setImage(ParseFile image){
        put(KEY_IMAGE, image);
    }

    public ParseUser getUser(){
        return getParseUser(KEY_USER);
    }
    public void setUser(ParseUser user){
        put(KEY_USER, user);
    }

    public ParseFile getProfilePic(){
        return getUser().getParseFile(KEY_PROFILE_PIC);
    }

    public JSONArray getLikedBy() {
        JSONArray a = getJSONArray(KEY_LIKES);
        if(a == null) {
            return new JSONArray();
        } else{
            return a;
        }
    }

    public boolean isLiked() {
        JSONArray a = getLikedBy();
        for (int i = 0; i < a.length(); i++){
            try{
                a.get(i);
                if (a.getJSONObject(i).getString("objectId").equals(ParseUser.getCurrentUser().getObjectId())) {
                    return true;
                }
            } catch(JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
