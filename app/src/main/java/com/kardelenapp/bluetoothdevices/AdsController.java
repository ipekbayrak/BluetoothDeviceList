package com.kardelenapp.bluetoothdevices;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

/**
 * Created by mustafa on 11/7/2017.
 */

public class AdsController {
    String bannerAdsTEST = "ca-app-pub-3940256099942544/6300978111";
    String InterstitialAdsTEST = "ca-app-pub-3940256099942544/1033173712";
    String Rewarded_Video_TEST = "ca-app-pub-3940256099942544/5224354917";
    String Native_Advanced_TEST = "ca-app-pub-3940256099942544/2247696110";
    String Native_Express_Small_TEST = ":ca-app-pub-3940256099942544/2793859312";
    String Native_Express_Large_TEST = "ca-app-pub-3940256099942544/2177258514";

    String fullscreeenads = "ca-app-pub-3312738864772003/8834975907";
    String videoads = "ca-app-pub-3312738864772003/6791691521";
    String bannerads = "ca-app-pub-3312738864772003/4764293985";

    Context context;

    public AdsController(Context context) {
        this.context = context;
    }


    private RewardedVideoAd mRewardedVideoAd;
    private InterstitialAd mInterstitialAd;
    private RewardedVideoAdListener listener;
    private AdRequest adRequest;
    private AdView mAdView;
    public void RevardedVideoAdsPrepare(){

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(context);



       listener = new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoAdLoaded() {

            }

            @Override
            public void onRewardedVideoAdOpened() {

            }

            @Override
            public void onRewardedVideoStarted() {

            }

            @Override
            public void onRewardedVideoAdClosed() {
                loadRevardedVideo();
            }

            @Override
            public void onRewarded(RewardItem rewardItem) {

            }

            @Override
            public void onRewardedVideoAdLeftApplication() {

            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {
                loadRevardedVideo();
            }
        };



        loadRevardedVideo();


    }

    public void InterstitialAdsPrepare(){


        mInterstitialAd = new InterstitialAd(context);

        if (BuildConfig.DEBUG) {
            mInterstitialAd.setAdUnitId(InterstitialAdsTEST);
        }
        else {
            mInterstitialAd.setAdUnitId(fullscreeenads);
        }


        InterstitialLoad();



        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                InterstitialLoad();
            }
        });
    }

    public void showInterstatialAds(){
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }

    }

    public void InterstitialLoad(){
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

    }
    public void loadRevardedVideo(){
        mRewardedVideoAd.setRewardedVideoAdListener(listener);

        if (BuildConfig.DEBUG) {
            mRewardedVideoAd.loadAd(Rewarded_Video_TEST, new AdRequest.Builder().build());
        }
        else {
            mRewardedVideoAd.loadAd(videoads, new AdRequest.Builder().build());
        }

    }


    public void  showRevardedVideo(){

        if(mRewardedVideoAd.isLoaded())
        {mRewardedVideoAd.show();}
    }


    public void loadBanner(final LinearLayout layout){




        mAdView = new AdView(context);
        mAdView.setAdSize(AdSize.BANNER);

        mAdView.setVisibility(View.GONE);

        ((LinearLayout)layout).addView(mAdView);

        if (BuildConfig.DEBUG) {
            mAdView.setAdUnitId(bannerAdsTEST);

        }
        else {
            mAdView.setAdUnitId(bannerads);
        }



        mAdView.setAdListener(new AdListener() {
            private void showToast(String message) {

                if (mAdView != null) {
                    Toast.makeText(mAdView.getContext(), message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mAdView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                //showToast(String.format("Ad failed to load with error code %d.", errorCode));
                mAdView.setVisibility(View.GONE);
            }

            @Override
            public void onAdOpened() {
                // showToast("Ad opened.");
            }

            @Override
            public void onAdClosed() {
                // showToast("Ad closed.");
            }

            @Override
            public void onAdLeftApplication() {
                // showToast("Ad left application.");
            }
        });

        adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


    }







}
