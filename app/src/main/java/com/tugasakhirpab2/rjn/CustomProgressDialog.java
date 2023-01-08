package com.tugasakhirpab2.rjn;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class CustomProgressDialog extends Dialog {

    public CustomProgressDialog(@NonNull Context context)
    {
        super(context);

        WindowManager.LayoutParams params = getWindow().getAttributes();

        params.gravity = Gravity.CENTER_HORIZONTAL;
        getWindow().setAttributes(params);
        setTitle(null);
        setOnCancelListener(null);
        View view = LayoutInflater.from(context).inflate(R.layout.loading_progress_layout, null);
        TextView tv = findViewById(R.id.tvprogress);
        setContentView(view);
    }

}
