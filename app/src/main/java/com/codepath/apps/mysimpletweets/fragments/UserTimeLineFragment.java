package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;

import com.codepath.apps.mysimpletweets.TwitterApp;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by somesh on 2/14/15.
 */
public class UserTimeLineFragment extends TweetsListFragment {

    private TwitterClient twitterClient;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        twitterClient = TwitterApp.getRestClient(); // singleton client
        populateTimeline();
    }

    private void populateTimeline() {
        String screenName = getArguments().getString("screen_name");

        if(true) {
            twitterClient.getUserTimeLine(new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                    addAll(Tweet.constructArrayFromJsonTweets(response));
                    //swipeContainer.setRefreshing(false);

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    /*List<Tweet> tweets = Tweet.recentTweets();

                    tweetList=Tweet.getTweetArrayList(tweets);
                    timelineAdapter.addAll(tweetList);
                    max_id = tweetList.get(tweetList.size() - 1).getUid();*/
                    System.out.println(errorResponse);

                    //swipeContainer.setRefreshing(false);


                }
            }, screenName);
        }

    }


    public static UserTimeLineFragment newInstance(String screenName) {
        UserTimeLineFragment fragment = new UserTimeLineFragment();
        Bundle args = new Bundle();
        args.putString("screen_name", screenName);
        fragment.setArguments(args);
        return fragment;
    }


}
