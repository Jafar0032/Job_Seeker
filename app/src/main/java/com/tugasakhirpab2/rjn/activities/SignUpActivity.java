package com.tugasakhirpab2.rjn.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tugasakhirpab2.rjn.R;
import com.tugasakhirpab2.rjn.databinding.ActivitySignUpBinding;
import com.tugasakhirpab2.rjn.model.User;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference reference, mRef;
    private SimpleDateFormat dateFormatter;
    private int mYear,mMonth,mDay;
    private String Famale = "Famale";
    private String Male = "Male";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        reference = mDatabase.getReference();

        binding.etBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(SignUpActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
//                        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                        binding.etBirthDate.setText(String.format("%02d-%02d-%04d",dayOfMonth, month+1, year));
                        mYear = year;
                        mMonth = month;
                        mDay = dayOfMonth;
                    }
                },mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                mDatabase = FirebaseDatabase.getInstance();
//                reference = mDatabase.getReference("users");

                String email = binding.etEmail.getText().toString();
                String fullName = binding.etFullName.getText().toString();
                String gender = binding.etGender.getText().toString();
                String birthDate = binding.etBirthDate.getText().toString();
                String address = binding.etAdddress.getText().toString();
                String password = binding.etPassword.getText().toString();
                String confirmPassword = binding.etKonfPassword.getText().toString();


                if (TextUtils.isEmpty(email)) {
                    binding.etEmail.setError("Enter your email address!");
                    return;
                }
                if (TextUtils.isEmpty(fullName)) {
                    binding.etFullName.setError("Enter your full name!");
                    return;
                }
                if (validateGender()) {
                    return;
                }
                if (TextUtils.isEmpty(birthDate)) {
                    binding.etBirthDate.setError("Enter your birthdate!");
                    return;
                }
                if (TextUtils.isEmpty(address)) {
                    binding.etAdddress.setError("Enter your address!");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    binding.etPassword.setError("Enter your password!");
                    return;
                }
                if (TextUtils.isEmpty(confirmPassword)) {
                    binding.etKonfPassword.setError("Enter your confirm password!");
                    return;
                }
                if (password.length() < 6) {
                    binding.etPassword.setError("Password too short, enter minimum 6 characters!");
                    return;
                }
                if (!confirmPassword.equals(password)) {
                    binding.etKonfPassword.setError("Password doesn't match!");
                    return;
                }

//                User user = new User(email,fullName,userName,gender,birthDate,address,password);
//                reference.child(userName).setValue(user);
//                Toast.makeText(SignUpActivity.this, "You have signup successfully!", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    User user = new User(email,fullName,gender,birthDate,address,password);
                                    String userId = task.getResult().getUser().getUid();
                                    mRef = reference.child("users").child(userId);
                                    mRef.setValue(user);
                                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    Log.w(SignUpActivity.class.getSimpleName(), "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(SignUpActivity.this, "Sign Up failed!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                finish();
            }
        });
    }
    public Boolean validateGender(){
        String val = binding.etGender.getText().toString().trim();
        if (val.isEmpty()){
            binding.etGender.setError("Gender cannot be empty");
            return true;
        } else if(val.equalsIgnoreCase(Famale)){
            binding.etGender.setError(null);
            return false;
        }else if(val.equalsIgnoreCase(Male)){
            binding.etGender.setError(null);
            return false;
        } else {
            binding.etGender.setError("Please fill in male or female");
            return true;
        }
    }
}