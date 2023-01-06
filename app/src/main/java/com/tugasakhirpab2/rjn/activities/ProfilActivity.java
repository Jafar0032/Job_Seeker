package com.tugasakhirpab2.rjn.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityProfilBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        userId = mAuth.getCurrentUser().getUid();
        mRoot = FirebaseDatabase.getInstance().getReference();
        mRef = mRoot.child("users").child(userId);
        if (firebaseUser != null) {
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

                    //Set foto profile after upload
                    Uri uri = firebaseUser.getPhotoUrl();

                    Glide.with(binding.ivProfil.getContext())
                            .load(uri)
                            .fitCenter()
                            .placeholder(R.drawable.img_placeholder)
                            .into(binding.ivProfil);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {
            Toast.makeText(this, "No User!!", Toast.LENGTH_SHORT).show();
        }


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

        binding.ivProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfilActivity.this, UploadProfileImageActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}