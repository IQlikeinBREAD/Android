package com.example.filaret_vasiliev_pr;

import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private EditText input1, input2;
    private ArrayList<String> listItems;
    private ArrayAdapter<String> adapter;
    private SharedPreferences preferences;
    private SharedPreferences prefsForPause;
    private int pauseCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input1 = findViewById(R.id.inputField1);
        input2 = findViewById(R.id.inputField2);
        Button addButton = findViewById(R.id.addButton);
        Button openSecondActivityButton = findViewById(R.id.openSecondActivityButton);
        Button clearListButton = findViewById(R.id.clearListButton);
        ListView listView = findViewById(R.id.listView);

        preferences = getSharedPreferences("AppData", MODE_PRIVATE);
        prefsForPause = getSharedPreferences("PAUSES", MODE_PRIVATE);
        listItems = new ArrayList<>(preferences.getStringSet("list", new HashSet<>()));
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItems);
        listView.setAdapter(adapter);

        addButton.setOnClickListener(v -> {
            String item1 = input1.getText().toString().trim();
            String item2 = input2.getText().toString().trim();
            if (!item1.isEmpty() && !item2.isEmpty()) {
                listItems.add(item1 + " - " + item2);
                adapter.notifyDataSetChanged();
                saveData();
                input1.setText("");
                input2.setText("");
            } else {
                Toast.makeText(this, "Wprowadź oba pola!", Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemClickListener((parent, view, position, id) ->
                Toast.makeText(this, listItems.get(position), Toast.LENGTH_SHORT).show());

        openSecondActivityButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra("word1", input1.getText().toString());
            intent.putExtra("word2", input2.getText().toString());
            startActivity(intent);
        });

        clearListButton.setOnClickListener(v -> {
            listItems.clear();
            adapter.notifyDataSetChanged();
            saveData();
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        int pauses = prefsForPause.getInt("PAUSES", 1);
        pauses++;
        Toast.makeText(this, "App paused: " + pauses, Toast.LENGTH_SHORT).show();
        prefsForPause.edit().putInt("PAUSES", pauses).apply();
    }

    private void saveData() {
        Set<String> set = new HashSet<>(listItems);
        preferences.edit().putStringSet("list", set).apply();
    }
}