package com.example.health;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HealthArticlesDetailsActivity extends AppCompatActivity {

    TextView tvTitle, tvDetail;
    ImageView img;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_articles_details);

        tvTitle = findViewById(R.id.textViewHATitle);
        tvDetail = findViewById(R.id.textViewHADetail);
        img = findViewById(R.id.imageViewHA);
        btnBack = findViewById(R.id.buttonHABack);

        Intent intent = getIntent();
        tvTitle.setText(intent.getStringExtra("text1"));
        
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int resId = bundle.getInt("text2");
            img.setImageResource(resId);
        }

        // Article content can be set based on title or passed via intent.
        // For simplicity, providing some dummy content based on the title.
        String title = intent.getStringExtra("text1");
        if (title.equals("Walking Daily")) {
            tvDetail.setText("Walking is a great way to improve or maintain your overall health. Just 30 minutes every day can increase cardiovascular fitness, strengthen bones, reduce excess body fat, and boost muscle power and endurance.");
        } else if (title.equals("Home Care of COVID-19")) {
            tvDetail.setText("Most people who get sick with COVID-19 will have only mild illness and should recover at home. Care at home can help stop the spread of COVID-19 and help protect people who are at risk for getting seriously ill from COVID-19.");
        } else if (title.equals("Stop Smoking")) {
            tvDetail.setText("Quitting smoking is one of the most important actions people can take to improve their health. This is true regardless of their age or how long they have been smoking.");
        } else if (title.equals("Menstrual Cramps")) {
            tvDetail.setText("Menstrual cramps are throbbing or cramping pains in the lower abdomen. Many women have menstrual cramps just before and during their menstrual periods.");
        } else if (title.equals("Healthy Gut")) {
            tvDetail.setText("A healthy gut contains healthy bacteria and immune cells that ward off infectious agents like bacteria, viruses, and fungi. A healthy gut also communicates with the brain through nerves and hormones.");
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
