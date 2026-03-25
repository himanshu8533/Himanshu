package com.example.health;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class HealthArticlesActivity extends AppCompatActivity {

    private String[][] health_articles =
            {
                    {"Walking Daily", "", "", "", "Click to Read More"},
                    {"Home Care of COVID-19", "", "", "", "Click to Read More"},
                    {"Stop Smoking", "", "", "", "Click to Read More"},
                    {"Menstrual Cramps", "", "", "", "Click to Read More"},
                    {"Healthy Gut", "", "", "", "Click to Read More"}
            };
    private int[] images = {
            android.R.drawable.ic_menu_directions,
            android.R.drawable.ic_menu_myplaces,
            android.R.drawable.ic_menu_close_clear_cancel,
            android.R.drawable.ic_menu_info_details,
            android.R.drawable.ic_menu_manage
    };
    HashMap<String, String> item;
    ArrayList list;
    SimpleAdapter sa;
    Button btnBack;
    ListView lst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_articles);

        lst = findViewById(R.id.listViewHA);
        btnBack = findViewById(R.id.buttonHABack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HealthArticlesActivity.this, HomeActivity.class));
            }
        });

        list = new ArrayList();
        for (int i = 0; i < health_articles.length; i++) {
            item = new HashMap<String, String>();
            item.put("line1", health_articles[i][0]);
            item.put("line2", health_articles[i][1]);
            item.put("line3", health_articles[i][2]);
            item.put("line4", health_articles[i][3]);
            item.put("line5", health_articles[i][4]);
            list.add(item);
        }

        sa = new SimpleAdapter(this, list,
                R.layout.multi_lines,
                new String[]{"line1", "line2", "line3", "line4", "line5"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e});
        lst.setAdapter(sa);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(HealthArticlesActivity.this, HealthArticlesDetailsActivity.class);
                it.putExtra("text1", health_articles[i][0]);
                it.putExtra("text2", images[i]);
                startActivity(it);
            }
        });
    }
}
