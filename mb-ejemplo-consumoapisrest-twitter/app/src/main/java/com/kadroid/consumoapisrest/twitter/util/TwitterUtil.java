package com.kadroid.consumoapisrest.twitter.util;

import android.util.Base64;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Kalu on 28/03/2015.
 */
public class TwitterUtil {

    public static final String TAG = TwitterUtil.class.toString();


    public static String autenticacion_app(){

        HttpURLConnection conexion_http = null;
        OutputStream outputStream = null;
        BufferedReader bufferedReader = null;
        StringBuilder response = null;

        try {
            URL url = new URL(Constantes.URL_AUTENTICACION);
            conexion_http = (HttpURLConnection) url.openConnection();
            conexion_http.setRequestMethod("POST");
            conexion_http.setDoOutput(true);
            conexion_http.setDoInput(true);

            String credenciales_acceso = Constantes.CONSUMER_KEY + ":" + Constantes.CONSUMER_SECRET;
            String autorizacion = "Basic " + Base64.encodeToString(credenciales_acceso.getBytes(), Base64.NO_WRAP);
            String parametros = "grant_type=client_credentials";

            conexion_http.addRequestProperty("Authorization", autorizacion);
            conexion_http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            conexion_http.connect();

            outputStream = conexion_http.getOutputStream();
            outputStream.write(parametros.getBytes());
            outputStream.flush();
            outputStream.close();

            bufferedReader = new BufferedReader(new InputStreamReader(conexion_http.getInputStream()));
            String linea;
            response = new StringBuilder();

            while ((linea = bufferedReader.readLine()) != null){
                response.append(linea);
            }

            Log.d(TAG, "POST codigo response: " + String.valueOf(conexion_http.getResponseCode()));
            Log.d(TAG, "JSON response: " + response.toString());

        } catch (Exception e) {
            Log.e(TAG, "POST error autenticacion: " + e);

        }finally{
            if (conexion_http != null) {
                conexion_http.disconnect();
            }
        }
        return response.toString();
    }

    public static String buscarEnTimeline(String searchTerm){

        HttpURLConnection conexion_http = null;
        BufferedReader bufferedReader = null;
        StringBuilder response = new StringBuilder();

        try {
            URL url = new URL(Constantes.URL_BUSQUEDA + searchTerm + "&count=10");
            conexion_http = (HttpURLConnection) url.openConnection();
            conexion_http.setRequestMethod("GET");

            String jsonString = autenticacion_app();
            JSONObject jsonObject = new JSONObject(jsonString);
            String token = jsonObject.getString("token_type") + " " +
                    jsonObject.getString("access_token");

            conexion_http.setRequestProperty("Authorization", token);
            conexion_http.setRequestProperty("Content-Type", "application/json");
            conexion_http.connect();

            bufferedReader = new BufferedReader(new InputStreamReader(conexion_http.getInputStream()));

            String linea;
            while ((linea = bufferedReader.readLine()) != null){
                response.append(linea);
            }

            Log.d(TAG, "GET codigo response: " + String.valueOf(conexion_http.getResponseCode()));
            Log.d(TAG, "JSON response: " + response.toString());

            return response.toString();

        } catch (Exception e) {
            Log.e(TAG, "GET error obtener tweets: " + e);
            return null;

        }finally {
            if(conexion_http != null){
                conexion_http.disconnect();

            }

        }
    }
}
