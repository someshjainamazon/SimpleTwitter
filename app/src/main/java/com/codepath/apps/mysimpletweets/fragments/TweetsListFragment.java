package com.codepath.apps.mysimpletweets.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApp;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.adapters.TweetsTimeLineAdapter;
import com.codepath.apps.mysimpletweets.models.Tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by somesh on 2/14/15.
 */
public class TweetsListFragment extends Fragment {

    protected TweetsTimeLineAdapter timelineAdapter;
    protected ArrayList<Tweet> tweetList;
    protected ListView lvTweets;
    //protected SwipeRefreshLayout swipeContainer;

    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        tweetList = new ArrayList<Tweet>();
        timelineAdapter = new TweetsTimeLineAdapter(getActivity(), tweetList);



    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragments_tweets_list, parent, false);
        lvTweets = (ListView)v.findViewById(R.id.lvTweets);
        lvTweets.setAdapter(timelineAdapter);
        //swipeContainer = (SwipeRefreshLayout)v.findViewById(R.id.swipeContainer);



        return v;
    }

    public void addAll(List<Tweet> tweets){
        timelineAdapter.addAll(tweets);
    }






}
