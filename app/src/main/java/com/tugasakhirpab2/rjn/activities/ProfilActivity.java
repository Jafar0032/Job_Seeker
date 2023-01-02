package com.tugasakhirpab2.rjn.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.tugasakhirpab2.rjn.R;
import com.tugasakhirpab2.rjn.databinding.ActivityProfilBinding;

public class ProfilActivity extends AppCompatActivity {

    ActivityProfilBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityProfilBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfilActivity.this, UbahProfilActivity.class);
                startActivity(intent);
            }
        });

        showUserData();
    }

    private void showUserData() {
        Intent intent = getIntent();

        String fullName = intent.getStringExtra("fullName");
        String gender = intent.getStringExtra("gender");
        String birthDate = intent.getStringExtra("birthDate");
        String address = intent.getStringExtra("address");

        binding.tvFullName.setText(fullName);
        binding.tvJenisKelamin.setText(gender);
        binding.tvTanggalLahir.setText(birthDate);
        binding.tvAlamat.setText(address);
    }

}