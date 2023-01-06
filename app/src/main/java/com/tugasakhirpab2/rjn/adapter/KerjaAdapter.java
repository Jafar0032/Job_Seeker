package com.tugasakhirpab2.rjn.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tugasakhirpab2.rjn.R;
import com.tugasakhirpab2.rjn.model.KerjaModel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class KerjaAdapter extends RecyclerView.Adapter<KerjaAdapter.ViewHolder> {

    private List<KerjaModel.Result> results = new ArrayList<>();
    private OnAdapterListener listener;
    private Locale localeID = new Locale("in", "ID");
    private NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    public KerjaAdapter(List<KerjaModel.Result> results, OnAdapterListener listener) {
        this.results = results;
        this.listener = listener;
    }

    public void setData(List<KerjaModel.Result> data) {
        results.clear();
        results.addAll(data);
        notifyDataSetChanged();
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
        holder.tvFulltime.setText(fullTimeChecker(result.getFulltime()));
        holder.tvJob.setText(result.getJobDesk());
        holder.tvNamaPerusahaan.setText(result.getNamaPerusahaan());
        holder.tvGaji.setText(formatRupiah.format(Double.parseDouble(result.getGaji())));
        holder.tvLokasiPerusahaan.setText(result.getAlamat());
        Glide.with(holder.itemView.getContext())
                .load(result.getLogoPerusahaan())
                .placeholder(R.drawable.img_placeholder)
                .fitCenter()
                .into(holder.ivLogoPerusahaan);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(result);
            }
        });
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFulltime, tvJob, tvNamaPerusahaan, tvGaji, tvLokasiPerusahaan;
        ImageView ivLogoPerusahaan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvFulltime = itemView.findViewById(R.id.tv_fulltime);
            tvJob = itemView.findViewById(R.id.tv_job);
            tvNamaPerusahaan = itemView.findViewById(R.id.tv_nama_perusahaan);
            tvGaji = itemView.findViewById(R.id.tv_gaji);
            tvLokasiPerusahaan = itemView.findViewById(R.id.tv_lokasi_perusahaan);

            ivLogoPerusahaan = itemView.findViewById(R.id.iv_logo_perusahaan);
        }
    }

    public interface OnAdapterListener {
        void onClick(KerjaModel.Result result);
    }

    private String fullTimeChecker(String f)
    {
        if(f.equals("Y"))
            return "# Full Time";
        else if(f.equals("N"))
            return "# Part Time";
        return "null";
    }

}
