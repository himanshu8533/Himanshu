package com.example.health;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MedicineBookActivity extends AppCompatActivity {

    EditText edname, edaddress, edcontact, edpincode;
    Button btnBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_book);

        edname = findViewById(R.id.editTextMBFullName);
        edaddress = findViewById(R.id.editTextMBAddress);
        edcontact = findViewById(R.id.editTextMBContact);
        edpincode = findViewById(R.id.editTextMBPincode);
        btnBooking = findViewById(R.id.buttonMBBooking);

        Intent intent = getIntent();
        String[] price = intent.getStringExtra("price").toString().split(java.util.regex.Pattern.quote(":"));
        String date = intent.getStringExtra("date");

        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedpreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username = sharedpreferences.getString("username", "").toString();

                Database db = new Database(getApplicationContext(), "health", null, 1);
                db.addOrder(username, edname.getText().toString(), edaddress.getText().toString(), edcontact.getText().toString(), Integer.parseInt(edpincode.getText().toString()), date, "", Float.parseFloat(price[1].substring(0, price[1].length() - 1)), "medicine");
                db.removeCart(username, "medicine");
                Toast.makeText(getApplicationContext(), "Booking done successfully", Toast.LENGTH_LONG).show();
                startActivity(new Intent(MedicineBookActivity.this, HomeActivity.class));
            }
        });
    }
}
