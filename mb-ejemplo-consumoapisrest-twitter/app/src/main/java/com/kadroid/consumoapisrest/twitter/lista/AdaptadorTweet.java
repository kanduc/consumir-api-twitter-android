package com.kadroid.consumoapisrest.twitter.lista;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kadroid.consumoapisrest.twitter.R;
import com.kadroid.consumoapisrest.twitter.model.Tweet;
import com.kadroid.consumoapisrest.twitter.util.BitmapManager;

import java.util.ArrayList;

/**
 * Created by Kalu on 28/03/2015.
 */
public class AdaptadorTweet extends ArrayAdapter<Tweet> {

    private Context context;
    private ArrayList<Tweet> tweets;

    public AdaptadorTweet(Context context, int viewResourceId, ArrayList<Tweet> tweets) {
        super(context, viewResourceId, tweets);
        this.context = context;
        this.tweets = tweets;
    }

    static class ViewHolder{
        public ImageView imgvw_avatar;
        public TextView txv_nombre;
        public TextView txv_alias;
        public TextView txv_tweet;
        public TextView txv_fecha_creacion;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(R.layout.disenio_lista_tweet, parent, false);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.imgvw_avatar = (ImageView) convertView.findViewById(R.id.imgvw_avatar);
            viewHolder.txv_nombre = (TextView) convertView.findViewById(R.id.txv_nombre);
            viewHolder.txv_alias = (TextView) convertView.findViewById(R.id.txv_alias);
            viewHolder.txv_tweet = (TextView) convertView.findViewById(R.id.txv_tweet);
            viewHolder.txv_fecha_creacion = (TextView) convertView.findViewById(R.id.txv_fecha_creacion);
            convertView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();
        BitmapManager.getInstance().loadBitmap(tweets.get(position).getUrl_imagen(), holder.imgvw_avatar);
        holder.txv_nombre.setText(tweets.get(position).getNombre());
        holder.txv_alias.setText(tweets.get(position).getAlias());
        holder.txv_tweet.setText(tweets.get(position).getTweet());
        holder.txv_fecha_creacion.setText(tweets.get(position).getFecha_creacion());

        return convertView;
    }
}
