package com.tugasakhirpab2.rjn.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.tugasakhirpab2.rjn.Intent_Key;
import com.tugasakhirpab2.rjn.R;
import com.tugasakhirpab2.rjn.adapter.KerjaAdapter;
import com.tugasakhirpab2.rjn.databinding.ActivityArsitekturBinding;
import com.tugasakhirpab2.rjn.databinding.ActivityKomputerBinding;
import com.tugasakhirpab2.rjn.model.KerjaModel;
import com.tugasakhirpab2.rjn.retrofit.APIService;
import com.tugasakhirpab2.rjn.ui.search.SearchViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArsitekturActivity extends AppCompatActivity {

    private ActivityArsitekturBinding binding;

    private KerjaAdapter kerjaAdapter;
    private List<KerjaModel.Result> results = new ArrayList<>();
    private String searchContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityArsitekturBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        backPressed();
        setupRecyclerViewKerja();
        getDataFromAPI();

        ImageView icon = (ImageView) binding.svSearch.findViewById(com.airbnb.lottie.R.id.search_mag_icon);
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchContent = binding.svSearch.getQuery().toString();
                getDataFromAPI();
                hideKeyboard(ArsitekturActivity.this);
            }
        });
    }

    private void setupRecyclerViewKerja() {
        kerjaAdapter = new KerjaAdapter(results, new KerjaAdapter.OnAdapterListener() {
            @Override
            public void onClick(KerjaModel.Result result) {
                Intent intent = new Intent(ArsitekturActivity.this, DetailKerjaActivity.class);
                intent.putExtra(Intent_Key.EXTRA_ID_KERJA, result.getIdKerja());
                intent.putExtra(Intent_Key.EXTRA_NAMA_PERUSAHAAN, result.getNamaPerusahaan());
                intent.putExtra(Intent_Key.EXTRA_JOB, result.getJobDesk());
                intent.putExtra(Intent_Key.EXTRA_GAJI, result.getGaji());
                intent.putExtra(Intent_Key.EXTRA_LOKASI_PERUSAHAAN, result.getAlamat());
                startActivity(intent);
            }
        });
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1, RecyclerView.VERTICAL, false);
        binding.rvRekomendasiPekerjaan.setLayoutManager(layoutManager);
        binding.rvRekomendasiPekerjaan.setAdapter(kerjaAdapter);
    }

    private void getDataFromAPI()
    {
        APIService.apiEndpoint().getKategoriArsitektur(searchContent)
                .enqueue(new Callback<KerjaModel>() {
                    @Override
                    public void onResponse(Call<KerjaModel> call, Response<KerjaModel> response) {
                        if(response.isSuccessful())
                        {
                            List<KerjaModel.Result> results = response.body().getResult();
                            kerjaAdapter.setData(results);
                        }
                    }

                    @Override
                    public void onFailure(Call<KerjaModel> call, Throwable t) {

                    }
                });
    }

    private void hideKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    private void backPressed()
    {
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}