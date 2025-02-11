package com.example.sectorx;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private ProgressBar progressBar;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the WebView and ProgressBar
        webView = findViewById(R.id.webview);
        progressBar = findViewById(R.id.progressBar);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);

        // Enable JavaScript for the WebView
        webView.getSettings().setJavaScriptEnabled(true);

        // Enable HTML5 video support (important for video playback)
        webView.getSettings().setMediaPlaybackRequiresUserGesture(false); // Allow autoplay without user interaction

        // Set a WebViewClient to handle links and prevent opening in an external browser
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, android.graphics.Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                // Show the ProgressBar while loading
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // Hide the ProgressBar when the page finishes loading
                progressBar.setVisibility(View.GONE);

                // Save in SharedPreferences that the WebView has finished loading
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isWebViewLoaded", true);
                editor.apply(); // Commit the change
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, android.webkit.WebResourceError error) {
                super.onReceivedError(view, request, error);
                // Handle errors (e.g., no internet connection)
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Error loading page", Toast.LENGTH_SHORT).show();
            }
        });

        // Set a WebChromeClient for handling events like loading progress
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                // Update the progress bar as the page loads
                progressBar.setProgress(newProgress);
            }

            @Override
            public void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback) {
                super.onShowCustomView(view, callback);
                // Custom view for handling full-screen videos (e.g., YouTube)
            }

            @Override
            public void onHideCustomView() {
                super.onHideCustomView();
                // Remove the full-screen video view when the video ends
            }
        });

        // Load the website URL
        webView.loadUrl("https://www.sectorx.in"); // Replace with your website URL
    }

    @Override
    public void onBackPressed() {
        // Handle back press to navigate back in WebView history
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
