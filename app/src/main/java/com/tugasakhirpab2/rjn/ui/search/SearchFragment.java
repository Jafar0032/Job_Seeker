package com.tugasakhirpab2.rjn.ui.search;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tugasakhirpab2.rjn.R;
import com.tugasakhirpab2.rjn.adapter.KerjaAdapter;
import com.tugasakhirpab2.rjn.databinding.FragmentHomeBinding;
import com.tugasakhirpab2.rjn.databinding.FragmentSearchBinding;
import com.tugasakhirpab2.rjn.model.KerjaModel;
import com.tugasakhirpab2.rjn.model.SearchKerjaModel;
import com.tugasakhirpab2.rjn.model.User;
import com.tugasakhirpab2.rjn.retrofit.APIService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;

    private KerjaAdapter kerjaAdapter;
    private List<KerjaModel.Result> results = new ArrayList<>();
    private String searchContent;
//    private SearchView searchView;
//    private MenuItem menuItem;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SearchViewModel searchViewModel =
                new ViewModelProvider(this).get(SearchViewModel.class);

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        setHasOptionsMenu(true);
//        super.onCreate(savedInstanceState);
//    }

//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        inflater.inflate(R.menu.search_menu, menu);
//        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
//        searchView.setIconified(true);
//
//        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                mySearch(query);
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String query) {
//                mySearch(query);
//                return true;
//            }
//        });
//
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//    private void mySearch(String query) {
//
//    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toast.makeText(getActivity(), "Hai" + searchContent, Toast.LENGTH_SHORT).show();
        getDataFromAPI();
        setupRecyclerViewKerja();

        ImageView icon = (ImageView) binding.svSearch.findViewById(com.airbnb.lottie.R.id.search_mag_icon);
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchContent = binding.svSearch.getQuery().toString();
                getDataFromAPI();
                hideKeyboard(getActivity());
            }
        });
    }



    private void setupRecyclerViewKerja() {
        kerjaAdapter = new KerjaAdapter(results, new KerjaAdapter.OnAdapterListener() {
            @Override
            public void onClick(KerjaModel.Result result) {

            }
        });
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 1, RecyclerView.VERTICAL, false);
        binding.rvSearch.setLayoutManager(layoutManager);
        binding.rvSearch.setAdapter(kerjaAdapter);
    }

    private void getDataFromAPI()
    {
        APIService.apiEndpoint().getSearchKerja(searchContent)
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}