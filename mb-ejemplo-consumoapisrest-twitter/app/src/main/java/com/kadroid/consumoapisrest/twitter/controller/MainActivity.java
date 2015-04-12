package com.kadroid.consumoapisrest.twitter.controller;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.kadroid.consumoapisrest.twitter.R;
import com.kadroid.consumoapisrest.twitter.lista.AdaptadorTweet;
import com.kadroid.consumoapisrest.twitter.model.Tweet;
import com.kadroid.consumoapisrest.twitter.rest.TweetsTask;
import com.kadroid.consumoapisrest.twitter.rest.TwitterPost;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements TwitterPost {

    private ListView listView;
    private Context context;
    private TwitterPost twiteer_post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.lstvw_timeline);
        context=this;
        twiteer_post=this;

        new TweetsTask(context, twiteer_post).execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private void actualizarListaTweets(ArrayList<Tweet> tweets){
        listView.setAdapter(new AdaptadorTweet(this, R.layout.disenio_lista_tweet, tweets));
    }

    @Override
    public void fin_conexion_tweet(Boolean estado, ArrayList<Tweet> tweets){
        if(estado){
            actualizarListaTweets(tweets);

        }
    }
}
