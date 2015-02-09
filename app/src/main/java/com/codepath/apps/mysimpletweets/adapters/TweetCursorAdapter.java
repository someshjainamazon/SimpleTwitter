package com.codepath.apps.mysimpletweets.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;

/**
 * Created by somesh on 2/7/15.
 */
public class TweetCursorAdapter extends CursorAdapter {


    public TweetCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView tvScreenName = (TextView) view.findViewById(R.id.tvMyUserNameHome);
        TextView tvTweetBody = (TextView) view.findViewById(R.id.tvTweetBody);
        TextView tvRelTime = (TextView) view.findViewById(R.id.tvRelTime);

        ImageView ivProfilePic = (ImageView) view.findViewById(R.id.ivProfilePic);


        // String temp = tweet.getRelativeTimeAgo(tweet.getCreatedAt());

        //tvScreenName.setText(tweet.getUser().getScreenName()+"");

        //tvScreenName.setText(cursor.getString(cursor.getColumnIndexOrThrow("screename")));
        //int uid = cursor.getInt(cursor.getColumnIndexOrThrow("uid"));

        int id = cursor.getInt(cursor.getColumnIndexOrThrow("Id"));
        String createTime=cursor.getString(cursor.getColumnIndexOrThrow("createTime"));
        Cursor userCursor = Tweet.fetchUser(createTime);
        String userString = cursor.getString(cursor.getColumnIndexOrThrow("User"));
        String tweetBody = cursor.getString(cursor.getColumnIndexOrThrow("body"));
        tvTweetBody.setText(cursor.getString(cursor.getColumnIndexOrThrow("body")));
        tvRelTime.setText(Tweet.getRelativeTimeAgo(cursor.getString(cursor.getColumnIndexOrThrow("createTime"))));
        //ivProfilePic.setImageResource(0);

        //Picasso.with(TimelineActivity.this).load(tweet.getUser().getProfileImageUrl()).into(ivProfilePic);

    }
}
