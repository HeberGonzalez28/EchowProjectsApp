package com.example.echowprojectsapp.Activities.Multimedia;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.echowprojectsapp.Adapters.AudioAdapter;
import com.example.echowprojectsapp.Models.audioItem;
import com.example.echowprojectsapp.NetworkTasks.Multimedia.obtenerAudiosMultimediaAsyncTask;
import com.example.echowprojectsapp.R;
import com.example.echowprojectsapp.Utilidades.Token.JwtDecoder;
import com.example.echowprojectsapp.Utilidades.Token.token;

import java.util.ArrayList;
import java.util.List;

public class ActivityVerCancionesFavoritas extends AppCompatActivity{
    ImageButton botonAtrass;
    RecyclerView recyclerviewvertodoCancionesFavoritas;
    ProgressDialog progressDialog;

    private com.example.echowprojectsapp.Utilidades.Token.token token = new token(this);
    private int idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_canciones_favoritas);
        botonAtrass = (ImageButton) findViewById(R.id.btn_vertodoscancionesFavoritasListAtras);
        recyclerviewvertodoCancionesFavoritas = (RecyclerView) findViewById(R.id.recyclerview_vertodaCancionFavoritas);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando...");
        progressDialog.setCancelable(false);



        //progressDialog.show();
        idUsuario = Integer.parseInt(JwtDecoder.decodeJwt(token.recuperarTokenFromKeystore()));

        // Creación de una lista de elementos de integrantesItem
        List<audioItem> musicList = new ArrayList<>();

        // Crea y vincula el adaptador - integrantes
        AudioAdapter musicaAdapter = new AudioAdapter(this, musicList);
        recyclerviewvertodoCancionesFavoritas.setAdapter(musicaAdapter);

        //Configuracion del administrador de diseño - integrantes
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerviewvertodoCancionesFavoritas.setLayoutManager(layoutManager);

        new obtenerAudiosMultimediaAsyncTask(ActivityVerCancionesFavoritas.this, musicaAdapter, progressDialog)
                .execute(String.valueOf(idUsuario));
        progressDialog.show();

        botonAtrass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}