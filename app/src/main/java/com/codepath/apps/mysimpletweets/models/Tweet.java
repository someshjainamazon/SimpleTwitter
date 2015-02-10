package com.codepath.apps.mysimpletweets.models;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateUtils;

import com.activeandroid.Cache;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.activeandroid.util.SQLiteUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by somesh on 2/6/15.
 */


@Table(name="Tweet")
public class Tweet extends Model implements Parcelable{
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }


    @Column(name="body")
    private String body;

    @Column(name = "uid")//, unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private long uid;


    @Column(name="createTime")
    private String createdAt;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    @Column(name = "username")
    private String userName;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "screename")
    private String screenName;

    @Column(name = "profileUrl")
    private String profileImageUrl;


    public static Tweet fromJSON(JSONObject jsonObject){
        Tweet tweet = new Tweet();
        try {
            tweet.body=jsonObject.getString("text");
            tweet.uid=jsonObject.getLong("id");
            tweet.createdAt=jsonObject.getString("created_at");

            JSONObject userJson = jsonObject.getJSONObject("user");

            tweet.userName = userJson.getString("name");
            tweet.screenName = userJson.getString("screen_name");
            tweet.userId = userJson.getLong("id");
            tweet.profileImageUrl=userJson.getString("profile_image_url");
            tweet.save();
            return tweet;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tweet;
    }



    public static ArrayList<Tweet> constructArrayFromJsonTweets(JSONArray jsonArray){

        ArrayList <Tweet> tweets = new ArrayList<>();

        for (int i=0;i<jsonArray.length();i++){
            try {

                Tweet tweet = Tweet.fromJSON(jsonArray.getJSONObject(i));
                if(tweet!=null) tweets.add(tweet);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return  tweets;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(body);
        dest.writeLong(uid);
        dest.writeString(createdAt);
        dest.writeString(userName);
        dest.writeString(screenName);
        dest.writeLong(userId);
        dest.writeString(profileImageUrl);

    }

    public static final Parcelable.Creator<Tweet> CREATOR
            = new Parcelable.Creator<Tweet>() {
        @Override
        public Tweet createFromParcel(Parcel in) {
            return new Tweet(in);
        }

        @Override
        public Tweet[] newArray(int size) {
            return new Tweet[size];
        }
    };


    private Tweet(Parcel in) {
        body=in.readString();
        uid=in.readLong();
        createdAt=in.readString();
        userName=in.readString();
        screenName=in.readString();
        userId=in.readLong();
        profileImageUrl=in.readString();

    }


    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public static String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    public static String getShowTime(String rawJSonDate){
        String relTime = getRelativeTimeAgo(rawJSonDate);
        String timeNumber=null;
        String timeUnit=null;
        String[] parts = relTime.split(" ");


        if(relTime.contains(" ago")){
                timeNumber=parts[0];
                if (parts[1].contains("sec")) timeUnit = "s";
                else if (parts[1].contains("min")) timeUnit = "m";
                else timeUnit = "h";

                return timeNumber + timeUnit;
        }
        else if(relTime.contains("in ")) {
            timeNumber = parts[1];
            if (parts[2].contains("sec")) timeUnit = "s";
            else if (parts[2].contains("min")) timeUnit = "m";
            else timeUnit = "h";

            return timeNumber + timeUnit;
        }

        return null;
    }


    public Tweet(){
        super();
    }

    public static Tweet tweetsById(long id) {
        return new Select().from(Tweet.class).where("uid = ?", id).executeSingle();
    }

    public static List<Tweet> recentTweets() {
        return new Select().from(Tweet.class).orderBy("uid DESC").limit("300").execute();
    }


    public static ArrayList<Tweet> getTweetArrayList(List<Tweet> tweets) {

        ArrayList<Tweet> tweetArrayList = new ArrayList<>();

        for (int i=0;i<tweets.size();i++){
            tweetArrayList.add(tweets.get(i));
        }

        return tweetArrayList;
    }
}
