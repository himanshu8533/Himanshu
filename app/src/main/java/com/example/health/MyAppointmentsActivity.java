package com.example.health;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class MyAppointmentsActivity extends AppCompatActivity {

    private String[][] appointment_details = {};
    HashMap<String, String> item;
    ArrayList list;
    SimpleAdapter sa;
    ListView lst;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appointments);

        btn = findViewById(R.id.buttonMABack);
        lst = findViewById(R.id.listViewMA);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Database db = new Database(getApplicationContext(), "health", null, 1);
        SharedPreferences sharedpreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String username = sharedpreferences.getString("username", "").toString();
        
        Cursor c = db.getAppointmentData(username);
        appointment_details = new String[c.getCount()][5];
        if (c.moveToFirst()) {
            for (int i = 0; i < appointment_details.length; i++) {
                appointment_details[i][0] = c.getString(1); // Doctor Name
                appointment_details[i][1] = c.getString(2); // Hospital Address
                appointment_details[i][2] = "Fees: " + c.getString(7);
                appointment_details[i][3] = "Date: " + c.getString(5) + " Time: " + c.getString(6);
                appointment_details[i][4] = "";
                c.moveToNext();
            }
        }

        list = new ArrayList();
        for (int i = 0; i < appointment_details.length; i++) {
            item = new HashMap<String, String>();
            item.put("line1", appointment_details[i][0]);
            item.put("line2", appointment_details[i][1]);
            item.put("line3", appointment_details[i][2]);
            item.put("line4", appointment_details[i][3]);
            item.put("line5", appointment_details[i][4]);
            list.add(item);
        }

        sa = new SimpleAdapter(this, list,
                R.layout.multi_lines,
                new String[]{"line1", "line2", "line3", "line4", "line5"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e});
        lst.setAdapter(sa);
    }
}
