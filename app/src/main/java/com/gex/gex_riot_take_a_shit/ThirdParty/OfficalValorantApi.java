package com.gex.gex_riot_take_a_shit.ThirdParty;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.gex.gex_riot_take_a_shit.LocalApiHandler;
import com.gex.gex_riot_take_a_shit.Utils.requestBodies.AuthCookiesBody;
import com.gex.gex_riot_take_a_shit.Utils.requestBodies.AuthRequestBody;
import com.gex.gex_riot_take_a_shit.Utils.signInChecker;
import com.gex.gex_riot_take_a_shit.enums.CurrencieIds;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OfficalValorantApi {

    private OkHttpClient miscClient = new OkHttpClient();
    private Context TheContext;
    private final String AuthCookiesUrl = "https://auth.riotgames.com/api/v1/authorization";
    private  Gson gson;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private CookieJar cookieJar;
    private String TokenJson = "";
    private ExecutorService executor;
    private final Map<String ,Integer> StorebundleHash = new HashMap<>(); //got lazy
    private static OfficalValorantApi instance;


    private OfficalValorantApi(Context context) throws IOException, JSONException {
        // What happened here
        this.TheContext = context;

        sharedPreferences = TheContext.getSharedPreferences("cookies", TheContext.MODE_PRIVATE);
        cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(sharedPreferences));

        editor = sharedPreferences.edit();

        gson = new Gson();

//        clearCookies();
        ReAuth();

        executor = Executors.newFixedThreadPool(6);

//        GetEntitlementToken();
    }


    public static OfficalValorantApi getInstance() {
        if (instance == null) {
            return null;
            //throw new IllegalStateException("WebsocketServer instance has not been initialized.");
        }
        return instance;
    }


    public static OfficalValorantApi getInstance(Context context) throws NoSuchAlgorithmException, KeyManagementException, JSONException, IOException {

        if (instance == null) {
            instance = new OfficalValorantApi(context);
        }

        return instance;
    }


    // Will clean up one day
    public boolean AuthToken(String username, String password) throws IOException, JSONException {
        clearCookies();

        cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(sharedPreferences));

        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .build();

        Headers headers = new Headers.Builder()
                .add("Content-Type", "application/json")
                .build();

        AuthCookiesBody authCookiesBody = new AuthCookiesBody();

        String postJsonBody = gson.toJson(authCookiesBody);

        Request postRequest = new Request.Builder()
                .url(AuthCookiesUrl)
                .headers(headers)
                .post(RequestBody.create(MediaType.parse("application/json"), postJsonBody))
                .build();

        // Send the POST request
        Response postResponse = client.newCall(postRequest).execute();

        String jsonPostResponse = postResponse.body().string();

        // Use the POST response as needed
        Log.d("AuthToken", "Official Api: from first request " + jsonPostResponse);

        // Save cookies from the POST response
        List<Cookie> cookies = cookieJar.loadForRequest(HttpUrl.parse("https://auth.riotgames.com/api/v1/authorization"));
        cookieJar.saveFromResponse(HttpUrl.parse("https://auth.riotgames.com/api/v1/authorization"), cookies);

        AuthRequestBody putRequestBody = new AuthRequestBody(username, password, true);

        // Convert the AuthRequestBody to JSON
        String putJsonBody = gson.toJson(putRequestBody);
        // PUT request
        Request putRequest = new Request.Builder()
                .url(AuthCookiesUrl)
                .headers(headers)
                .put(RequestBody.create(MediaType.parse("application/json"), putJsonBody))
                .build();

        // Send the PUT request
        Response putResponse = client.newCall(putRequest).execute();

        cookies = cookieJar.loadForRequest(HttpUrl.parse("https://auth.riotgames.com/api/v1/authorization"));
        cookieJar.saveFromResponse(HttpUrl.parse("https://auth.riotgames.com/api/v1/authorization"), cookies);

        String responseBody = putResponse.body().string();
        System.out.println(putResponse.isSuccessful());
        Log.d("AuthToken", "AuthToken: putResponse: " + responseBody);

        client = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .build();

        headers = new Headers.Builder()
                .add("Content-Type", "application/json")
                .build();

        authCookiesBody = new AuthCookiesBody();

        postJsonBody = gson.toJson(authCookiesBody);
        postRequest = new Request.Builder()
                .url(AuthCookiesUrl)
                .headers(headers)
                .post(RequestBody.create(MediaType.parse("application/json"), postJsonBody))
                .build();

        // Send the POST request
        postResponse = client.newCall(postRequest).execute();

       jsonPostResponse = postResponse.body().string();

        Log.d("AuthToken", "AuthToken: secondAuth: "+jsonPostResponse);

        putRequestBody = new AuthRequestBody(username, password, true);

        // Convert the AuthRequestBody to JSON
        putJsonBody = gson.toJson(putRequestBody);
        // PUT request
        putRequest = new Request.Builder()
                .url(AuthCookiesUrl)
                .headers(headers)
                .put(RequestBody.create(MediaType.parse("application/json"), putJsonBody))
                .build();

        // Send the PUT request
        putResponse = client.newCall(putRequest).execute();

        String putResponseBody = putResponse.body().string();
        Log.d("AuthToken", "AuthToken: Second Put Response: "+putResponseBody);
        JSONObject jsonObject = new JSONObject(putResponseBody);
        try{
            String j = jsonObject.getString("error");
            if (j.equals("auth_failure")){
                Log.d("AuthToken", "AuthToken: Invalid Username or password");
                return false;
            }
        }catch (JSONException e){
            Log.d("AuthToken", "AuthToken: No error");
        }

        TokenJson = putResponseBody;
        return true;
    }

    public void clearCookies() {
        editor.clear();
        editor.apply();
        // Reset any other necessary cookie-related variables or references
    }

    private void ReAuth() throws IOException {
        if (!signInChecker.isCookieAlive()){
            return;
        }
        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .build();

        String reAuthCookiesUrl = "https://auth.riotgames.com/authorize?redirect_uri=https%3A%2F%2Fplayvalorant.com%2Fopt_in&client_id=play-valorant-web-prod&response_type=token%20id_token&nonce=1";
        Request putRequest = new Request.Builder()
                .url(reAuthCookiesUrl)
                .build();

        // Send the PUT request
        Response putResponse = client.newCall(putRequest).execute();

        Log.d("ReAuth", "ReAuth: "+putResponse.body().string());


        Headers headers = new Headers.Builder()
                .add("Content-Type", "application/json")
                .build();

        AuthCookiesBody authCookiesBody = new AuthCookiesBody();

        String postjsonbody = gson.toJson(authCookiesBody);
        Request postRequest = new Request.Builder()
                .url(AuthCookiesUrl)
                .headers(headers)
                .post(RequestBody.create(MediaType.parse("application/json"), postjsonbody))
                .build();

        // Send the POST request
        Response postResponse = client.newCall(postRequest).execute();

        String jsonPostResponse = postResponse.body().string();

        TokenJson = jsonPostResponse;

    }

    private String GetAccessToken() throws JSONException {
        Log.d("GetAccessToken", "GetAccessToken: "+TokenJson);
        JSONObject jsonObject = new JSONObject(TokenJson);
        JSONObject j = jsonObject.getJSONObject("response");
        JSONObject jj = j.getJSONObject("parameters");
        String jjj = jj.getString("uri");
        // Extract the access token from the URI
        String accessToken = jjj.split("access_token=")[1].split("&")[0];

        // Print the access token
        System.out.println("Access Token: " + accessToken);
        return accessToken;
    }

    private String GetEntitlementToken() throws IOException, JSONException, ExecutionException, InterruptedException {

        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Headers headers = new Headers.Builder()
                        .add("Content-Type", "application/json")
                        .add("Authorization","Bearer "+ GetAccessToken())
                        .build();

                //Empty Body
                RequestBody requestBody = RequestBody.create(null, new byte[0]);
                Request postRequest = new Request.Builder()
                        .url("https://entitlements.auth.riotgames.com/api/token/v1")
                        .headers(headers)
                        .post(requestBody)
                        .build();

                // Send the POST request
                Response response = miscClient.newCall(postRequest).execute();
                if (!response.isSuccessful()){
                    ReAuth();
                    GetEntitlementToken();
                }

                JSONObject jsonObject = new JSONObject(response.body().string());
                String entitlement = jsonObject.getString("entitlements_token");
                // Handle the response
                Log.d("GetEntitlementToken", "GetEntitlementToken: "+entitlement);
                return entitlement;
            }
        };

        // Submit the Callable object to the ExecutorService to run in a separate thread
        return executor.submit(callable).get();

    }

    private String GetPuuid() throws JSONException, IOException, ExecutionException, InterruptedException {
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Headers headers = new Headers.Builder()
                        .add("Authorization","Bearer "+ GetAccessToken())
                        .build();

                //Empty Body
                RequestBody requestBody = RequestBody.create(null, new byte[0]);
                Request Request = new Request.Builder()
                        .url("https://auth.riotgames.com/userinfo")
                        .headers(headers)
                        .build();

                // Send the POST request
                Response response = miscClient.newCall(Request).execute();

                JSONObject jsonObject = new JSONObject(response.body().string());
                String Puuid = jsonObject.getString("sub");
                // Handle the response
                Log.d("Official Api", "GetPuuid: "+Puuid);
                return Puuid;

            }
        };

        return executor.submit(callable).get();


    }

    public Map<String, Integer> GetStore() throws JSONException, IOException, ExecutionException, InterruptedException, NoSuchAlgorithmException, KeyManagementException {
        Headers headers = new Headers.Builder()
                .add("Authorization","Bearer "+ GetAccessToken())
                .add("X-Riot-Entitlements-JWT",GetEntitlementToken())
                .build();

        Request request = new Request.Builder()
                .url("https://pd.eu.a.pvp.net/store/v2/storefront/"+GetPuuid())
                .headers(headers)
                .build();

        Response response = miscClient.newCall(request).execute();

        String ResponseString = response.body().string();

        JSONObject jsonObject = new JSONObject(ResponseString);
        JSONObject j = jsonObject.getJSONObject("SkinsPanelLayout");
        String RemaningTime = j.getString("SingleItemOffersRemainingDurationInSeconds");
        JSONArray jj = j.getJSONArray("SingleItemStoreOffers");

        Map<String,Integer> StoreList = new HashMap<>();

        for (int i = 0; i < jj.length(); i++) {
            JSONObject jbject = jj.getJSONObject(i);

            String offerId = jbject.getString("OfferID");
            JSONObject costObject = jbject.getJSONObject("Cost");
            int cost = costObject.getInt("85ad13f7-3d1b-5128-9eb2-7cd8ee0b5741");

            StoreList.put(offerId,cost);
        }

        //Get Bundle
        JSONObject FeaturedBundle = jsonObject.getJSONObject("FeaturedBundle");
        JSONArray bundles = FeaturedBundle.getJSONArray("Bundles");
        JSONObject BundleData =  bundles.getJSONObject(0);
        String ID = BundleData.getString("DataAssetID");
        //fix later
        Integer fakeprice = 2000;

        StorebundleHash.put(ID,fakeprice);

        ///// DEBUG

        headers = new Headers.Builder()
                .add("X-Riot-ClientVersion",ValorantApi.getInstance().GetClientVersion())
                .add("Authorization","Bearer "+ GetAccessToken())
                .add("X-Riot-Entitlements-JWT",GetEntitlementToken())
                .build();

        request = new Request.Builder()
                .url("https://glz-eu-1.eu.a.pvp.net/parties/v1/players/6daff3be-92d8-58cb-986c-e4ab72b96a5a")
                .headers(headers)
                .build();

        response = miscClient.newCall(request).execute();

        ResponseString = response.body().string();

        Log.d("Party Debug", "Is Player Online: "+ ResponseString);

        return StoreList;
    }

    public Map<String,Integer> GetStoreBundle(){
        if (!StorebundleHash.isEmpty()){
            return StorebundleHash;
        }else {
            return null;
        }
    }


    public Map<String, Integer> GetWallet() throws JSONException, IOException, ExecutionException, InterruptedException {
        Headers headers = new Headers.Builder()
                .add("Authorization","Bearer "+ GetAccessToken())
                .add("X-Riot-Entitlements-JWT",GetEntitlementToken())
                .build();

        Request request = new Request.Builder()
                .url("https://pd.eu.a.pvp.net/store/v1/wallet/"+GetPuuid())
                .headers(headers)
                .build();

        Response response = miscClient.newCall(request).execute();

        String ResponseString = response.body().string();
        JSONObject jObject = new JSONObject(ResponseString);
        JSONObject jsonObject = jObject.getJSONObject("Balances");
        Integer ValorantPoint = jsonObject.getInt(CurrencieIds.ValorantPoint.getID());
        Integer RadPoint = jsonObject.getInt(CurrencieIds.RadianitePoints.getID());
        Integer KingdomCreds = jsonObject.getInt(CurrencieIds.KingdomCredits.getID());


        Map<String,Integer> Wallet = new HashMap<>();
        Wallet.put(CurrencieIds.ValorantPoint.toString(), ValorantPoint);
        Wallet.put(CurrencieIds.RadianitePoints.toString(), RadPoint);
        Wallet.put(CurrencieIds.KingdomCredits.toString(), KingdomCreds);


        return Wallet;
    }

    public String GetPartyID() throws IOException, JSONException, NoSuchAlgorithmException, KeyManagementException, ExecutionException, InterruptedException {

        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Headers headers = new Headers.Builder()
                        .add("X-Riot-ClientVersion",ValorantApi.getInstance().GetClientVersion())
                        .add("Authorization","Bearer "+ GetAccessToken())
                        .add("X-Riot-Entitlements-JWT",GetEntitlementToken())
                        .build();

                Request request = new Request.Builder()
                        .url("https://glz-eu-1.eu.a.pvp.net/parties/v1/players/"+GetPuuid())
                        .headers(headers)
                        .build();

                Response response = miscClient.newCall(request).execute();

                String ResponseString = response.body().string();
                String PartyID = new JSONObject(ResponseString).getString("CurrentPartyID");
                return  PartyID;
            }
        };

        return executor.submit(callable).get();

    }

    public String GetParty() throws IOException, JSONException, NoSuchAlgorithmException, KeyManagementException, ExecutionException, InterruptedException {

        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Headers headers = new Headers.Builder()
                        .add("Authorization","Bearer "+ GetAccessToken())
                        .add("X-Riot-Entitlements-JWT",GetEntitlementToken())
                        .build();

                Request request = new Request.Builder()
                        .url("https://glz-eu-1.eu.a.pvp.net/parties/v1/parties/"+GetPartyID())
                        .headers(headers)
                        .build();

                Response response = miscClient.newCall(request).execute();

                String ResponseString = response.body().string();

                return  ResponseString;
            }
        };

        return executor.submit(callable).get();

    }

    public String GetQeueMode() throws JSONException, IOException, NoSuchAlgorithmException, ExecutionException, InterruptedException, KeyManagementException {

        String QeueMode = new JSONObject(GetParty()).getJSONObject("MatchmakingData").getString("QueueID");

        return QeueMode;
    }

    public String GetNameByPuuid(String puuid) throws IOException, JSONException, NoSuchAlgorithmException, KeyManagementException, ExecutionException, InterruptedException{

        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Headers headers = new Headers.Builder()
                        .add("Authorization","Bearer "+ GetAccessToken())
                        .add("X-Riot-Entitlements-JWT",GetEntitlementToken())
                        .build();

                JSONObject requestBody = new JSONObject();

                String[] payload = new String[]{puuid};
                Request request = new Request.Builder()
                        .url("https://pd.eu.a.pvp.net/name-service/v2/players")
                        .headers(headers)
                        .post(RequestBody.create(MediaType.parse("application/json"), gson.toJson(requestBody)))
                        .build();

                Response response = miscClient.newCall(request).execute();

                String ResponseString = response.body().string();

                return  new JSONArray(ResponseString).getJSONObject(0).getString("GameName");
            }
        };

        return executor.submit(callable).get();

    }


}
