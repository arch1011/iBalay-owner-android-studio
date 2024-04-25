package com.example.ibalay_owner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

public class page404 extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page404);

        // Apply padding to the layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            WindowInsetsCompat systemBars = insets;
            v.setPadding(systemBars.getInsets(WindowInsetsCompat.Type.systemBars()).left,
                    systemBars.getInsets(WindowInsetsCompat.Type.systemBars()).top,
                    systemBars.getInsets(WindowInsetsCompat.Type.systemBars()).right,
                    systemBars.getInsets(WindowInsetsCompat.Type.systemBars()).bottom);
            return systemBars;
        });

        // Find the retry button
        Button retryButton = findViewById(R.id.btn_retry);

        // Set click listener for the retry button
        retryButton.setOnClickListener(v -> {
            // Start the WebView activity again
            Intent intent = new Intent(page404.this, webview.class);
            startActivity(intent);
            finish(); // Finish the current activity to prevent going back to it
        });
    }
}