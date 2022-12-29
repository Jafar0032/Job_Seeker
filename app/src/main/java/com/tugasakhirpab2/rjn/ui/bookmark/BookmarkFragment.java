package com.tugasakhirpab2.rjn.ui.bookmark;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tugasakhirpab2.rjn.R;
import com.tugasakhirpab2.rjn.databinding.FragmentBookmarkBinding;
import com.tugasakhirpab2.rjn.databinding.FragmentHomeBinding;
import com.tugasakhirpab2.rjn.ui.home.HomeViewModel;

public class BookmarkFragment extends Fragment {

    private FragmentBookmarkBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BookmarkViewModel bookmarkViewModel =
                new ViewModelProvider(this).get(BookmarkViewModel.class);

        binding = FragmentBookmarkBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textBookmark;
        bookmarkViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}