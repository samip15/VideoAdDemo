package com.example.rewardadmobdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class MainActivity extends AppCompatActivity {
    private Button bnLoad, bnShow;
    private TextView textPoints;
    private RewardedAd rewardedAd;
    private int points = 0;
    private static final String TAG = "MainActivity";

    //creating Object of RewardedAdLoadCallback
    RewardedAdLoadCallback rewardedAdLoadCallback;

    //creating Object of RewardedAdCallback
    RewardedAdCallback rewardedAdCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                Toast.makeText(MainActivity.this, "AdMob Sdk Initialize " + initializationStatus.toString(), Toast.LENGTH_LONG).show();

            }
        });
        //Initializing the RewardedAd  objects
        rewardedAd = new RewardedAd(this, "ca-app-pub-3940256099942544/5224354917");
        bnLoad = findViewById(R.id.load_button);
        bnShow = findViewById(R.id.show_button);
        textPoints = findViewById(R.id.coins);
        bnLoad.setOnClickListener((v) -> {
            loadRewardedAd();
        });
        bnShow.setOnClickListener((v) -> {
            showRewardedAd();
        });

        // creating  RewardedAdLoadCallback for Rewarded Ad with some 2 Override methods
        rewardedAdLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                // Showing a simple Toast message to user when Rewarded Ad Failed to Load
                Toast.makeText(MainActivity.this, "Rewarded Ad is Loaded", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRewardedAdFailedToLoad(LoadAdError adError) {
                // Showing a simple Toast message to user when Rewarded Ad Failed to Load
                Toast.makeText(MainActivity.this, "Rewarded Ad is Loaded", Toast.LENGTH_LONG).show();
            }
        };
    }

    private void showRewardedAd() {
        if (rewardedAd.isLoaded()) {

            //creating the Rewarded Ad Callback and showing the user appropriate message
            rewardedAdCallback = new RewardedAdCallback() {
                @Override
                public void onRewardedAdOpened() {
                    // Showing a simple Toast message to user when Rewarded Ad is opened
                    Toast.makeText(MainActivity.this, "Rewarded Ad is Opened", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onRewardedAdClosed() {
                    // Showing a simple Toast message to user when Rewarded Ad is closed
                    Toast.makeText(MainActivity.this, "Rewarded Ad Closed", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onUserEarnedReward(RewardItem reward) {
                    // Showing a simple Toast message to user when user earned the reward by completely watching the Rewarded Ad
                    Toast.makeText(MainActivity.this, "You won the reward :" + reward.getAmount(), Toast.LENGTH_LONG).show();
                    points = points + 5;
                    textPoints.setText("Points: " + points);
                }

                @Override
                public void onRewardedAdFailedToShow(AdError adError) {
                    // Showing a simple Toast message to user when Rewarded Ad Failed to Show
                    Toast.makeText(MainActivity.this, "Rewarded Ad failed to show due to error:" + adError.toString(), Toast.LENGTH_LONG).show();
                }
            };

            //showing the ad Rewarded Ad if it is loaded
            rewardedAd.show(MainActivity.this, rewardedAdCallback);

            // Showing a simple Toast message to user when an Rewarded ad is shown to the user
            Toast.makeText(MainActivity.this, "Rewarded Ad  is loaded and Now showing ad  ", Toast.LENGTH_LONG).show();
        } else {
            //Load the Rewarded ad if it is not loaded
            loadRewardedAd();

            // Showing a simple Toast message to user when Rewarded ad is not loaded
            Toast.makeText(MainActivity.this, "Rewarded Ad is not Loaded ", Toast.LENGTH_LONG).show();

        }
    }

    private void loadRewardedAd() {
        // Creating  an Ad Request
        AdRequest adRequest = new AdRequest.Builder().build();

        // load Rewarded Ad with the Request
        rewardedAd.loadAd(adRequest, rewardedAdLoadCallback);

        // Showing a simple Toast message to user when Rewarded an ad is Loading
        Toast.makeText(MainActivity.this, "Rewarded Ad is loading ", Toast.LENGTH_LONG).show();
    }
}