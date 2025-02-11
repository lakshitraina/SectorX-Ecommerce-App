package com.example.sectorx;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);

        // Start a delay to check WebView loading status after 3 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Check if WebView has finished loading based on the SharedPreferences flag
                boolean isWebViewLoaded = sharedPreferences.getBoolean("isWebViewLoaded", false);

                if (isWebViewLoaded) {
                    // If WebView is loaded, proceed to MainActivity
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // If WebView is not yet loaded, we can either wait more or just proceed with the transition anyway
                    // For now, just proceed after a fixed delay
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 3000); // 3 seconds delay to check WebView status
    }
}
