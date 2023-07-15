package com.gex.gex_riot_take_a_shit.fragments;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.gex.gex_riot_take_a_shit.MainActivity;
import com.gex.gex_riot_take_a_shit.R;
import com.gex.gex_riot_take_a_shit.ThirdParty.OfficalValorantApi;
import com.gex.gex_riot_take_a_shit.ThirdParty.ValorantApi;
import com.gex.gex_riot_take_a_shit.Utils.FragmentSwitcher;
import com.gex.gex_riot_take_a_shit.enums.CurrencieIds;
import com.gex.gex_riot_take_a_shit.enums.SkinsVariable;
import com.google.android.material.imageview.ShapeableImageView;
import com.labo.kaji.fragmentanimations.MoveAnimation;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;


public class Store_Fragment extends Fragment {


    ShapeableImageView[] itemOffers;
    ShapeableImageView bundle;
    Button _logoutButton;
    TextView[] currencies;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        // Fragment swap animation
        return MoveAnimation.create(MoveAnimation.RIGHT,enter,200);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_store_, container, false);

        itemOffers = new ShapeableImageView[4];
        itemOffers[0] = v.findViewById(R.id.ItemOffer1);
        itemOffers[1] = v.findViewById(R.id.ItemOffer2);
        itemOffers[2] = v.findViewById(R.id.ItemOffer3);
        itemOffers[3] = v.findViewById(R.id.ItemOffer4);


        bundle = v.findViewById(R.id.bundleImage);


        currencies = new TextView[3];
        currencies[0] = v.findViewById(R.id.valPoint);
        currencies[1] = v.findViewById(R.id.radPoint);
        currencies[2] = v.findViewById(R.id.kingdompoints);


        _logoutButton = v.findViewById(R.id.logout_button);




        try {
            LoadOffers();
            LoadWallet();
        } catch (Exception e){
            throw new RuntimeException(e);
        }


        _logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OfficalValorantApi.getInstance().clearCookies();
                FragmentSwitcher.Store_Fragment();
            }
        });
        return v;
    }

    private void LoadOffers() {
        AsyncTask<Void, Void, Void> loadOffersTask = new AsyncTask<Void, Void, Void>() {
            @SuppressLint("StaticFieldLeak")
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    String[] skins = new String[4];
                    Map<String, Integer> load = OfficalValorantApi.getInstance().GetStore();

                    int index = 0;

                    for (Map.Entry<String, Integer> entry : load.entrySet()) {
                        String ItemID = entry.getKey();
                        Integer Cost = entry.getValue();

                        Log.d("loadstore", "doInBackground: "+ItemID);
                        skins = ValorantApi.GetSkinByLevel(ItemID);

                        if (index < itemOffers.length) {
                            final int currentIndex = index;
                            String[] finalSkins = skins;
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Picasso.with(MainActivity.ContextMethod()).load(finalSkins[SkinsVariable.Image.ordinal()]).into(itemOffers[currentIndex]);
                                }
                            });
                            index++;
                        }
                    }

                    Map<String,Integer> forStoreBundle = OfficalValorantApi.getInstance().GetStoreBundle();

                    for (Map.Entry<String, Integer> entry : forStoreBundle.entrySet()) {
                        String ID = entry.getKey();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Picasso.with(MainActivity.ContextMethod()).load(ValorantApi.GetBundle(ID)).fit()
                                            .centerCrop().into(bundle);
                                } catch (ExecutionException | InterruptedException e ) {
                                    throw new RuntimeException(e);
                                }
                            }
                        });
                    }



                } catch (JSONException | IOException | ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }

                return null;
            }
        };

        loadOffersTask.execute();
    }

    private void LoadWallet() {
        AsyncTask<Void, Void, Map<String, Integer>> loadWalletTask = new AsyncTask<Void, Void, Map<String, Integer>>() {
            @Override
            protected Map<String, Integer> doInBackground(Void... params) {
                try {
                    return OfficalValorantApi.getInstance().GetWallet();
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Map<String, Integer> wallet) {
                if (wallet != null) {
                    for (Map.Entry<String, Integer> entry : wallet.entrySet()) {
                        String walletName = entry.getKey();
                        Integer amount = entry.getValue();

                        if (Objects.equals(walletName, CurrencieIds.ValorantPoint.toString())) {
                            currencies[0].setText(amount.toString());
                        }
                        if (Objects.equals(walletName, CurrencieIds.RadianitePoints.toString())) {
                            currencies[1].setText(amount.toString());
                        }
                        if (Objects.equals(walletName, CurrencieIds.KingdomCredits.toString())){
                            currencies[2].setText(amount.toString());
                        }
                    }
                }
            }
        };

        loadWalletTask.execute();
    }


}