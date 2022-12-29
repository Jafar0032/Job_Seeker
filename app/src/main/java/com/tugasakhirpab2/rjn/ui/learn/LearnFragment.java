package com.tugasakhirpab2.rjn.ui.learn;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tugasakhirpab2.rjn.R;
import com.tugasakhirpab2.rjn.databinding.FragmentHomeBinding;
import com.tugasakhirpab2.rjn.databinding.FragmentLearnBinding;
import com.tugasakhirpab2.rjn.ui.home.HomeViewModel;

public class LearnFragment extends Fragment {

    private FragmentLearnBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LearnViewModel learnViewModel =
                new ViewModelProvider(this).get(LearnViewModel.class);

        binding = FragmentLearnBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textLearn;
        learnViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}