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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tugasakhirpab2.rjn.databinding.ActivityCvactivityBinding;
import com.tugasakhirpab2.rjn.model.FileInModel;

import java.io.File;

public class CVActivity extends AppCompatActivity {

    private ActivityCvactivityBinding binding;

    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCvactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("pdfs");

        binding.btnUploadFile.setEnabled(false);

        binding.etTempatUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPDF();
            }
        });

        binding.btnLiatFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retrievePDFs();
            }
        });
    }

    private void selectPDF() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select PDF files"), 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 101 && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            Uri uri = data.getData();

            // Extract name of the pdf file
            String uriString = uri.toString();
            File myFile = new File(uriString);
            String path = myFile.getAbsolutePath();
            String displayName = null;

            if(uriString.startsWith("content://"))
            {
                Cursor cursor = null;
                try {
                    cursor = this.getContentResolver().query(uri, null, null, null, null);
                    if(cursor != null && cursor.moveToFirst())
                    {
                        displayName = cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME));
                    }
                } finally {
                    cursor.close();
                }
            }
            else if(uriString.startsWith("file//"))
            {
                displayName = myFile.getName();
            }

            binding.btnUploadFile.setEnabled(true);
            binding.etTempatUpload.setText(displayName);

            binding.btnUploadFile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    uploadPDF(data.getData());
                }
            });
        }
    }

    private void uploadPDF(Uri data) {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("File Uploading...");
        pd.show();

        final StorageReference reference = storageReference.child("uploads/"+ System.currentTimeMillis() + ".pdf");
        // store in upload folder of the Firebase Storage
        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while(!uriTask.isComplete());
                        Uri uri = uriTask.getResult();

                        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        FileInModel fileInModel = new FileInModel(binding.etTempatUpload.getText().toString(), uri.toString()); // Get the views from the model class
                        databaseReference.child(userId).setValue(fileInModel); // Push the value into realtime database
                        Toast.makeText(CVActivity.this, "File Uploaded Successfully!", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        float percent = (100 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                        pd.setMessage("Uploaded : " + (int) percent + "%");
                    }
                });

    }

    // Try to upload pdf file

    public void retrievePDFs()
    {

    }
}