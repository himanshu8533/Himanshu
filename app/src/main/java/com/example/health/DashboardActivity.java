package com.example.health;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DashboardActivity extends AppCompatActivity {

    TextView tvUsername, tvEmail, tvAppCount, tvOrderCount;
    Button btnBack;
    ImageView imgProfile;
    CardView cardProfile, cardAppointments, cardOrders;

    private final ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri imageUri = result.getData().getData();
                    if (saveImageToInternalStorage(imageUri)) {
                        loadProfilePicture();
                        Toast.makeText(this, "Profile picture updated", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Failed to update profile picture", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        tvUsername = findViewById(R.id.textViewDashboardUsername);
        tvEmail = findViewById(R.id.textViewDashboardEmail);
        tvAppCount = findViewById(R.id.textViewDashboardAppCount);
        tvOrderCount = findViewById(R.id.textViewDashboardOrderCount);
        btnBack = findViewById(R.id.buttonDashboardBack);
        imgProfile = findViewById(R.id.imageViewDashboardProfile);
        cardProfile = findViewById(R.id.cardDashboardProfile);
        cardAppointments = findViewById(R.id.cardDashboardAppointments);
        cardOrders = findViewById(R.id.cardDashboardOrders);

        SharedPreferences sharedpreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String username = sharedpreferences.getString("username", "");
        tvUsername.setText(username);

        Database db = new Database(getApplicationContext(), "health", null, 1);
        String email = db.getEmail(username);
        tvEmail.setText(email);

        int appCount = db.getCount(username, "appointment");
        int orderCount = db.getCount(username, "lab") + db.getCount(username, "medicine");

        tvAppCount.setText(String.valueOf(appCount));
        tvOrderCount.setText(String.valueOf(orderCount));

        loadProfilePicture();

        cardProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProfileDialog();
            }
        });

        cardAppointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, MyAppointmentsActivity.class));
            }
        });

        cardOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, OrderDetailsActivity.class));
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, HomeActivity.class));
                finish();
            }
        });
    }

    private void showProfileDialog() {
        String[] options = {"Change Picture", "Remove Picture", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Profile Picture");
        builder.setItems(options, (dialog, which) -> {
            if (which == 0) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryLauncher.launch(intent);
            } else if (which == 1) {
                removeProfilePicture();
            } else {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void removeProfilePicture() {
        SharedPreferences sharedpreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String imagePath = sharedpreferences.getString("profile_image_path", "");
        if (!imagePath.isEmpty()) {
            File file = new File(imagePath);
            if (file.exists()) {
                file.delete();
            }
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.remove("profile_image_path");
            editor.apply();
            
            // Reset to default icon
            imgProfile.setImageResource(android.R.drawable.ic_menu_gallery);
            imgProfile.setPadding(20, 20, 20, 20); // Restore original padding
            Toast.makeText(this, "Profile picture removed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No profile picture to remove", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadProfilePicture() {
        SharedPreferences sharedpreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String imagePath = sharedpreferences.getString("profile_image_path", "");
        if (!imagePath.isEmpty()) {
            File file = new File(imagePath);
            if (file.exists()) {
                imgProfile.setPadding(0, 0, 0, 0);
                imgProfile.setImageURI(Uri.fromFile(file));
            } else {
                setDefaultProfileIcon();
            }
        } else {
            setDefaultProfileIcon();
        }
    }
    
    private void setDefaultProfileIcon() {
        imgProfile.setImageResource(android.R.drawable.ic_menu_gallery);
        imgProfile.setPadding(20, 20, 20, 20);
    }

    private boolean saveImageToInternalStorage(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            if (inputStream == null) return false;
            
            File file = new File(getFilesDir(), "profile_pic.jpg");
            OutputStream outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();

            SharedPreferences sharedpreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("profile_image_path", file.getAbsolutePath());
            editor.apply();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
