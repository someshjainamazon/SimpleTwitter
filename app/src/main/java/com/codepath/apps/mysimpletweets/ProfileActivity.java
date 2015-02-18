package com.codepath.apps.mysimpletweets;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.fragments.UserTimeLineFragment;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;


public class ProfileActivity extends ActionBarActivity {

    TwitterClient client;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_copy);
        String screenName = getIntent().getStringExtra("screen_name");
        client = TwitterApp.getRestClient();

        if(screenName==null){
            client.getUserInfo(new JsonHttpResponseHandler(){

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    user = User.fromJson(response);
                    getSupportActionBar().setTitle("@"+user.getScreenName());
                    //populateProfile(user);
                    populateProfileCopy(user);
                }
            });

        }else {

            client.getUserProfile(new JsonHttpResponseHandler(){

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    user = User.fromJson(response);
                    getSupportActionBar().setTitle("@"+user.getScreenName());
                    //populateProfile(user);
                    populateProfileCopy(user);
                }
            }, screenName);

        }



        if(savedInstanceState==null){
            UserTimeLineFragment userTimeLineFragment = UserTimeLineFragment.newInstance(screenName);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            //ft.replace(R.id.flContainer, userTimeLineFragment);
            ft.replace(R.id.flContainerCopy, userTimeLineFragment);
            ft.commit();
        }



    }

    private void populateProfile(User user) {

        TextView tvName = (TextView) findViewById(R.id.tvUserTimeLineName);
        TextView tvTagLine = (TextView) findViewById(R.id.tvUserTimeLineTagline);
        TextView tvFollower = (TextView) findViewById(R.id.tvFollowerCount);
        TextView tvFollowing = (TextView) findViewById(R.id.tvFollowingCount);
        ImageView ivProfile = (ImageView) findViewById(R.id.ivUserProfilePhoto);

        tvName.setText(user.getName());
        tvTagLine.setText(user.getTagLine());
        tvFollower.setText(user.getFollowerCount()+" Followers");
        tvFollowing.setText(user.getFollowingCount()+" Following");

        Picasso.with(this).load(user.getProfileImageUrl()).into(ivProfile);

    }

    private void populateProfileCopy(User user) {

        TextView tvName = (TextView) findViewById(R.id.tvUserNameProfileCopy);
        TextView tvTagLine = (TextView) findViewById(R.id.tvTagLineProfileCopy);
        TextView tvFollower = (TextView) findViewById(R.id.tvNumFollowersProfileCopy);
        TextView tvFollowing = (TextView) findViewById(R.id.tvNumFollowingProfileCopy);
        ImageView ivProfile = (ImageView) findViewById(R.id.ivFrontImageProfile);
        TextView tvNumTweets = (TextView) findViewById(R.id.tvNumTweetsProfileCopy);
        ImageView ivProfileBackGround = (ImageView) findViewById(R.id.ivBackImage_Profile);


        tvName.setText(user.getName());
        tvTagLine.setText(user.getTagLine());

        if(user.getFollowerCount()>=1000)tvFollower.setText((user.getFollowerCount()/1000)+"k Followers");
        else tvFollower.setText(user.getFollowerCount()+" Followers");

        if(user.getFollowingCount()>=1000)tvFollowing.setText((user.getFollowerCount()/1000)+"k Following");
        else tvFollowing.setText(user.getFollowingCount()+" Following");

        if(user.getTweetCount()>=1000)tvNumTweets.setText((user.getTweetCount()/1000)+"k Tweets");
        else tvNumTweets.setText(user.getTweetCount()+" Tweets");



        Picasso.with(this).load(user.getProfileImageUrl()).into(ivProfile);
        Picasso.with(this).load(user.getProfileBackGroundImageUrl()).into(ivProfileBackGround);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
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
}
