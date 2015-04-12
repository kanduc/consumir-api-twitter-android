package com.kadroid.consumoapisrest.twitter.rest;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.kadroid.consumoapisrest.twitter.R;
import com.kadroid.consumoapisrest.twitter.model.Tweet;
import com.kadroid.consumoapisrest.twitter.util.Constantes;
import com.kadroid.consumoapisrest.twitter.util.TwitterUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Kalu on 28/03/2015.
 */
public class TweetsTask extends AsyncTask<Object, Void, ArrayList<Tweet>> {

    private ProgressDialog progressDialog;
    private Context context;
    private TwitterPost twiteer_post;

    public TweetsTask(Context context, TwitterPost twiteer_post){

        this.context=context;
        this.twiteer_post=twiteer_post;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getResources().getString(R.string.label_descargando_tweets));
        progressDialog.show();
    }

    @Override
    protected ArrayList<Tweet> doInBackground(Object... param) {

        ArrayList<Tweet> tweets = new ArrayList<Tweet>();

        try {

            String timeline = TwitterUtil.buscarEnTimeline(Constantes.HASHTAG);
            JSONObject jsonResponse = new JSONObject(timeline);

            JSONArray jsonArray = jsonResponse.getJSONArray("statuses");
            JSONObject jsonObject;

            for (int i = 0; i < jsonArray.length(); i++) {

                jsonObject = (JSONObject) jsonArray.get(i);
                Tweet tweet = new Tweet();

                tweet.setNombre(jsonObject.getJSONObject("user").getString("name"));
                tweet.setAlias(jsonObject.getJSONObject("user").getString("screen_name"));
                tweet.setUrl_imagen(jsonObject.getJSONObject("user").getString("profile_image_url"));
                tweet.setTweet(jsonObject.getString("text"));
                tweet.setFecha_creacion(jsonObject.getString("created_at"));

                tweets.add(i, tweet);
            }

        } catch (Exception e) {
            Log.e("JSON error background: ", Log.getStackTraceString(e));

        }
        return tweets;
    }

    @Override
    protected void onPostExecute(ArrayList<Tweet> tweets){
        progressDialog.dismiss();

        Boolean estado;

        if (tweets.isEmpty()) {
            Toast.makeText(context, context.getResources().getString(R.string.label_no_se_encontraron_tweets),
                    Toast.LENGTH_SHORT).show();
            estado=false;
        } else {
            estado=true;
            Toast.makeText(context, context.getResources().getString(R.string.label_tweets_descargados),
                    Toast.LENGTH_SHORT).show();
        }

        twiteer_post.fin_conexion_tweet(estado,tweets);
    }

}
