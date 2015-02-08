package com.codepath.apps.mysimpletweets.models;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by somesh on 2/6/15.
 */

@Table(name = "User")
public class User extends Model implements Parcelable {

    @Column(name = "name")
    private String name;

    @Column(name = "user_id")
    private long uid;

    @Column(name = "screename")
    private String screenName;

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String profileImageUrl;


    public static User fromJson(JSONObject jsonObject){
        User user = new User();

        try {
            user.name = jsonObject.getString("name");
            user.screenName = jsonObject.getString("screen_name");
            user.uid = jsonObject.getLong("id");
            user.profileImageUrl=jsonObject.getString("profile_image_url");


            User existingUser =new Select().from(User.class).where("screename = ?", user.screenName).executeSingle();
            if(existingUser==null)user.save();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(name);
        dest.writeLong(uid);
        dest.writeString(screenName);
        dest.writeString(profileImageUrl);
    }

    public static final Parcelable.Creator<User> CREATOR
            = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };


    private User(Parcel in) {
        name=in.readString();
        uid=in.readLong();
        screenName=in.readString();
        profileImageUrl=in.readString();
    }

    public User() {
        //normal actions performed by class, it's still a normal object!
    }



    // Used to return items from another table based on the foreign key

    public List<User> items() {
        return getMany(User.class, "User");
    }



}
