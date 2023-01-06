package com.tugasakhirpab2.rjn.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tugasakhirpab2.rjn.Intent_Key;
import com.tugasakhirpab2.rjn.R;
import com.tugasakhirpab2.rjn.activities.AkuntansiActivity;
import com.tugasakhirpab2.rjn.activities.ArsitekturActivity;
import com.tugasakhirpab2.rjn.activities.DesignActivity;
import com.tugasakhirpab2.rjn.activities.DetailKerjaActivity;
import com.tugasakhirpab2.rjn.activities.EngineeringActivity;
import com.tugasakhirpab2.rjn.activities.HukumActivity;
import com.tugasakhirpab2.rjn.activities.KomputerActivity;
import com.tugasakhirpab2.rjn.activities.MarketingActivity;
import com.tugasakhirpab2.rjn.activities.PariwisataActivity;
import com.tugasakhirpab2.rjn.adapter.KerjaAdapter;
import com.tugasakhirpab2.rjn.adapter.SliderPagerAdapter;
import com.tugasakhirpab2.rjn.databinding.FragmentHomeBinding;
import com.tugasakhirpab2.rjn.decoration.BannerSlider;
import com.tugasakhirpab2.rjn.decoration.SliderIndicator;
import com.tugasakhirpab2.rjn.fragmentext.FragmentSlider;
import com.tugasakhirpab2.rjn.model.KerjaModel;
import com.tugasakhirpab2.rjn.model.User;
import com.tugasakhirpab2.rjn.retrofit.APIService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private SliderPagerAdapter mAdapter;
    private SliderIndicator mIndicator;

    private KerjaAdapter kerjaAdapter;
    private List<KerjaModel.Result> results = new ArrayList<>();

    private FirebaseAuth mAuth;
    private DatabaseReference mRoot, mRef;

    private String mFullname;
    private String userId;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        userId = mAuth.getCurrentUser().getUid();
        mRoot = FirebaseDatabase.getInstance().getReference();
        mRef = mRoot.child("users").child(userId);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                mFullname = user.getFullName();
                binding.tvUsernameHalo.setText(mFullname + " !");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                mFullname = "Anonymous";
            }
        });

        binding.btnKategoriKomputer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), KomputerActivity.class);
                startActivity(intent);
            }
        });

        binding.btnKategoriAkuntansi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AkuntansiActivity.class);
                startActivity(intent);
            }
        });

        binding.btnKategoriMarketing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MarketingActivity.class);
                startActivity(intent);
            }
        });

        binding.btnKategoriDesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DesignActivity.class);
                startActivity(intent);
            }
        });

        binding.btnKategoriArsitektur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ArsitekturActivity.class);
                startActivity(intent);
            }
        });

        binding.btnKategoriPariwisata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PariwisataActivity.class);
                startActivity(intent);
            }
        });

        binding.btnKategoriHukum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HukumActivity.class);
                startActivity(intent);
            }
        });

        binding.btnKategoriEngineering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EngineeringActivity.class);
                startActivity(intent);
            }
        });

        setupSlider();
        getDataFromAPI();
        setupRecyclerViewKerja();

    }

    private void setupSlider() {
        binding.llBanner.sliderView.setDurationScroll(800);
        List<Fragment> fragments = new ArrayList<>();

        //link image
        fragments.add(FragmentSlider.newInstance("https://ecs7.tokopedia.net/img/banner/2020/4/19/85531617/85531617_17b56894-2608-4509-a4f4-ad86aa5d3b62.jpg"));
        fragments.add(FragmentSlider.newInstance("https://ecs7.tokopedia.net/img/banner/2020/4/19/85531617/85531617_7da28e4c-a14f-4c10-8fec-845578f7d748.jpg"));
        fragments.add(FragmentSlider.newInstance("https://ecs7.tokopedia.net/img/banner/2020/4/18/85531617/85531617_87a351f9-b040-4d90-99f4-3f3e846cd7ef.jpg"));
        fragments.add(FragmentSlider.newInstance("https://ecs7.tokopedia.net/img/banner/2020/4/20/85531617/85531617_03e76141-3faf-45b3-8bcd-fc0962836db3.jpg"));

        mAdapter = new SliderPagerAdapter(getParentFragmentManager(), fragments);
        binding.llBanner.sliderView.setAdapter(mAdapter);
        mIndicator = new SliderIndicator(getActivity(), binding.llBanner.pagesContainer, binding.llBanner.sliderView, R.drawable.indicator_circle);
        mIndicator.setPageCount(fragments.size());
        mIndicator.show();
    }

    private void setupRecyclerViewKerja() {
        kerjaAdapter = new KerjaAdapter(results, new KerjaAdapter.OnAdapterListener() {
            @Override
            public void onClick(KerjaModel.Result result) {
                Intent intent = new Intent(getActivity(), DetailKerjaActivity.class);
                intent.putExtra(Intent_Key.EXTRA_ID_KERJA, result.getIdKerja());
                intent.putExtra(Intent_Key.EXTRA_NAMA_PERUSAHAAN, result.getNamaPerusahaan());
                intent.putExtra(Intent_Key.EXTRA_JOB, result.getJobDesk());
                intent.putExtra(Intent_Key.EXTRA_GAJI, result.getGaji());
                intent.putExtra(Intent_Key.EXTRA_LOKASI_PERUSAHAAN, result.getAlamat());
                startActivity(intent);
            }
        });
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 1, RecyclerView.HORIZONTAL, false);
        binding.rvRekomendasiPekerjaan.setLayoutManager(layoutManager);
        binding.rvRekomendasiPekerjaan.setAdapter(kerjaAdapter);
    }

    private void getDataFromAPI()
    {
        APIService.apiEndpoint().getKerja()
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}