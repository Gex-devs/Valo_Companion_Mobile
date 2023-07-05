package com.gex.gex_riot_take_a_shit;

import android.util.Log;

import java.io.IOException;
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

public class RestApiCalls {


    public static String getUsername(String puuid) throws IOException, ExecutionException, InterruptedException {

        Callable<String> callable = new Callable<String>() {
            public String call() {
                try {
                    System.out.println("called from python Rest Api");
                    OkHttpClient client = new OkHttpClient();
                    // code request code here
                    Request request = new Request.Builder()
                            .url("http:/"+String.valueOf(Game_Status.client.getRemoteSocketAddress()).split(":")[0]+":7979/api/get_name?PUID="+puuid)
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
                            .url("http:/"+String.valueOf(Game_Status.client.getRemoteSocketAddress()).split(":")[0]+":7979/api/startQ")
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
                            .url("http:/"+String.valueOf(Game_Status.client.getRemoteSocketAddress()).split(":")[0]+":7979/api/stopQ")
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
                            .url("http:/"+String.valueOf(Game_Status.client.getRemoteSocketAddress()).split(":")[0]+":7979/api/leave_party")
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
                            .url("http:/"+String.valueOf(Game_Status.client.getRemoteSocketAddress()).split(":")[0]+":7979/api/Dodge")
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
    public static void Change_Q(String Queue) throws IOException {
        new Thread(new Runnable() {
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    // code request code here
                    Request request = new Request.Builder()
                            .url("http:/"+String.valueOf(Game_Status.client.getRemoteSocketAddress()).split(":")[0]+":7979/api/changeQ?queue="+Queue)
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
                            .url("http:/"+String.valueOf(Game_Status.client.getRemoteSocketAddress()).split(":")[0]+":7979/api/party_accessibility?state="+status)
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
                            .url("http:/"+String.valueOf(Game_Status.client.getRemoteSocketAddress()).split(":")[0]+":7979/api/pregame/selectagent?agent="+agent)
                            .build();

                    Response response = client.newCall(request).execute();
                    System.out.println(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static String LockAgent(String agent) throws IOException, ExecutionException, InterruptedException {
        Callable<String> callable = new Callable<String>() {
            public String call() {
                try {
                    System.out.println("called from python Rest Api");
                    OkHttpClient client = new OkHttpClient();
                    // code request code here
                    Request request = new Request.Builder()
                            .url("http:/"+String.valueOf(Game_Status.client.getRemoteSocketAddress()).split(":")[0]+":7979/api/pregame/lockagent?agent="+agent)
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
    public static String get_map_name() throws IOException, ExecutionException, InterruptedException {
        Callable<String> callable = new Callable<String>() {
            public String call() {
                try {
                    System.out.println("called from python Rest Api");
                    OkHttpClient client = new OkHttpClient();
                    // code request code here
                    Request request = new Request.Builder()
                            .url("http:/"+String.valueOf(Game_Status.client.getRemoteSocketAddress()).split(":")[0]+":7979/api/get_map")
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
                            .url("http:/"+String.valueOf(Game_Status.client.getRemoteSocketAddress()).split(":")[0]+":7979/api/get_server/pre_game")
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
                    //OkHttpClient client = new OkHttpClient();
                    // code request code here
                    // Create a trust manager that accepts all certificates
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

                    OkHttpClient client = new OkHttpClient.Builder()
                            .sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0])
                            .hostnameVerifier((hostname, session) -> true)
                            .build();

                    Request request = new Request.Builder()
                            .url("http:/"+String.valueOf(Game_Status.client.getRemoteSocketAddress()).split(":")[0]+":7979/api/current_state")
                            .build();
                    Response response = client.newCall(request).execute();
                    Log.d("Api_Call", "call: "+response);
                    return response.body().string();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("Api_Call","Call failed: "+e);
                    return "null";
                }

            }
        };
        ExecutorService executor = Executors.newSingleThreadExecutor();

        // Submit the Callable object to the ExecutorService to run in a separate thread
        return executor.submit(callable).get();
    }
    public static String get_gamemode() throws IOException, ExecutionException, InterruptedException {
        Callable<String> callable = new Callable<String>() {
            public String call() {
                try {
                    System.out.println("called from python Rest Api");
                    OkHttpClient client = new OkHttpClient();
                    // code request code here
                    Request request = new Request.Builder()
                            .url("http:/"+String.valueOf(Game_Status.client.getRemoteSocketAddress()).split(":")[0]+":7979/api/pregame/gamemode")
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
    public static String get_players_Current_game() throws IOException, ExecutionException, InterruptedException {
        Callable<String> callable = new Callable<String>() {
            public String call() {
                try {
                    System.out.println("called from python Rest Api");
                    OkHttpClient client = new OkHttpClient();
                    // code request code here
                    Request request = new Request.Builder()
                            .url("http:/"+String.valueOf(Game_Status.client.getRemoteSocketAddress()).split(":")[0]+":7979/api/coregame/players")
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
    public static String get_party() throws IOException, ExecutionException, InterruptedException {
        Callable<String> callable = new Callable<String>() {
            public String call() {
                try {
                    System.out.println("called from python Rest Api");
                    OkHttpClient client = new OkHttpClient();
                    // code request code here
                    Request request = new Request.Builder()
                            .url("http:/"+String.valueOf(Game_Status.client.getRemoteSocketAddress()).split(":")[0]+":7979/api/getParty")
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




