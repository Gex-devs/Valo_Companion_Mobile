package com.gex.gex_riot_take_a_shit.fragments;

import android.app.AlertDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

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
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class Store_Fragment extends Fragment {


    ShapeableImageView[] itemOffers;
    ShapeableImageView bundle;
    Button _logoutButton;
    TextView[] currencies;

    private SweetAlertDialog loadingDialog;
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

        // Init Items
        itemOffers = new ShapeableImageView[4];
        itemOffers[0] = v.findViewById(R.id.ItemOffer1);
        itemOffers[1] = v.findViewById(R.id.ItemOffer2);
        itemOffers[2] = v.findViewById(R.id.ItemOffer3);
        itemOffers[3] = v.findViewById(R.id.ItemOffer4);

        // Setup on click listeners
        for (ShapeableImageView items: itemOffers) {
            items.setOnClickListener(SkinOnClickHandler);
        }

        bundle = v.findViewById(R.id.bundleImage);

        currencies = new TextView[3];
        currencies[0] = v.findViewById(R.id.valPoint);
        currencies[1] = v.findViewById(R.id.radPoint);
        currencies[2] = v.findViewById(R.id.kingdompoints);


        _logoutButton = v.findViewById(R.id.logout_button);


        showLoadingDialog();

//        OfficalValorantApi.getInstance().clearCookies();

        try {
            LoadOffers();
            LoadWallet();
        } catch (Exception e){
            throw new RuntimeException(e);
        }


        _logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();
            }
        });
        return v;
    }

    private void showLoadingDialog() {
        // Create the loading dialog using Sweet Alert
        loadingDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        loadingDialog.setTitleText("Loading...");
        loadingDialog.setCancelable(false);
        loadingDialog.show();

        loadingTimeoutHandler.postDelayed(loadingTimeoutRunnable, LOADING_TIMEOUT_MS);
    }
    private void dismissLoadingDialog() {
        // Dismiss the loading dialog
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismissWithAnimation();
        }

        loadingTimeoutHandler.removeCallbacks(loadingTimeoutRunnable);
    }
    private void Logout(){
        OfficalValorantApi.getInstance().clearCookies();
        FragmentSwitcher.getInstance().Store_Fragment();
    }
    private static final int LOADING_TIMEOUT_MS = 20000; // 20 seconds

    private Handler loadingTimeoutHandler = new Handler();
    private Runnable loadingTimeoutRunnable = new Runnable() {
        @Override
        public void run() {
            Logout();
            dismissLoadingDialog();
        }
    };

    private void LoadOffers() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String[] skins = new String[3];
                    Map<String, Integer> load = OfficalValorantApi.getInstance().GetStore();

                    int index = 0;

                    for (Map.Entry<String, Integer> entry : load.entrySet()) {
                        String ItemID = entry.getKey();
                        Integer Cost = entry.getValue();
                        skins = ValorantApi.GetSkinByLevel(ItemID);

                        if (index < itemOffers.length) {
                            final int currentIndex = index;
                            String[] finalSkins = skins;
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Picasso.with(MainActivity.ContextMethod()).load(finalSkins[SkinsVariable.Image.ordinal()]).into(itemOffers[currentIndex]);
                                    itemOffers[currentIndex].setTag(finalSkins[SkinsVariable.StreamedVid.ordinal()]);
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
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                } catch (KeyManagementException e) {
                    throw new RuntimeException(e);
                }
                // Dismiss the loading dialog when the task is completed
                dismissLoadingDialog();
            }
        });
    }



    private void LoadWallet() {
        AsyncTask<Void, Void, Map<String, Integer>> loadWalletTask = new AsyncTask<Void, Void, Map<String, Integer>>() {
            @Override
            protected Map<String, Integer> doInBackground(Void... params) {
                try {
                    return OfficalValorantApi.getInstance().GetWallet();
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
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
                // Dismiss the loading dialog when the task is completed
                dismissLoadingDialog();
            }
        };

        loadWalletTask.execute();
    }

    View.OnClickListener SkinOnClickHandler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Object downloadVideo = view.getTag();
            // for some reason its a string null
            if (downloadVideo != "null"){
                VideoPreviewHandler(downloadVideo.toString());
            }else{
                Toast.makeText(getActivity(),"Preview video not available for this Skin",Toast.LENGTH_SHORT).show();
            }

        }
    };

    private void VideoPreviewHandler(String downloadVideo){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View popupView = getLayoutInflater().inflate(R.layout.video_player_popup, null);
        builder.setView(popupView);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();

        VideoView videoView = popupView.findViewById(R.id.videoView);
        videoView.setVideoURI(Uri.parse(downloadVideo));
        videoView.start();

        // Loop the video
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                videoView.start();
            }
        });
    }

}