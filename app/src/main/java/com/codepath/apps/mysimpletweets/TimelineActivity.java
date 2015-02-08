package com.codepath.apps.mysimpletweets;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.codepath.apps.mysimpletweets.adapters.TweetCursorAdapter;
import com.codepath.apps.mysimpletweets.adapters.TweetsTimeLineAdapter;
import com.codepath.apps.mysimpletweets.listeners.EndlessScrollListener;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TimelineActivity extends ActionBarActivity {


    private TwitterClient twitterClient;
    private TweetsTimeLineAdapter timelineAdapter;
    private ArrayList<Tweet> tweetList;
    private ListView lvTweets;
    private long max_id;
    private User myHandle;
    public static final int  REQUEST_RESULT=50;
    private SwipeRefreshLayout swipeContainer;
    private TweetCursorAdapter tweetCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("@someshjain1");

        twitterClient = TwitterApp.getRestClient(); // singleton client
        lvTweets = (ListView) findViewById(R.id.lvTweets);

        tweetList = new ArrayList<Tweet>();
        timelineAdapter = new TweetsTimeLineAdapter(this, tweetList);
        lvTweets.setAdapter(timelineAdapter);


        /*Cursor tweetCursor = Tweet.fetchResultCursor();
        tweetCursorAdapter = new TweetCursorAdapter(this, tweetCursor);
        lvTweets.setAdapter(tweetCursorAdapter);*/


        getPersonalDetails();
        populateTimeline();

        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                loadMoreData();
            }
        });

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                populateTimeline();

            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


    }

    private void getPersonalDetails() {
        if(isInternetAvailable()) {
            twitterClient.getMyProfile(new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                    myHandle = User.fromJson(response);
                    System.out.println(myHandle);
                    System.out.println("hello");

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.d("DEBUG", responseString);

                }
            });
        }

    }

    private void loadMoreData() {

        if(isInternetAvailable()) {
            twitterClient.getHomeTimeLineInfinite(new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                    timelineAdapter.addAll(Tweet.constructArrayFromJsonTweets(response));
                    //tweetList = Tweet.constructArrayFromJsonTweets(response);
                    max_id = tweetList.get(tweetList.size() - 1).getUid();
                    //tweetCursorAdapter.notifyDataSetChanged();
                    swipeContainer.setRefreshing(false);

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.d("DEBUG", responseString);

                }
            }, max_id, false);

        }
    }

    // send api request to get timeline json and then fill list view by creating tweet objects
    private void populateTimeline() {
        if(isInternetAvailable()) {
            twitterClient.getHomeTimeLine(new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                    timelineAdapter.addAll(Tweet.constructArrayFromJsonTweets(response));
                    //tweetList =Tweet.constructArrayFromJsonTweets(response);
                    System.out.println("hello");
                    //tweetCursorAdapter.notifyDataSetChanged();
                    max_id = tweetList.get(tweetList.size() - 1).getUid();
                    swipeContainer.setRefreshing(false);

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.d("DEBUG", responseString);

                }
            });
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(this, TweetPostActivity.class);
            i.putExtra("personalDetails", myHandle);
            startActivityForResult(i, REQUEST_RESULT);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==REQUEST_RESULT){
            if(resultCode==RESULT_OK){
                Tweet newTweet = data.getParcelableExtra("newTweet");
                tweetList.add(0,newTweet);
                timelineAdapter.notifyDataSetChanged();

            }
        }
    }

    private boolean isInternetAvailable() {

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo!=null && networkInfo.isConnectedOrConnecting();
    }


}
