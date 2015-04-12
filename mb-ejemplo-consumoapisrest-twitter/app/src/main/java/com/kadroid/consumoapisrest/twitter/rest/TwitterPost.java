package com.kadroid.consumoapisrest.twitter.rest;

import com.kadroid.consumoapisrest.twitter.model.Tweet;

import java.util.ArrayList;

/**
 * Created by Kalu on 29/03/2015.
 */
public interface TwitterPost {

    public void fin_conexion_tweet(Boolean estado, ArrayList<Tweet> tweets);
}
