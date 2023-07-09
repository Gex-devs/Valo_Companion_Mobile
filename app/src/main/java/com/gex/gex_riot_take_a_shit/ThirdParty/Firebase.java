package com.gex.gex_riot_take_a_shit.ThirdParty;

import android.content.Context;

import com.google.firebase.analytics.FirebaseAnalytics;

public class Firebase {
    private FirebaseAnalytics mFirebaseAnalytics;
    public Firebase(Context context){
        // Empty Constructor
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);

    }

}
