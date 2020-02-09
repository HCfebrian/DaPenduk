package com.example.dapenduk.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dapenduk.R;
import com.example.dapenduk.data.model.Penduduk;

import java.util.ArrayList;
import java.util.List;

public class ListPendudukAdapter extends RecyclerView.Adapter<ListPendudukAdapter.ListPendudukHolder> implements Filterable {
    private List<Penduduk> pendudukList = new ArrayList<>();
    private List<Penduduk> pendudukListFull;
    private OnItemClickListener listener;

    @NonNull
    @Override
    public ListPendudukHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.penduduk_list, parent, false);
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

    public void setPendudukList(List<Penduduk> pendudukList) {
        this.pendudukList = pendudukList;
        this.pendudukListFull = new ArrayList<>(this.pendudukList);
        notifyDataSetChanged();
    }

    public Penduduk getpendudukPos(int pos) {
        return pendudukList.get(pos);
    }

    @Override
    public Filter getFilter() {
        return listFilter;
    }

    private Filter listFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Penduduk> filteredPenduduk = new ArrayList<>();
            if(constraint == null || constraint.length() == 0 ){
                filteredPenduduk.addAll(pendudukListFull);
            }else{
                String filterPattern =  constraint.toString().toLowerCase().trim();
                for(Penduduk penduduk : pendudukListFull){
                    if(penduduk.getName().toLowerCase().contains(filterPattern)){
                        filteredPenduduk.add(penduduk);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredPenduduk;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            pendudukList.clear();
            pendudukListFull.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    class ListPendudukHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvDescription;
        private TextView tvPriority;


        public ListPendudukHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDescription = itemView.findViewById(R.id.tv_description);
            tvPriority = itemView.findViewById(R.id.tv_property);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (listener != null && pos != RecyclerView.NO_POSITION) {
                        listener.onItemClick(pendudukList.get(pos));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Penduduk penduduk);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
