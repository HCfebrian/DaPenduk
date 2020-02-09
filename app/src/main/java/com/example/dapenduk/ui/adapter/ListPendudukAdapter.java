package com.example.dapenduk.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dapenduk.R;
import com.example.dapenduk.data.model.Penduduk;

import java.util.ArrayList;
import java.util.List;

public class ListPendudukAdapter  extends RecyclerView.Adapter<ListPendudukAdapter.ListPendudukHolder> {
    private List<Penduduk> pendudukList = new ArrayList<>();

    @NonNull
    @Override
    public ListPendudukHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.penduduk_list,parent,false);
        return new ListPendudukHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListPendudukHolder holder, int position) {
        Penduduk currentPenduduk = pendudukList.get(position);
        holder.tvTitle.setText(currentPenduduk.getName());
        holder.tvDescription.setText(currentPenduduk.getAddress());
        holder.tvPriority.setText(currentPenduduk.getProfession());
    }

    @Override
    public int getItemCount() {
        return pendudukList.size();
    }

    public void setPendudukList(List<Penduduk> pendudukList){
        this.pendudukList = pendudukList;
        notifyDataSetChanged();
    }

    public Penduduk getpendudukPos(int pos){
        return pendudukList.get(pos);
    }
    class ListPendudukHolder extends RecyclerView.ViewHolder{
        private TextView tvTitle;
        private TextView tvDescription;
        private TextView tvPriority;


        public ListPendudukHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDescription = itemView.findViewById(R.id.tv_description);
            tvPriority = itemView.findViewById(R.id.tv_property);
        }
    }
}
