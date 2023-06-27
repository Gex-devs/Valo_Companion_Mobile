package com.gex.gex_riot_take_a_shit;

import android.util.Log;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LocalApiHandler {
    private static LocalApiHandler instance;
    private static OkHttpClient client;
    LocalApiHandler() throws NoSuchAlgorithmException, KeyManagementException {
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[] {};
                    }
                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
        };

        // Create an SSL context with the custom trust manager
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

        client = new OkHttpClient.Builder()
                .sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0])
                .hostnameVerifier((hostname, session) -> true)
                .build();

    }


    public static LocalApiHandler getInstance() throws NoSuchAlgorithmException, KeyManagementException {

        if (instance == null) {
            synchronized (LocalApiHandler.class) {
                if (instance == null) {
                    instance = new LocalApiHandler();
                }
            }
        }
        return instance;
    }

    public static String current_state() throws IOException, ExecutionException, InterruptedException {
        Callable<String> callable = new Callable<String>() {
            public String call() {
                try {
                    System.out.println("called from python Rest Api");

                    Request request = new Request.Builder()
                            .url("http:/" + String.valueOf(Game_Status.client.getRemoteSocketAddress()).split(":")[0] + ":7979/api/current_state")
                            .build();
                    Response response = client.newCall(request).execute();
                    return response.body().string();
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

    public static void send_text(String text) throws IOException {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Request request = new Request.Builder()
                            .url("http:/"+String.valueOf(Game_Status.client.getRemoteSocketAddress()).split(":")[0]+":7979/api/sendChat?text="+text)
                            .build();

                    Response response = client.newCall(request).execute();
                    System.out.println(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
