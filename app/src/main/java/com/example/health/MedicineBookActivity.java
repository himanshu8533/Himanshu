package com.example.health;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class MedicineBookActivity extends AppCompatActivity {

    EditText edname, edaddress, edcontact, edpincode;
    Button btnBooking, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_medicine_book);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edname = findViewById(R.id.editTextMBFullName);
        edaddress = findViewById(R.id.editTextMBAddress);
        edcontact = findViewById(R.id.editTextMBContact);
        edpincode = findViewById(R.id.editTextMBPincode);
        btnBooking = findViewById(R.id.buttonMBBooking);
        
        // Ensure there is a back button in the layout or add listener to one if it exists
        // Checking activity_medicine_book.xml... It doesn't have a back button. 
        // I should probably add one to be consistent. 
        // Wait, activity_medicine_book.xml has not been read recently. I'll read it.
    }
}
