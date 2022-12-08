package com.gex.gex_riot_take_a_shit;
import static com.gex.gex_riot_take_a_shit.MainActivity.viewModel;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
public class pythonRestApi {

    public static String getUsername(String puuid) throws IOException, ExecutionException, InterruptedException {
        Callable<String> callable = new Callable<String>() {
            public String call() {
                try {
                    System.out.println("called from python Rest Api");
                    OkHttpClient client = new OkHttpClient();
                    // code request code here
                    Request request = new Request.Builder()
                            .url("http://192.168.1.19:7979/get_name/"+puuid)
                            .build();

                    Response response = client.newCall(request).execute();
                    Log.d("Api Call Response", "call: "+response);
                    return response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                    return "null";
                }

            }
        };
        ExecutorService executor = Executors.newSingleThreadExecutor();

        // Submit the Callable object to the ExecutorService to run in a separate thread
        return executor.submit(callable).get();

    }

    public static void StartQ() throws IOException {
        new Thread(new Runnable() {
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    // code request code here
                    Request request = new Request.Builder()
                            .url("http://192.168.1.19:7979/startQ")
                            .build();

                    Response response = client.newCall(request).execute();
                    System.out.println(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public static void LeaveQ() throws IOException {
        new Thread(new Runnable() {
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    // code request code here
                    Request request = new Request.Builder()
                            .url("http://192.168.1.19:7979/stopQ")
                            .build();

                    Response response = client.newCall(request).execute();
                    System.out.println(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public static void LeaveParty() throws IOException {
        new Thread(new Runnable() {
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    // code request code here
                    Request request = new Request.Builder()
                            .url("http://192.168.1.19:7979/leave_party")
                            .build();

                    Response response = client.newCall(request).execute();
                    System.out.println(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public static void Dodge() throws IOException {
        new Thread(new Runnable() {
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    // code request code here
                    Request request = new Request.Builder()
                            .url("http://192.168.1.19:7979/Dodge")
                            .build();

                    Response response = client.newCall(request).execute();
                    System.out.println(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public static void send_text(String text) throws IOException {
        new Thread(new Runnable() {
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    // code request code here
                    Request request = new Request.Builder()
                            .url("http://192.168.1.19:7979/chat/"+text)
                            .build();

                    Response response = client.newCall(request).execute();
                    System.out.println(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public static void Change_Q(String Queue) throws IOException {
        new Thread(new Runnable() {
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    // code request code here
                    Request request = new Request.Builder()
                            .url("http://192.168.1.19:7979/changeQ/"+Queue)
                            .build();

                    Response response = client.newCall(request).execute();
                    System.out.println(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public static void set_party_status(String status) throws IOException {
        new Thread(new Runnable() {
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    // code request code here
                    Request request = new Request.Builder()
                            .url("http://192.168.1.19:7979/party/"+status)
                            .build();

                    Response response = client.newCall(request).execute();
                    System.out.println(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public static void SelectAgent(String agent) throws IOException {
        new Thread(new Runnable() {
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    // code request code here
                    Request request = new Request.Builder()
                            .url("http://192.168.1.19:7979/select_agent/"+agent)
                            .build();

                    Response response = client.newCall(request).execute();
                    System.out.println(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public static void LockAgent(String agent) throws IOException {
        new Thread(new Runnable() {
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    // code request code here
                    Request request = new Request.Builder()
                            .url("http://192.168.1.19:7979/lock_agent/"+agent)
                            .build();

                    Response response = client.newCall(request).execute();
                    System.out.println(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public static String get_map_name() throws IOException, ExecutionException, InterruptedException {
        Callable<String> callable = new Callable<String>() {
            public String call() {
                try {
                    System.out.println("called from python Rest Api");
                    OkHttpClient client = new OkHttpClient();
                    // code request code here
                    Request request = new Request.Builder()
                            .url("http://192.168.1.19:7979/get_map")
                            .build();

                    Response response = client.newCall(request).execute();
                    Log.d("Api Call Response", "call: "+response);
                    return response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                    return "null";
                }

            }
        };
        ExecutorService executor = Executors.newSingleThreadExecutor();

        // Submit the Callable object to the ExecutorService to run in a separate thread
        return executor.submit(callable).get();

    }
    public static String get_server() throws IOException, ExecutionException, InterruptedException {
        Callable<String> callable = new Callable<String>() {
            public String call() {
                try {
                    System.out.println("called from python Rest Api");
                    OkHttpClient client = new OkHttpClient();
                    // code request code here
                    Request request = new Request.Builder()
                            .url("http://192.168.1.19:7979/get_server/pre_game")
                            .build();

                    Response response = client.newCall(request).execute();
                    Log.d("Api Call Response", "call: "+response);
                    return response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                    return "null";
                }

            }
        };
        ExecutorService executor = Executors.newSingleThreadExecutor();

        // Submit the Callable object to the ExecutorService to run in a separate thread
        return executor.submit(callable).get();

    }
    public static String current_state() throws IOException, ExecutionException, InterruptedException {
        Callable<String> callable = new Callable<String>() {
            public String call() {
                try {
                    System.out.println("called from python Rest Api");
                    OkHttpClient client = new OkHttpClient();
                    // code request code here
                    Request request = new Request.Builder()
                            .url("http://192.168.1.19:7979/current_state")
                            .build();

                    Response response = client.newCall(request).execute();
                    Log.d("Api Call Response", "call: "+response);
                    return response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                    return "null";
                }

            }
        };
        ExecutorService executor = Executors.newSingleThreadExecutor();

        // Submit the Callable object to the ExecutorService to run in a separate thread
        return executor.submit(callable).get();

    }
}





