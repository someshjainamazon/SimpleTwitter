package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApp;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.listeners.EndlessScrollListener;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by somesh on 2/14/15.
 */
public class HomeTimelineFragment extends TweetsListFragment {


    private long max_id;
    private TwitterClient twitterClient;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        twitterClient = TwitterApp.getRestClient(); // singleton client
        populateTimeline();


        /*
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                populateTimeline();

            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);*/

    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, parent, savedInstanceState);
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                loadMoreData();
            }
        });

        return v;

    }

    private void loadMoreData() {

        if(true) {
            twitterClient.getHomeTimeLineInfinite(new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                    addAll(Tweet.constructArrayFromJsonTweets(response));
                    max_id = tweetList.get(tweetList.size() - 1).getUid();
                    //swipeContainer.setRefreshing(false);

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                    System.out.println(errorResponse);
                    /*List<Tweet> tweets = Tweet.recentTweets();

                    tweetList=Tweet.getTweetArrayList(tweets);
                    timelineAdapter.addAll(tweetList);
                    max_id = tweetList.get(tweetList.size() - 1).getUid();*/
                    //swipeContainer.setRefreshing(false);


                }
            }, max_id, false);

        }else {
            List<Tweet> tweets = Tweet.recentTweets();
            System.out.println("hello");
        }
    }



    private void populateTimeline() {
        if(true) {
            twitterClient.getHomeTimeLine(new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                    addAll(Tweet.constructArrayFromJsonTweets(response));

                    max_id = tweetList.get(tweetList.size() - 1).getUid();
                    //swipeContainer.setRefreshing(false);

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    /*List<Tweet> tweets = Tweet.recentTweets();

                    tweetList=Tweet.getTweetArrayList(tweets);
                    timelineAdapter.addAll(tweetList);
                    max_id = tweetList.get(tweetList.size() - 1).getUid();*/
                    //swipeContainer.setRefreshing(false);
                    System.out.println(errorResponse);



                }
            });
        }

    }

}
