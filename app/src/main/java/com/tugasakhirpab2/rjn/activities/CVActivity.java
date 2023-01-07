package com.tugasakhirpab2.rjn.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.provider.ContactsContract;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tugasakhirpab2.rjn.databinding.ActivityCvactivityBinding;
import com.tugasakhirpab2.rjn.model.FileInModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CVActivity extends AppCompatActivity {

    private static final String TAG = CVActivity.class.getSimpleName();

    private ActivityCvactivityBinding binding;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCvactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("pdfs");

        List<String> list = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    String data = dataSnapshot.child("userId").getValue().toString();
                    Log.d(TAG, data);
                    list.add(data);
                }

                if(list.contains(userId))
                {
                    binding.llAdaCv.setVisibility(View.VISIBLE);
                    binding.llNothingCv.setVisibility(View.INVISIBLE);
                    String namaFile = snapshot.child(userId).child("fileName").getValue().toString();
                    binding.tvNamaFile.setText(namaFile);
                }
                else
                {
                    binding.llAdaCv.setVisibility(View.INVISIBLE);
                    binding.llNothingCv.setVisibility(View.VISIBLE);
                    binding.btnBrowseFile.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            selectPDF();
                        }
                    });
                }
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
            uploadPDF(data.getData(), displayName);
        }
    }

    private void uploadPDF(Uri data, String fileName) {

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

                        FileInModel fileInModel = new FileInModel(userId, fileName, uri.toString()); // Get the views from the model class
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

    public void retrievePDFs()
    {

    }
}