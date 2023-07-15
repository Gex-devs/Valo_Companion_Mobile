package com.gex.gex_riot_take_a_shit.Utils;

import android.content.SharedPreferences;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.gex.gex_riot_take_a_shit.MainActivity;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class signInChecker {
    private static final String AuthCookiesUrl = "https://auth.riotgames.com/api/v1/authorization";

    public static boolean isCookieAlive(){
        SharedPreferences sharedPreferences = MainActivity.ContextMethod().getSharedPreferences("cookies", MainActivity.ContextMethod().MODE_PRIVATE);
        CookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(sharedPreferences));
        List<Cookie> cookies = cookieJar.loadForRequest(HttpUrl.parse(AuthCookiesUrl));

// Check if cookies are present
        if (!cookies.isEmpty()) {
            // Cookies are present
            for (Cookie cookie : cookies) {
                // Do something with each cookie
                System.out.println("Cookie: " + cookie.toString());
            }
            return true;
        } else {
            // No cookies found
            System.out.println("No cookies found.");
            return false;
        }
    }
}
