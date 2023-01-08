package com.tugasakhirpab2.rjn.activities;

import static android.os.Build.VERSION_CODES.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.tugasakhirpab2.rjn.Intent_Key;
import com.tugasakhirpab2.rjn.R;
import com.tugasakhirpab2.rjn.adapter.KerjaAdapter;
import com.tugasakhirpab2.rjn.adapter.SkillReqAdapter;
import com.tugasakhirpab2.rjn.databinding.ActivityDetailKerjaBinding;
import com.tugasakhirpab2.rjn.model.DetailKerjaModel;
import com.tugasakhirpab2.rjn.model.KerjaModel;
import com.tugasakhirpab2.rjn.retrofit.APIService;

import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailKerjaActivity extends AppCompatActivity {

    private ActivityDetailKerjaBinding binding;
    private final String TAG = "DetailKerjaActivity";
    private SkillReqAdapter skillReqAdapter;
    private Locale localeID = new Locale("in", "ID");
    private List<String> listSkillReq;
    private NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDetailKerjaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressBar();
                onBackPressed();
                hideProgressBar();
            }
        });
        getDataFromApi();
    }

    private void setupRecyclerViewReq() {
        skillReqAdapter = new SkillReqAdapter(this, listSkillReq);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
        binding.rvSkillReq.setLayoutManager(layoutManager);
        binding.rvSkillReq.setAdapter(skillReqAdapter);
    }

    private void getDataFromApi()
    {
        String idKerja = getIntent().getStringExtra(Intent_Key.EXTRA_ID_KERJA);
        String namaPerusahaan = getIntent().getStringExtra(Intent_Key.EXTRA_NAMA_PERUSAHAAN);
        String job = getIntent().getStringExtra(Intent_Key.EXTRA_JOB);
        String gaji = getIntent().getStringExtra(Intent_Key.EXTRA_GAJI);
        String lokasiPerusahaan = getIntent().getStringExtra(Intent_Key.EXTRA_LOKASI_PERUSAHAAN);
        showProgressBar();
        APIService.apiEndpoint().getDetailKerja(idKerja)
                .enqueue(new Callback<DetailKerjaModel>() {
                    @Override
                    public void onResponse(Call<DetailKerjaModel> call, Response<DetailKerjaModel> response) {
                        hideProgressBar();
                        if(response.isSuccessful())
                        {
                            binding.tvNamaPerusahaan.setText(namaPerusahaan);
                            binding.tvJob.setText(job);
                            binding.tvGaji.setText(formatRupiah.format(Double.parseDouble(gaji)));
                            binding.tvLokasiPerusahaan.setText(lokasiPerusahaan);
                            binding.tvDeskripsi.setText(response.body().getDeskripsi());
                            String skillReq = response.body().getSkillReq();
                            String[] arrSkillReq = skillReq.split(",");
                            listSkillReq = Arrays.asList(arrSkillReq);
                            setupRecyclerViewReq();
                        }
                    }

                    @Override
                    public void onFailure(Call<DetailKerjaModel> call, Throwable t) {
                        Toast.makeText(DetailKerjaActivity.this, "Error, Please check logcat for detail", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, t.toString());
                    }
                });
    }
    private void hideProgressBar(){
        binding.loLoad.setVisibility(View.GONE);
    }
    private void showProgressBar(){
        binding.loLoad.setVisibility(View.VISIBLE);
    }
}