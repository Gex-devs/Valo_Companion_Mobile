package com.gex.gex_riot_take_a_shit;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {


    @Before
    public void setup() {

    }


    @Test
    public void adition_isCorrect() {
        System.out.println("test?");
    }

    @Test
    public void RiotStore(){
        String username = "";
        String password = "";

        OkHttpClient client = new OkHttpClient();

        // Step 1: Perform authorization
        Request initialAuthRequest = new Request.Builder()
                .url("https://auth.riotgames.com/api/v1/authorization")
                .post(RequestBody.create(MediaType.parse("application/json"),
                        "{\"client_id\":\"play-valorant-web-prod\",\"nonce\":\"1\",\"redirect_uri\":\"https://playvalorant.com/opt_in\",\"response_type\":\"token id_token\"}"))
                .build();
        client.newCall(initialAuthRequest).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // Step 2: Send login credentials
                Request loginRequest = new Request.Builder()
                        .url("https://auth.riotgames.com/api/v1/authorization")
                        .put(RequestBody.create(MediaType.parse("application/json"),
                                "{\"type\":\"auth\",\"username\":\"" + username + "\",\"password\":\"" + password + "\"}"))
                        .build();
                client.newCall(loginRequest).enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseBody = Objects.requireNonNull(response.body()).string();

                        // Step 3: Extract access token and other information
                        Pattern pattern = Pattern.compile("access_token=((?:[a-zA-Z]|\\d|\\.|\\-|_)*).*id_token=((?:[a-zA-Z]|\\d|\\.|\\-|_)*).*expires_in=(\\d*)");
                        Matcher matcher = pattern.matcher(responseBody);
                        if (matcher.find()) {
                            String accessToken = matcher.group(1);
                            String idToken = matcher.group(2);
                            String expiresIn = matcher.group(3);
                            System.out.println("Access Token: " + accessToken);
                            System.out.println("ID Token: " + idToken);
                            System.out.println("Expires In: " + expiresIn);

                            // Step 4: Make requests using the access token
                            Request entitlementsRequest = new Request.Builder()
                                    .url("https://entitlements.auth.riotgames.com/api/token/v1")
                                    .header("Authorization", "Bearer " + accessToken)
                                    .post(RequestBody.create(null, new byte[0]))
                                    .build();
                            client.newCall(entitlementsRequest).enqueue(new Callback() {
                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    String entitlementsBody = Objects.requireNonNull(response.body()).string();
                                    String entitlementsToken = extractEntitlementsToken(entitlementsBody);
                                    System.out.println("Entitlements Token: " + entitlementsToken);

                                    // Additional requests can be made here using the access token and entitlements token
                                }

                                @Override
                                public void onFailure(Call call, IOException e) {
                                    e.printStackTrace();
                                }
                            });
                        } else {
                            throw new RuntimeException("Access token not found in response");
                        }
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });

        // Wait for the requests to complete
        try {
            Thread.sleep(5000); // Adjust as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static String extractEntitlementsToken(String responseBody) {
        // Extract entitlements token from the response body
        // Implement the extraction logic based on the actual response format
        return "entitlements_token";
    }

//    @Test
//    public void WebsocketTest() throws URISyntaxException {
//        try{
//            WebsocketServer client = new WebsocketServer(new URI("ws://192.168.1.19:8765"));
//            client.connect();
//            System.out.println("hello "+client.isOpen());
//            Assert.assertTrue(client.isOpen());
//        }catch (Exception e){
//            System.out.println(e);
//        }
//
//    }
}
