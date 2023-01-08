package com.tugasakhirpab2.rjn.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tugasakhirpab2.rjn.R;
import com.tugasakhirpab2.rjn.databinding.ActivityUploadProfileImageBinding;
import com.tugasakhirpab2.rjn.model.FileInModel;
import com.tugasakhirpab2.rjn.model.User;

import java.io.File;

public class UploadProfileImageActivity extends AppCompatActivity {
    ActivityUploadProfileImageBinding binding;
    private FirebaseAuth mAuth;
    private String userId;
    private ImageView imageView_profile_dp;
    private Button uploadPicButton;
    private DatabaseReference mRoot, mRef;
    private StorageReference storageReference;
    private FirebaseUser firebaseUser;
    private Uri uriImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUploadProfileImageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        imageView_profile_dp = findViewById(R.id.imageView_profile_dp);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        userId = mAuth.getCurrentUser().getUid();

        mRoot = FirebaseDatabase.getInstance().getReference();
        mRef = mRoot.child("users").child(userId);

        storageReference = FirebaseStorage.getInstance().getReference("PhotoProfile");

        Uri uri = firebaseUser.getPhotoUrl();
        Glide.with(imageView_profile_dp.getContext())
                .load(uriImage)
                .fitCenter()
                .into(imageView_profile_dp);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UploadProfileImageActivity.this, ProfilActivity.class);
                startActivity(intent);
                finish();
            }
        });

        binding.btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileCooser();
            }
        });

        binding.btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });

    }

    private void uploadImage() {
        showProgressBar();

        if (uriImage != null) {
            //Save Image with uid of the curently logged user
            StorageReference fileReference = storageReference.child(userId);

            //upload image to storage
            fileReference.putFile(uriImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Uri downloadUri = uri;
                            firebaseUser = mAuth.getCurrentUser();

                            UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                    .setPhotoUri(downloadUri).build();
                            firebaseUser.updateProfile(profileUpdate);
                        }
                    });

                    hideProgressBar();

                    Toast.makeText(UploadProfileImageActivity.this, "Upload Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UploadProfileImageActivity.this, ProfilActivity.class);
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UploadProfileImageActivity.this, "Upload Fail", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            hideProgressBar();
            Toast.makeText(UploadProfileImageActivity.this, "No File Select", Toast.LENGTH_SHORT);
        }
    }

    private void openFileCooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select PDF files"), 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uriImage = data.getData();
//            imageView_profile_dp.setImageURI(uriImage);
            Glide.with(imageView_profile_dp.getContext())
                    .load(uriImage)
                    .fitCenter()
                    .into(imageView_profile_dp);
            binding.btnUpload.setEnabled(true);
        }
    }

    private void showProgressBar() {
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        binding.progressBar.setVisibility(View.GONE);
    }
}