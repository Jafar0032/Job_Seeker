package com.tugasakhirpab2.rjn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tugasakhirpab2.rjn.R;

import java.util.ArrayList;
import java.util.List;

public class SkillReqAdapter extends RecyclerView.Adapter<SkillReqAdapter.ViewHolder> {
    Context c;
    private List<String> mList;

    public SkillReqAdapter(Context c, List<String> mList) {
        this.c = c;
        this.mList = mList;
    }

    @NonNull
    @Override
    public SkillReqAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SkillReqAdapter.ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_req, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SkillReqAdapter.ViewHolder holder, int position) {
        holder.tvSkill.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvSkill;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvSkill = itemView.findViewById(R.id.tv_req);
        }
    }
}
