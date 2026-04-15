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
        btnBack = findViewById(R.id.buttonMBBack);

        Intent intent = getIntent();
        String priceStr = intent.getStringExtra("price");
        String date = intent.getStringExtra("date");

        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edname.getText().toString();
                String address = edaddress.getText().toString();
                String contact = edcontact.getText().toString();
                String pincode = edpincode.getText().toString();

                if (name.isEmpty() || address.isEmpty() || contact.isEmpty() || pincode.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill all details", Toast.LENGTH_SHORT).show();
                    return;
                }

                SharedPreferences sharedpreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username = sharedpreferences.getString("username", "");

                Database db = new Database(getApplicationContext(), "health", null, 1);
                
                float amount = 0;
                try {
                    if (priceStr != null) {
                        String[] parts = priceStr.split(":");
                        if (parts.length > 1) {
                            String val = parts[1].trim();
                            if (val.endsWith("/-")) val = val.substring(0, val.length() - 2);
                            amount = Float.parseFloat(val);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                db.addOrder(username, name, address, contact, Integer.parseInt(pincode), date, "", amount, "medicine");
                db.removeCart(username, "medicine");
                Toast.makeText(getApplicationContext(), "Booking done successfully", Toast.LENGTH_LONG).show();
                
                Intent it = new Intent(MedicineBookActivity.this, HomeActivity.class);
                it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(it);
                finish();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });
    }
}
