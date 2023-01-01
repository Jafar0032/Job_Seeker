package com.tugasakhirpab2.rjn.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tugasakhirpab2.rjn.R;
import com.tugasakhirpab2.rjn.model.KerjaModel;

import java.util.ArrayList;
import java.util.List;

public class KerjaAdapter extends RecyclerView.Adapter<KerjaAdapter.ViewHolder> {

    private List<KerjaModel.Result> results = new ArrayList<>();
    private OnAdapterListener listener;

    public KerjaAdapter(List<KerjaModel.Result> results, OnAdapterListener listener) {
        this.results = results;
        this.listener = listener;
    }

    @NonNull
    @Override
    public KerjaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kerja, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull KerjaAdapter.ViewHolder holder, int position) {
        KerjaModel.Result result = results.get(position);

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public interface OnAdapterListener {
        void onClick(KerjaModel.Result result);
    }
}
