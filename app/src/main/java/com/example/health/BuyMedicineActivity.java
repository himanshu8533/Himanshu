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

public class BuyMedicineActivity extends AppCompatActivity {
    private String[][] packages =
            {
                    {"Uprise-D3 1000IU Capsule", "", "", "", "50"},
                    {"HealthVit Vitamin B12", "", "", "", "305"},
                    {"Vitamin C 500mg", "", "", "", "448"},
                    {"Iron Supplement", "", "", "", "200"},
                    {"Calcium + Vitamin D3", "", "", "", "150"},
                    {"Folic Acid 5mg", "", "", "", "80"},
                    {"Zincovit Tablet", "", "", "", "130"},
                    {"Multivitamin Capsules", "", "", "", "250"},
                    {"Paracetamol 500mg", "", "", "", "20"}
            };
    private String[] package_details = {
            "Building and keeping the bones & teeth strong\n" +
                    "Reducing Fatigue/Stress and muscular pains\n" +
                    "Boosting immunity and increasing resistance against infection",
            "HealthVit Vitamin B12 500mcg for Mental Health and energy production",
            "Vitamin C 500mg for immunity boosting and antioxidant support",
            "Helps in preventing anemia and improving hemoglobin levels",
            "Essential for bone health and preventing osteoporosis",
            "Vital for cell growth and healthy blood production",
            "Immunity booster with essential minerals and vitamins",
            "Comprehensive daily health support",
            "Effective for fever and mild to moderate pain relief"
    };

    HashMap<String, String> item;
    ArrayList list;
    SimpleAdapter sa;
    ListView lst;
    Button btnBack, btnGoToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_medicine);

        lst = findViewById(R.id.listViewBM);
        btnBack = findViewById(R.id.buttonBMBack);
        btnGoToCart = findViewById(R.id.buttonBMGoToCart);

        btnGoToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BuyMedicineActivity.this, CartMedicineActivity.class));
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BuyMedicineActivity.this, HomeActivity.class));
            }
        });

        list = new ArrayList();
        for (int i = 0; i < packages.length; i++) {
            item = new HashMap<String, String>();
            item.put("line1", packages[i][0]);
            item.put("line2", packages[i][1]);
            item.put("line3", packages[i][2]);
            item.put("line4", packages[i][3]);
            item.put("line5", "Total Cost:" + packages[i][4] + "/-");
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
                Intent it = new Intent(BuyMedicineActivity.this, MedicineDetailsActivity.class);
                it.putExtra("text1", packages[i][0]);
                it.putExtra("text2", package_details[i]);
                it.putExtra("text3", packages[i][4]);
                startActivity(it);
            }
        });
    }
}
