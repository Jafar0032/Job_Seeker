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
import com.tugasakhirpab2.rjn.model.User;

public class ProfilActivity extends AppCompatActivity {

    ActivityProfilBinding binding;
    private FirebaseAuth mAuth;
    private String userId;
    private DatabaseReference mRoot, mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityProfilBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        mRoot = FirebaseDatabase.getInstance().getReference();
        mRef = mRoot.child("users").child(userId);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String nama, jenisKelamin, birthDate, address;
                User user = snapshot.getValue(User.class);
                nama = user.getFullName();
                jenisKelamin = user.getGender();
                birthDate = user.getBirthDate();
                address = user.getAddress();

                binding.tvFullName.setText(nama);
                binding.tvJenisKelamin.setText(jenisKelamin);
                binding.tvTanggalLahir.setText(birthDate);
                binding.tvAlamat.setText(address);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
                intent.putExtra("fullName", String.valueOf(binding.tvFullName));
                intent.putExtra("gender", String.valueOf(binding.tvJenisKelamin));
                intent.putExtra("fulbirthDatelName", String.valueOf(binding.tvTanggalLahir));
                intent.putExtra("address", String.valueOf(binding.tvAlamat));
                startActivity(intent);
            }
        });

//        showUserData();
    }

//    private void showUserData() {
//        Intent intent = getIntent();
//
//        String fullName = intent.getStringExtra("fullName");
//        String gender = intent.getStringExtra("gender");
//        String birthDate = intent.getStringExtra("birthDate");
//        String address = intent.getStringExtra("address");
//
//        binding.tvFullName.setText(fullName);
//        binding.tvJenisKelamin.setText(gender);
//        binding.tvTanggalLahir.setText(birthDate);
//        binding.tvAlamat.setText(address);
//    }

}