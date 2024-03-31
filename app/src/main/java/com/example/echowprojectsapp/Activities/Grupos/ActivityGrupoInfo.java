package com.example.echowprojectsapp.Activities.Grupos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.echowprojectsapp.Adapters.IntegrantesAdapter;
import com.example.echowprojectsapp.Adapters.MusicaAdapter;
import com.example.echowprojectsapp.Adapters.VideoAdapter;
import com.example.echowprojectsapp.Models.informacionGrupoGeneral;
import com.example.echowprojectsapp.Models.integrantesItem;
import com.example.echowprojectsapp.Models.musicItem;
import com.example.echowprojectsapp.Models.videoItem;
import com.example.echowprojectsapp.NetworkTasks.GruposNetworkTasks.InfomacionGeneralGrupoAsyncTask;
import com.example.echowprojectsapp.NetworkTasks.GruposNetworkTasks.obtenerAudiosGrupoAsyncTask;
import com.example.echowprojectsapp.NetworkTasks.GruposNetworkTasks.obtenerIntegrantesGrupoAsyncTask;
import com.example.echowprojectsapp.NetworkTasks.GruposNetworkTasks.obtenerVideosGrupoAsyncTask;
import com.example.echowprojectsapp.R;
import com.example.echowprojectsapp.Utilidades.Navegacion.NavigationClickListener;
import com.example.echowprojectsapp.Utilidades.Navegacion.NavigationGruposInfoClickListener;
import com.example.echowprojectsapp.Utilidades.Token.JwtDecoder;
import com.example.echowprojectsapp.Utilidades.Token.token;

import java.util.ArrayList;
import java.util.List;

public class ActivityGrupoInfo extends AppCompatActivity implements InfomacionGeneralGrupoAsyncTask.DataFetchListener {

    DrawerLayout drawerLayout;
    ImageButton openMenuButton;
    TextView nombreGrupo, textviewNumeroIntegrantes, textviewNumeroAudio, textviewNumeroVideo;
    ImageView fotoGrupo;
    Button botonEntrarChat;
    RecyclerView recyclerViewIntegrantes, recyclerViewMusica, recyclerViewVideos;
    private int idgrupo;
    ProgressDialog progressDialog;
    private com.example.echowprojectsapp.Utilidades.Token.token token = new token(this);
    private NavigationGruposInfoClickListener navigationClickListener;
    private int idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupo_info);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando...");
        progressDialog.setCancelable(false);

        idgrupo = getIntent().getIntExtra("idgrupo", 0);
        idUsuario = Integer.parseInt(JwtDecoder.decodeJwt(token.recuperarTokenFromKeystore()));

        // Inicializa listener para elementos
        navigationClickListener = new NavigationGruposInfoClickListener(this, this, idgrupo);

        // Declaración de variables
        recyclerViewIntegrantes = (RecyclerView) findViewById(R.id.recyclerviewIntegrantes);
        recyclerViewMusica = (RecyclerView) findViewById(R.id.recyclerviewMusica);
        recyclerViewVideos = (RecyclerView) findViewById(R.id.recyclerviewVideo);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layoutGrupoInfo);
        openMenuButton = (ImageButton) findViewById(R.id.btn_GrupoInfoMenu);
        fotoGrupo = (ImageView) findViewById(R.id.imageviewGrupoInfoFoto);
        nombreGrupo = (TextView) findViewById(R.id.textview_GrupoInfoTitulo);
        textviewNumeroIntegrantes = (TextView) findViewById(R.id.textviewIntegrantesTitle);
        textviewNumeroAudio = (TextView) findViewById(R.id.textviewMusicaTitle);
        textviewNumeroVideo = (TextView) findViewById(R.id.textviewVideoTitle);
        botonEntrarChat = findViewById(R.id.buttonIngresarChat);

        // Creación de una lista de elementos de integrantesItem
        List<integrantesItem> integrantesList = new ArrayList<>();

        // Creación de una lista de elementos de musicItem
        List<musicItem> musicList = new ArrayList<>();

        // Creación de una lista de elementos de videoItem
        List<videoItem> videoList = new ArrayList<>();

        // Crea y vincula el adaptador - integrantes
        IntegrantesAdapter integrantesAdapter = new IntegrantesAdapter(this, integrantesList);
        recyclerViewIntegrantes.setAdapter(integrantesAdapter);
        //musica
        MusicaAdapter musicaAdapter = new MusicaAdapter(this, musicList);
        recyclerViewMusica.setAdapter(musicaAdapter);
        //video
        VideoAdapter videoAdapter = new VideoAdapter(this, videoList);
        recyclerViewVideos.setAdapter(videoAdapter);

        //Configuracion del administrador de diseño - integrantes
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewIntegrantes.setLayoutManager(layoutManager);
        //musica
        LinearLayoutManager layoutManagerMusica = new LinearLayoutManager(this);
        layoutManagerMusica.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewMusica.setLayoutManager(layoutManagerMusica);
        //video
        LinearLayoutManager layoutManagerVideo = new LinearLayoutManager(this);
        layoutManagerVideo.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewVideos.setLayoutManager(layoutManagerVideo);

        // Fetch data from the server
        String url = "https://phpclusters-156700-0.cloudclusters.net/obtenerInfoGrupo.php";
        progressDialog.show();
        new InfomacionGeneralGrupoAsyncTask(this).execute(url, String.valueOf(idgrupo));
        new obtenerIntegrantesGrupoAsyncTask(ActivityGrupoInfo.this, integrantesAdapter, progressDialog)
                .execute(String.valueOf(idgrupo), String.valueOf(0));
        new obtenerAudiosGrupoAsyncTask(ActivityGrupoInfo.this, musicaAdapter, progressDialog)
                .execute(String.valueOf(idgrupo), String.valueOf(0));
        new obtenerVideosGrupoAsyncTask(ActivityGrupoInfo.this, videoAdapter, progressDialog)
                .execute(String.valueOf(idgrupo), String.valueOf(1));


        // Listener para abrir el menú lateral
        openMenuButton.setOnClickListener(v -> {
            drawerLayout.openDrawer(findViewById(R.id.side_menu));
        });

        //Listener para menu de navegacion
        View.OnClickListener buttonClickNav = new NavigationClickListener(this,this);

    }

    @Override
    public void onDataFetched(List<informacionGrupoGeneral> dataList) {
        if (dataList != null && !dataList.isEmpty()) {
            informacionGrupoGeneral groupInfo = dataList.get(0);

            nombreGrupo.setText(groupInfo.getNombre());
            fotoGrupo.setImageBitmap(groupInfo.getFoto());
            textviewNumeroIntegrantes.setText("Integrantes: " + groupInfo.getNumeroMiembros());
            textviewNumeroAudio.setText("Audio: " + groupInfo.getNumeroMusica());
            textviewNumeroVideo.setText("Videos: " + groupInfo.getNumeroVideos());

            botonEntrarChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ActivityChat.class);
                    intent.putExtra("idgrupo", groupInfo.getIdgrupo());
                    intent.putExtra("nombregrupo", groupInfo.getNombre());
                    intent.putExtra("image", groupInfo.getUrl());
                    v.getContext().startActivity(intent);
                }
            });
        } else {
            Log.e("Error", "No data fetched from the server");
        }
    }
}