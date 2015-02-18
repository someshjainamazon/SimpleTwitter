package com.codepath.apps.mysimpletweets;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;
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
    private TextView tvNumChars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_post);
        twitterClient = TwitterApp.getRestClient(); // singleton client

        final ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        twitterClient.getUserInfo(new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                myHandle = User.fromJson(response);
                populateUser();
            }
        });


        /*actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.custom_action_bar_post);
        actionBar.setBackgroundDrawable(new ColorDrawable(0xFF21D3FF));*/



        //myHandle = getIntent().getParcelableExtra("personalDetails");

        etTweetPost = (EditText) findViewById(R.id.etTweet);
        tvNumChars = (TextView) findViewById(R.id.tvCharLimit);



        etTweetPost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvNumChars.setText((140 - s.length())+"");

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }

    private void populateUser() {
        ivMyProfile=(ImageView)findViewById(R.id.ivMyImage);
        tvScreenName=(TextView)findViewById(R.id.tvMyUserName);
        tvScreenName.setText(myHandle.getScreenName());
        Picasso.with(TweetPostActivity.this).load(myHandle.getProfileImageUrl()).into(ivMyProfile);

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
