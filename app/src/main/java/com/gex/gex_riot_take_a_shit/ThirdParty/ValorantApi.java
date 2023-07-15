package com.gex.gex_riot_take_a_shit.ThirdParty;

import android.util.Log;

import com.gex.gex_riot_take_a_shit.LocalApiHandler;

import org.json.JSONObject;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ValorantApi {
    private static ValorantApi instance;
    private static OkHttpClient client;

    public ValorantApi(){

        client = new OkHttpClient.Builder()
                //.sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0])
                .hostnameVerifier((hostname, session) -> true)
                .build();
    }

    public static ValorantApi getInstance() throws NoSuchAlgorithmException, KeyManagementException {

        if (instance == null) {
            synchronized (LocalApiHandler.class) {
                if (instance == null) {
                    instance = new ValorantApi();
                }
            }
        }
        return instance;
    }

    public static String GetPlayerCard(String PlayerCardID,boolean large) throws IOException, ExecutionException, InterruptedException {
        Callable<String> callable = new Callable<String>() {
            public String call() {
                try {
                    System.out.println("called from python Rest Api");

                    Request request = new Request.Builder()
                            .url("https://valorant-api.com/v1/playercards/"+PlayerCardID)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseString = response.body().string();
                    JSONObject json = new JSONObject(responseString);
                    if(large){
                        String PlayerCardIDURL = json.getJSONObject("data").getString("wideArt");
                        return PlayerCardIDURL;
                    }else {
                        String PlayerCardIDURL = json.getJSONObject("data").getString("smallArt");
                        return PlayerCardIDURL;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("Api_Call", "Call failed: " + e);
                    return "null";
                }
            }
        };
        ExecutorService executor = Executors.newSingleThreadExecutor();
        // Submit the Callable object to the ExecutorService to run in a separate thread
        return executor.submit(callable).get();
    }


    public static String GetPlayerTitle(String PlayerTitle) throws IOException, ExecutionException, InterruptedException {
        Callable<String> callable = new Callable<String>() {
            public String call() {
                try {
                    System.out.println("called from python Rest Api");

                    Request request = new Request.Builder()
                            .url("https://valorant-api.com/v1/playertitles/"+PlayerTitle)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseString = response.body().string();
                    JSONObject json = new JSONObject(responseString);
                    String PlayerTitle = json.getJSONObject("data").getString("titleText");
                    return PlayerTitle;
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("Api_Call", "Call failed: " + e);
                    return "null";
                }
            }
        };
        ExecutorService executor = Executors.newSingleThreadExecutor();
        // Submit the Callable object to the ExecutorService to run in a separate thread
        return executor.submit(callable).get();
    }

    public static String GetRank(String PlayerTitle) throws IOException, ExecutionException, InterruptedException {
        Callable<String> callable = new Callable<String>() {
            public String call() {
                try {
                    System.out.println("called from python Rest Api");

                    Request request = new Request.Builder()
                            .url("https://valorant-api.com/v1/playertitles/"+PlayerTitle)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseString = response.body().string();
                    JSONObject json = new JSONObject(responseString);
                    String PlayerTitle = json.getJSONObject("data").getString("titleText");
                    return PlayerTitle;
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("Api_Call", "Call failed: " + e);
                    return "null";
                }
            }
        };
        ExecutorService executor = Executors.newSingleThreadExecutor();
        // Submit the Callable object to the ExecutorService to run in a separate thread
        return executor.submit(callable).get();
    }

    public static String[] GetSkinByLevel(String WeaponID) throws ExecutionException, InterruptedException {
        Callable<String[]> callable = new Callable<String[]>() {
            public String[] call() {
                try {
                    String[] TheData = new String[4];

                    Request request = new Request.Builder()
                            .url("https://valorant-api.com/v1/weapons/skinlevels/"+WeaponID)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseString = response.body().string();
                    JSONObject json = new JSONObject(responseString).getJSONObject("data");
                    String name = json.getString("displayName");
                    String icon = json.getString("displayIcon");
                    String streamedVid = json.getString("streamedVideo");

                    TheData[0] = name;
                    TheData[1] = icon;
                    TheData [2] = streamedVid;

                    return TheData;
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("Api_Call", "Call failed: " + e);
                    return null;
                }
            }
        };
        ExecutorService executor = Executors.newSingleThreadExecutor();
        // Submit the Callable object to the ExecutorService to run in a separate thread
        return executor.submit(callable).get();
    }


    public static String  GetBundle(String bundleID) throws ExecutionException, InterruptedException {
        Callable<String> callable = new Callable<String>() {
            public String call() {
                try {
                    System.out.println("called from python Rest Api");

                    Request request = new Request.Builder()
                            .url("https://valorant-api.com/v1/bundles/"+bundleID)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseString = response.body().string();
                    JSONObject json = new JSONObject(responseString);
                    String ImageLink = json.getJSONObject("data").getString("displayIcon2");
                    Log.d("GetBundleApi", "Get Bundle Api: "+ImageLink);
                    return ImageLink;
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("Api_Call", "Call failed: " + e);
                    return "null";
                }
            }
        };
        ExecutorService executor = Executors.newSingleThreadExecutor();
        // Submit the Callable object to the ExecutorService to run in a separate thread
        return executor.submit(callable).get();
    }
}
