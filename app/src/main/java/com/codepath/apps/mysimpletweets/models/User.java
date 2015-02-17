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

    @Column(name = "tagline")
    private String tagLine;

    @Column(name = "follower")
    private int followerCount;

    public String getTagLine() {
        return tagLine;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public int getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }

    @Column(name = "following")
    private int followingCount;

    public int getTweetCount() {
        return tweetCount;
    }

    public void setTweetCount(int tweetCount) {
        this.tweetCount = tweetCount;
    }

    @Column(name = "tweetCount")
    private int tweetCount;


    // This is the unique id given by the server
    //@Column(name = "remote_id", unique = true)
    //public long remoteId;


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

    @Column(name = "profileUrl")
    private String profileImageUrl;

    public String getProfileBackGroundImageUrl() {
        return profileBackGroundImageUrl;
    }

    public void setProfileBackGroundImageUrl(String profileBackGroundImageUrl) {
        this.profileBackGroundImageUrl = profileBackGroundImageUrl;
    }

    @Column(name = "profileBackgroundImageUrl")
    private String profileBackGroundImageUrl;


    public static User fromJson(JSONObject jsonObject){
        User user = new User();
        User existingUser=null;
        try {
            user.name = jsonObject.getString("name");
            user.screenName = jsonObject.getString("screen_name");
            user.uid = jsonObject.getLong("id");
            user.profileImageUrl=jsonObject.getString("profile_image_url");
            user.tagLine=jsonObject.getString("description");
            user.followerCount=jsonObject.getInt("followers_count");
            user.followingCount=jsonObject.getInt("friends_count");
            user.profileBackGroundImageUrl=jsonObject.getString("profile_background_image_url");
            user.tweetCount=jsonObject.getInt("statuses_count");

            existingUser =new Select().from(User.class).where("screename = ?", user.screenName).executeSingle();
            if(existingUser==null){
                user.save();
                existingUser= new Select().from(User.class).where("screename = ?", user.screenName).executeSingle();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return existingUser;

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
        dest.writeString(tagLine);
        dest.writeInt(followerCount);
        dest.writeInt(followingCount);
        dest.writeString(profileBackGroundImageUrl);
        dest.writeInt(tweetCount);

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
        tagLine=in.readString();
        followerCount=in.readInt();
        followingCount=in.readInt();
        profileBackGroundImageUrl=in.readString();
        tweetCount=in.readInt();
    }

    public User() {
        //normal actions performed by class, it's still a normal object!
    }



    // Used to return items from another table based on the foreign key

    public List<Tweet> items() {
        return getMany(Tweet.class, "User");
    }

    public static User getUserFromTweet(long uid){
        return new Select()
                .from(Tweet.class)
                .where("uid = ?", uid)
                .executeSingle();

    }



}
