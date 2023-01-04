package com.tugasakhirpab2.rjn.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.tugasakhirpab2.rjn.databinding.ActivitySignInBinding;

public class SignInActivity extends AppCompatActivity {
    private ActivitySignInBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = binding.etEmail.getText().toString();
                String password = binding.etPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    binding.etEmail.setError("Enter your email address!");
                    return;
                } else if (TextUtils.isEmpty(password)) {
                    binding.etPassword.setError("Enter your password!");
                    return;
                } else {
                    checkUser();
                }

            }
        });

        binding.btnForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, ResetPassActivity.class));
                finish();
            }
        });

        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
                finish();
            }
        });
    }

    private Boolean validateEmail() {
        String val = binding.etEmail.getText().toString();
        if (val.isEmpty()) {
            binding.etEmail.setError("Email cannot be empty");
            return false;
        } else {
            binding.etEmail.setError(null);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = binding.etPassword.getText().toString();
        if (val.isEmpty()) {
            binding.etPassword.setError("Password cannot be empty");
            return false;
        } else {
            binding.etPassword.setError(null);
            return true;
        }
    }

    private void checkUser() {
        mAuth = FirebaseAuth.getInstance();

        String email = binding.etEmail.getText().toString();
        String password = binding.etPassword.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
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

                                            Intent intent = new Intent(SignInActivity.this, MainActivity.class);

                                            intent.putExtra("email", emailFromDB);
                                            intent.putExtra("fullName", fullNameFromDB);
                                            intent.putExtra("gender", genderFromDB);
                                            intent.putExtra("birthDate", birthDateFromDB);
                                            intent.putExtra("address", addressFromDB);
                                            intent.putExtra("password", passwordfromDB);

                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(SignInActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(SignInActivity.this, "User Id Doesn't Exist", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        } else {
                            Toast.makeText(SignInActivity.this, "Authentication failed, check your email and password or sign up", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
//        Query checkUserInDatabase = reference.orderByChild("userName").equalTo(userUsername);
//
//        checkUserInDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                if (snapshot.exists()){
//                    binding.etUsername.setError(null);
//                    String passwordFromDB = snapshot.child(userUsername).child("password").getValue(String.class);
//
//                    if (passwordFromDB.equals(userPassword)){
//                        binding.etUsername.setError(null);
//
//                        //Pass the data using intent
//
//                        String emailFromDB = snapshot.child(userUsername).child("email").getValue(String.class);
//                        String realNameFromDB = snapshot.child(userUsername).child("realName").getValue(String.class);
//                        String usernameFromDB = snapshot.child(userUsername).child("userName").getValue(String.class);
//                        String genderFromDB = snapshot.child(userUsername).child("gender").getValue(String.class);
//                        String birthDateFromDB = snapshot.child(userUsername).child("birthDate").getValue(String.class);
//                        String addressFromDB = snapshot.child(userUsername).child("address").getValue(String.class);
//                        String passwordfromDB = snapshot.child(userUsername).child("password").getValue(String.class);
//
//                        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
//
//                        intent.putExtra("email", emailFromDB);
//                        intent.putExtra("realName", realNameFromDB);
//                        intent.putExtra("username", usernameFromDB);
//                        intent.putExtra("gender", genderFromDB);
//                        intent.putExtra("birthDate", birthDateFromDB);
//                        intent.putExtra("address", addressFromDB);
//                        intent.putExtra("password", passwordfromDB);
//
//                        startActivity(intent);
//                    } else {
//                        binding.etPassword.setError("Invalid Credentials");
//                        binding.etPassword.requestFocus();
//                    }
//                } else {
//                    binding.etUsername.setError("User does not exist");
//                    binding.etUsername.requestFocus();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }
}