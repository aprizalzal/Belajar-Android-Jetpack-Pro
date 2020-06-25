package com.application.academy.ui.reader.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.academy.R;
import com.application.academy.data.ModuleEntity;

import java.util.ArrayList;
import java.util.List;

public class ModuleListAdapter extends RecyclerView.Adapter<ModuleListAdapter.ModuleViewHolder> {
    private final MyAdapterClickListener listener;
    private ArrayList<ModuleEntity> listModules = new ArrayList<>();

    ModuleListAdapter(MyAdapterClickListener listener){
        this.listener = listener;
    }

    public void setModules(List<ModuleEntity> modules) {
        if (listModules == null)return;
        listModules.clear();
        listModules.addAll(modules);
    }

    @NonNull
    @Override
    public ModuleListAdapter.ModuleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ModuleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.items_module_list_custom, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ModuleListAdapter.ModuleViewHolder viewHolder, int position) {
        ModuleEntity module = listModules.get(position);
        viewHolder.bind(module);
        viewHolder.itemView.setOnClickListener( v -> listener.onItemClicked(viewHolder.getAdapterPosition(),listModules.get(viewHolder.getAdapterPosition()).getModuleId()));

    }

    @Override
    public int getItemCount() {
        return listModules.size();
    }

    public static class ModuleViewHolder extends RecyclerView.ViewHolder {
        final TextView textTitle;
        public ModuleViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.text_module_title);
        }

        public void bind(ModuleEntity module) {
            textTitle.setText(module.getTitle());

        }
    }
}
