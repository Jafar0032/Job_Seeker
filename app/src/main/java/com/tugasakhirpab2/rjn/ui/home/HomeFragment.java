package com.tugasakhirpab2.rjn.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tugasakhirpab2.rjn.R;
import com.tugasakhirpab2.rjn.adapter.KerjaAdapter;
import com.tugasakhirpab2.rjn.adapter.SliderPagerAdapter;
import com.tugasakhirpab2.rjn.databinding.FragmentHomeBinding;
import com.tugasakhirpab2.rjn.decoration.BannerSlider;
import com.tugasakhirpab2.rjn.decoration.SliderIndicator;
import com.tugasakhirpab2.rjn.fragmentext.FragmentSlider;
import com.tugasakhirpab2.rjn.model.KerjaModel;
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