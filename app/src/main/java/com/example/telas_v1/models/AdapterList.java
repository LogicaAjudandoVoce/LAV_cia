package com.example.telas_v1.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telas_v1.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterList extends RecyclerView.Adapter<AdapterList.ViewHolder> {

    private List<String> urls;
    private LayoutInflater inflater;

    public AdapterList(Context context, List<String> urls){
        this.urls = urls;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_list_fotos, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load(urls.get(position)).into(holder.imgList);
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgList;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgList = itemView.findViewById(R.id.imgListSimple);
        }
    }
}
