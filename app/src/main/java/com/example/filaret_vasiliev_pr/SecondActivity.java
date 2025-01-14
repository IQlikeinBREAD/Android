package com.example.filaret_vasiliev_pr;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView reversedText = findViewById(R.id.reversedText);
        String word1 = getIntent().getStringExtra("word1");
        String word2 = getIntent().getStringExtra("word2");

        if (word1 != null && word2 != null) {
            String reversed = new StringBuilder(word1).reverse() + " " +
                    new StringBuilder(word2).reverse();
            reversedText.setText(reversed);
        }
    }
}