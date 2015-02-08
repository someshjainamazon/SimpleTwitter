package com.codepath.apps.mysimpletweets.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by somesh on 2/6/15.
 */
public class TweetsTimeLineAdapter extends ArrayAdapter<Tweet> {
    public TweetsTimeLineAdapter(Context context, List<Tweet> tweets) {
        super(context, android.R.layout.simple_list_item_1, tweets);
    }


    // checkout view holder pattern

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Tweet tweet = getItem(position);

        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
        }

        TextView tvScreenName = (TextView) convertView.findViewById(R.id.tvMyScreenName);
        TextView tvTweetBody = (TextView) convertView.findViewById(R.id.tvTweetBody);
        TextView tvRelTime = (TextView) convertView.findViewById(R.id.tvRelTime);

        ImageView ivProfilePic = (ImageView) convertView.findViewById(R.id.ivProfilePic);

       // String temp = tweet.getRelativeTimeAgo(tweet.getCreatedAt());

        tvScreenName.setText(tweet.getUser().getScreenName()+"");
        tvTweetBody.setText(tweet.getBody());
        tvRelTime.setText(tweet.getRelativeTimeAgo(tweet.getCreatedAt()));
        ivProfilePic.setImageResource(0);

        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(ivProfilePic);

        return convertView;
    }
}
