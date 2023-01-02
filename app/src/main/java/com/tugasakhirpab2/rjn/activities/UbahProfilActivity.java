package com.tugasakhirpab2.rjn.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
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
import com.tugasakhirpab2.rjn.databinding.ActivityUbahProfilBinding;

import java.util.Calendar;

public class UbahProfilActivity extends AppCompatActivity {
    ActivityUbahProfilBinding binding;
    DatabaseReference reference;
    private FirebaseAuth mAuth;
    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUbahProfilBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        binding.ibDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(UbahProfilActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        binding.etBirthDate.setText(String.format("%02d-%02d-%04d", dayOfMonth, month + 1, year));
                        mYear = year;
                        mMonth = month;
                        mDay = dayOfMonth;
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        showData();

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editDataUser();
            }
        });
    }

    private void showData() {
        Intent intent = getIntent();

        String email = intent.getStringExtra("email");
        String fullName = intent.getStringExtra("fullName");
        String gender = intent.getStringExtra("gender");
        String birthDate = intent.getStringExtra("birthDate");
        String address = intent.getStringExtra("address");
        String password = intent.getStringExtra("password");

        binding.etEmail.setText(email);
        binding.etFullName.setText(fullName);
        binding.etGender.setText(gender);
        binding.etBirthDate.setText(birthDate);
        binding.etAdddress.setText(address);
        binding.etPassword.setText(password);
    }

    private void editDataUser() {

        mAuth = FirebaseAuth.getInstance();

        String email = binding.etEmail.getText().toString();
        String password = binding.etPassword.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(UbahProfilActivity.this, new OnCompleteListener<AuthResult>() {
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

                                            String updateeEmailFromDB = String.valueOf(reference.child(userId).child("email").setValue(binding.etEmail.getText().toString()));
                                            String updateFullNameFromDB = String.valueOf(reference.child(userId).child("fullName").setValue(binding.etFullName.getText().toString()));
                                            String updateGenderFromDB = String.valueOf(reference.child(userId).child("gender").setValue(binding.spinGender.getSelectedItem().toString()));
                                            String updateBirthDateFromDB = String.valueOf(reference.child(userId).child("birthDate").setValue(binding.etBirthDate.getText().toString()));
                                            String updateAddressFromDB = String.valueOf(reference.child(userId).child("address").setValue(binding.etAdddress.getText().toString()));
                                            String updatepPasswordfromDB = String.valueOf(reference.child(userId).child("password").setValue(binding.etPassword.getText().toString()));

                                            Intent intent = new Intent(UbahProfilActivity.this, SignInActivity.class);

                                            intent.putExtra("email", updateeEmailFromDB);
                                            intent.putExtra("fullName", updateFullNameFromDB);
                                            intent.putExtra("gender", updateGenderFromDB);
                                            intent.putExtra("birthDate", updateBirthDateFromDB);
                                            intent.putExtra("address", updateAddressFromDB);
                                            intent.putExtra("password", updatepPasswordfromDB);

                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(UbahProfilActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(UbahProfilActivity.this, "User Id Doesn't Exist", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        } else {
                            Toast.makeText(UbahProfilActivity.this, "Authentication failed, check your email and password or sign up", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}