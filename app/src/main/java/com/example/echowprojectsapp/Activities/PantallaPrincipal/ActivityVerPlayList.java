package com.example.echowprojectsapp.Activities.PantallaPrincipal;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.echowprojectsapp.Adapters.CustomAdapterReproductorPlaylist;
import com.example.echowprojectsapp.Models.vistadeplaylist;
import com.example.echowprojectsapp.NetworkTaksMulti.FetchDataAsyncModiPlayList;
import com.example.echowprojectsapp.R;
import com.example.echowprojectsapp.Utilidades.Token.JwtDecoder;
import com.example.echowprojectsapp.Utilidades.Token.token;

import java.util.List;

public class ActivityVerPlayList extends AppCompatActivity implements FetchDataAsyncModiPlayList.DataFetchListener {
    ImageButton botonAtrass;
    DrawerLayout drawerLayout;
    RecyclerView recyclerviewvertodoPlayList;
    ProgressDialog progressDialog;
    private int idplaylist;

    private com.example.echowprojectsapp.Utilidades.Token.token token = new token(this);

    private int idUsuario;

    private final String tipo = "1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_play_list);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando...");
        progressDialog.setCancelable(false);

        //progressDialog.show();
        idUsuario = Integer.parseInt(JwtDecoder.decodeJwt(token.recuperarTokenFromKeystore()));

        recyclerviewvertodoPlayList  = (RecyclerView) findViewById(R.id.recyclerview_vertodoPlayList);
        botonAtrass = (ImageButton) findViewById(R.id.btn_vertodosPlayListAtras);
       // drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layoutsVerPlaylistAgregar);
        recyclerviewvertodoPlayList = (RecyclerView) findViewById(R.id.recyclerview_vertodoPlayList);




        String url = "https://phpclusters-156700-0.cloudclusters.net/modiPlayList.php";
        //Remplazar por el idusuario - motivos de pruebas
        progressDialog.show();
        new FetchDataAsyncModiPlayList(this, progressDialog).execute(url, String.valueOf(idUsuario));

        // Configuración del administrador de diseño y adaptador para el RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this); // Use an appropriate layout manager
        recyclerviewvertodoPlayList.setLayoutManager(layoutManager);

        botonAtrass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    public void onDataFetched(List<vistadeplaylist> dataList) {
        // Muestra el Recycle view con la nueva informacion
        progressDialog.dismiss(); // Esconde el spinner de carga
        CustomAdapterReproductorPlaylist adapter = new CustomAdapterReproductorPlaylist(this, dataList, idUsuario, recyclerviewvertodoPlayList);
        recyclerviewvertodoPlayList.setAdapter(adapter);
    }



}