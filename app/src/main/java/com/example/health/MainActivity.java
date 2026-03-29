package com.example.health;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText etDisease;
    private Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etDisease = findViewById(R.id.etDisease);
        btnSearch = findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String disease = etDisease.getText().toString().trim();
                if (!disease.isEmpty()) {
                    searchDoctors(disease);
                } else {
                    Toast.makeText(MainActivity.this, "Please enter a disease", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void searchDoctors(String disease) {
        String query = "doctors for " + disease + " near me";
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(query));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            // Fallback to web search if Google Maps is not available
            Uri webSearchUri = Uri.parse("https://www.google.com/search?q=" + Uri.encode(query));
            Intent webIntent = new Intent(Intent.ACTION_VIEW, webSearchUri);
            startActivity(webIntent);
        }
    }
}
