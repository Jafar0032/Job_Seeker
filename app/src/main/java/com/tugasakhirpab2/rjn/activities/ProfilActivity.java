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

        showUserData();

        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editUserData();
            }
        });

        showUserData();
    }

    private void showUserData() {
        Intent intent = getIntent();

        String email = intent.getStringExtra("email");
        String fullName = intent.getStringExtra("fullName");
        String gender = intent.getStringExtra("gender");
        String birthDate = intent.getStringExtra("birthDate");
        String address = intent.getStringExtra("address");
        String password = intent.getStringExtra("password");

        binding.tvEmail.setText(email);
        binding.tvNama.setText(fullName);
        binding.tvJenKel.setText(gender);
        binding.tvTglLahir.setText(birthDate);
        binding.tvAlamat.setText(address);
        binding.tvPassword.setText(password);
    }

    private void editUserData() {
        mAuth = FirebaseAuth.getInstance();

        String email = binding.tvEmail.getText().toString();
        String password = binding.tvPassword.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(ProfilActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
//                            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
//                            startActivity(intent);
//                            finish();
                            String userId = task.getResult().getUser().getUid();
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                            Query checkUserInDatabase = reference.orderByChild(userId);

                            checkUserInDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    if (snapshot.exists()) {
                                        String passwordFromDB = snapshot.child(userId).child("password").getValue(String.class);

                                        if (passwordFromDB.equalsIgnoreCase(password)) {
//                                            Pass the data using intent

                                            String emailFromDB = snapshot.child(userId).child("email").getValue(String.class);
                                            String fullNameFromDB = snapshot.child(userId).child("fullName").getValue(String.class);
                                            String genderFromDB = snapshot.child(userId).child("gender").getValue(String.class);
                                            String birthDateFromDB = snapshot.child(userId).child("birthDate").getValue(String.class);
                                            String addressFromDB = snapshot.child(userId).child("address").getValue(String.class);
                                            String passwordfromDB = snapshot.child(userId).child("password").getValue(String.class);

                                            Intent intent = new Intent(ProfilActivity.this, UbahProfilActivity.class);

                                            intent.putExtra("email", emailFromDB);
                                            intent.putExtra("fullName", fullNameFromDB);
                                            intent.putExtra("gender", genderFromDB);
                                            intent.putExtra("birthDate", birthDateFromDB);
                                            intent.putExtra("address", addressFromDB);
                                            intent.putExtra("password", passwordfromDB);

                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(ProfilActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(ProfilActivity.this, "User Id Doesn't Exist", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        } else {
                            Toast.makeText(ProfilActivity.this, "Authentication failed, check your email and password or sign up", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}