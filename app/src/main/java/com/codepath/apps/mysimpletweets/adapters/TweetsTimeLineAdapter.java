package com.codepath.apps.mysimpletweets.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.ProfileActivity;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TimelineActivity;
import com.codepath.apps.mysimpletweets.TweetPostActivity;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by somesh on 2/6/15.
 */
public class TweetsTimeLineAdapter extends ArrayAdapter<Tweet> {
    public TweetsTimeLineAdapter(Context context, List<Tweet> tweets) {
        super(context, R.layout.item_tweet, tweets);
    }


    // checkout view holder pattern

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Tweet tweet = getItem(position);

        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
        }

        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvMyUserNameHome);
        final TextView tvScreenName = (TextView) convertView.findViewById(R.id.tvMyScreenNameHome);


        TextView tvTweetBody = (TextView) convertView.findViewById(R.id.tvTweetBody);
        TextView tvRelTime = (TextView) convertView.findViewById(R.id.tvRelTime);

        ImageView ivProfilePic = (ImageView) convertView.findViewById(R.id.ivProfilePic);

        ivProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ProfileActivity.class);
                i.putExtra("screen_name", tvScreenName.getText());
                getContext().startActivity(i);

            }
        });

        // String temp = tweet.getRelativeTimeAgo(tweet.getCreatedAt());

        tvScreenName.setText(" @"+tweet.getScreenName());
        tvUserName.setText(tweet.getUserName());
        tvTweetBody.setText(tweet.getBody());
        tvRelTime.setText(Tweet.getShowTime(tweet.getCreatedAt()));
        ivProfilePic.setImageResource(0);

        //String profileUrl =tweet.getUser().getProfileImageUrl();
        //String biggerProfileUrl= profileUrl.replace("normal","bigger");
        Picasso.with(getContext()).load(tweet.getProfileImageUrl()).into(ivProfilePic);

        return convertView;
    }
}
