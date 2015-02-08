package com.codepath.apps.mysimpletweets;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;


public class TweetPostActivity extends ActionBarActivity {



    private User myHandle;

    private ImageView ivMyProfile;
    private TextView tvScreenName;
    private EditText etTweetPost;
    private TwitterClient twitterClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_post);
        twitterClient = TwitterApp.getRestClient(); // singleton client


        /*ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("Tweet Post");*/
        myHandle = getIntent().getParcelableExtra("personalDetails");
        ivMyProfile=(ImageView)findViewById(R.id.ivMyImage);
        tvScreenName=(TextView)findViewById(R.id.tvMyScreenName);
        etTweetPost = (EditText) findViewById(R.id.etTweet);

        tvScreenName.setText(myHandle.getScreenName());
        Picasso.with(TweetPostActivity.this).load(myHandle.getProfileImageUrl()).into(ivMyProfile);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tweet_post, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onCancel(View view) {

        this.finish();
    }

    public void onTweet(View view) {

        String tweetText=etTweetPost.getText().toString();

        if(tweetText!=null)twitterClient.postNewTweet(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Tweet tweet = Tweet.fromJSON(response);
                Intent i = new Intent();
                i.putExtra("newTweet", tweet);
                setResult(RESULT_OK, i);
                TweetPostActivity.this.finish();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                TweetPostActivity.this.finish();
            }
        }, tweetText);

    }
}
