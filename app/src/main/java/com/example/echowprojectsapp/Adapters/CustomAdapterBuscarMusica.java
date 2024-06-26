package com.example.echowprojectsapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.echowprojectsapp.Models.vistaMusicaVideo;
import com.example.echowprojectsapp.R;

import java.util.List;

public class CustomAdapterBuscarMusica extends RecyclerView.Adapter<CustomAdapterBuscarMusica.CustomViewHolder>  {

    private List<vistaMusicaVideo> dataList;
    private Context context;


    public CustomAdapterBuscarMusica(Context context, List<vistaMusicaVideo> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_list_item_musica, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        vistaMusicaVideo data = dataList.get(position);

        // Vincula los datos a las vistas en tu diseño de elemento de lista personalizado
        holder.nombreCancion.setText(data.getText1());
        holder.creadoPor.setText(data.getText2());
        holder.image.setImageResource(data.getImageResource());

        // Obtiene el ImageView del diseño
        ImageView imgUnirse = holder.itemView.findViewById(R.id.imageviewListItemImage);
    }



    public int getItemCount(){return dataList.size(); }
    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView nombreCancion; // TextView para mostrar el nombre del grupo
        TextView creadoPor; // TextView para mostrar el creador del grupo
        ImageView image; // ImageView para mostrar una imagen asociada al grupo

        public CustomViewHolder(View itemView) {
            super(itemView);
            nombreCancion = itemView.findViewById(R.id.txtListItemNombreGrupo); // Asocia la vista de nombre del grupo
            creadoPor = itemView.findViewById(R.id.txtListItemCreado); // Asocia la vista del creador del grupo
            image = itemView.findViewById(R.id.imageviewListItemImage); // Asocia la vista de la imagen asociada al grupo
        }
    }
}
