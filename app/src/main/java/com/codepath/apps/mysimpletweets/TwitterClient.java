package com.codepath.apps.mysimpletweets;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.FlickrApi;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "xTV1vvKwK2BdBOFPmvpk5jA4V";       // Change this
	public static final String REST_CONSUMER_SECRET = "e1AiGg1cWs74zVji5in8XfcePgw9GGL5pNuzUZ8Bw9hwhurx9t"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://mysimpletweets"; // Change this (here and in manifest)



	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}


    public void getHomeTimeLine(AsyncHttpResponseHandler responseHandler){
        String apiUrl = getApiUrl("statuses/home_timeline.json");
        RequestParams requestParams = new RequestParams();
        requestParams.put("count", 25);
        requestParams.put("since_id", 1);


        getClient().get(apiUrl, requestParams, responseHandler);

    }

    public void getHomeTimeLineInfinite(AsyncHttpResponseHandler responseHandler, long max_id, boolean isFirst){
        String apiUrl = getApiUrl("statuses/home_timeline.json");
        RequestParams requestParams = new RequestParams();
        requestParams.put("count", 25);
        requestParams.put("since_id", 1);
        if(!isFirst) requestParams.put("max_id", max_id);


        getClient().get(apiUrl, requestParams, responseHandler);

    }


    public void getUserTimeLine(AsyncHttpResponseHandler responseHandler, String screenName){
        String apiUrl = getApiUrl("statuses/user_timeline.json");
        RequestParams requestParams = new RequestParams();
        requestParams.put("count", 25);
        requestParams.put("screen_name", screenName);

        getClient().get(apiUrl, requestParams, responseHandler);

    }


    public void getUserInfo(AsyncHttpResponseHandler responseHandler){
        String apiUrl = getApiUrl("account/verify_credentials.json");

        getClient().get(apiUrl, null, responseHandler);

    }


    public void getMentionTimeLine(AsyncHttpResponseHandler responseHandler) {

        String apiUrl = getApiUrl("statuses/mentions_timeline.json");
        RequestParams requestParams = new RequestParams();
        requestParams.put("count", 25);
        requestParams.put("since_id", 1);


        getClient().get(apiUrl, requestParams, responseHandler);
    }

    public void getMentionTimeLineInfinite(AsyncHttpResponseHandler responseHandler, long max_id, boolean isFirst) {

        String apiUrl = getApiUrl("statuses/mentions_timeline.json");
        RequestParams requestParams = new RequestParams();
        requestParams.put("count", 25);
        requestParams.put("since_id", 1);
        if(!isFirst) requestParams.put("max_id", max_id);
        getClient().get(apiUrl, requestParams, responseHandler);
    }



    public void getMyProfile(AsyncHttpResponseHandler responseHandler){
        String apiUrl = getApiUrl("users/show.json");
        RequestParams requestParams = new RequestParams();
        requestParams.put("screen_name", "someshjain1");
        getClient().get(apiUrl, requestParams, responseHandler);

    }



    public void getUserProfile(AsyncHttpResponseHandler responseHandler, String screenName){
        String apiUrl = getApiUrl("users/show.json");
        RequestParams requestParams = new RequestParams();
        requestParams.put("screen_name", screenName);
        getClient().get(apiUrl, requestParams, responseHandler);

    }


    public void postNewTweet(AsyncHttpResponseHandler responseHandler, String tweetText){
        String apiUrl = getApiUrl("statuses/update.json");
        RequestParams requestParams = new RequestParams();
        requestParams.put("status", tweetText);

        getClient().post(apiUrl, requestParams, responseHandler);


    }








	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */


    // method = endpoint

}