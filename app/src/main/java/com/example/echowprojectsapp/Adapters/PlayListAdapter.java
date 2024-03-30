package com.example.echowprojectsapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectogrupo1musicstore.Models.PlayListItem;
import com.example.proyectogrupo1musicstore.R;

import java.util.List;

public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.PlayListViewHolder> {

    private List<PlayListItem> itemList;
    private Context context;

    public PlayListAdapter(Context context, List<PlayListItem> itemList) {
        this.context = context;
        this.itemList = itemList;
    }



    @NonNull
    @Override
    public PlayListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carousek_item_playlist, parent, false);
        return new PlayListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayListViewHolder holder, int position) {
        PlayListItem item = itemList.get(position);
        holder.playlitsImage.setImageBitmap(item.getImageResId());
        holder.playlistName.setText(item.getItemName());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class PlayListViewHolder extends RecyclerView.ViewHolder {
        ImageView playlitsImage;
        TextView playlistName;

        public PlayListViewHolder(@NonNull View itemView) {
            super(itemView);
            playlitsImage = itemView.findViewById(R.id.imageviewCarouselItemPlayList);
            playlistName = itemView.findViewById(R.id.textviewCarouselItemPlayList);
        }
    }

    public void setDataList(List<PlayListItem> newDataList) {
        itemList = newDataList;
        notifyDataSetChanged();
    }
}
