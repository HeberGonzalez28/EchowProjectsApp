package com.example.echowprojectsapp.Activities.Grupos;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.echowprojectsapp.Adapters.CustomAdapter;
import com.example.echowprojectsapp.Models.vistaDeGrupo;
import com.example.echowprojectsapp.NetworkTasks.GruposNetworkTasks.FetchDataAsyncGruposPrincipal;
import com.example.echowprojectsapp.R;
import com.example.echowprojectsapp.Utilidades.Navegacion.NavigationClickListener;
import com.example.echowprojectsapp.Utilidades.Navegacion.NavigationGruposPrincipalClickListener;
import com.example.echowprojectsapp.Utilidades.Token.JwtDecoder;
import com.example.echowprojectsapp.Utilidades.Token.token;

import java.util.List;

public class ActivityGrupoPrincipal extends AppCompatActivity implements FetchDataAsyncGruposPrincipal.DataFetchListener {

    // Declaración de variables
    private RecyclerView lista;
    private DrawerLayout drawerLayout;
    private ImageButton openMenuButton;
    private ProgressDialog progressDialog;
    private NavigationGruposPrincipalClickListener navigationClickListener;
    private com.example.echowprojectsapp.Utilidades.Token.token token = new token(this);
    private int idUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupo_principal);

        // Inicializa listener para elementos
        navigationClickListener = new NavigationGruposPrincipalClickListener(this, this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando...");
        progressDialog.setCancelable(false);

        idUsuario = Integer.parseInt(JwtDecoder.decodeJwt(token.recuperarTokenFromKeystore()));

        // Inicialización de vistas y elementos del diseño
        lista = (RecyclerView) findViewById(R.id.recyclerview_GruposPrincipal);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        openMenuButton = (ImageButton) findViewById(R.id.btn_GruposPrincipalMenu);

        // Fetch data from the server
        String url = "https://phpclusters-156700-0.cloudclusters.net/principalGrupos.php";
        // Reemplazar por el idusuario - Motivos de prueba
        progressDialog.show();
        new FetchDataAsyncGruposPrincipal(this, progressDialog).execute(url, String.valueOf(idUsuario));


        // Configuración del administrador de diseño y adaptador para el RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this); // Use an appropriate layout manager
        lista.setLayoutManager(layoutManager);

        // Listener para abrir el menú lateral
        openMenuButton.setOnClickListener(v -> {
            drawerLayout.openDrawer(findViewById(R.id.side_menu));
        });

        // Listener para menu de navegacion
        View.OnClickListener buttonClickNav = new NavigationClickListener(this,this);
    }

    @Override
    public void onDataFetched(List<vistaDeGrupo> dataList) {
        // Muestra el Recycle view con la nueva informacion
        progressDialog.dismiss(); // Esconde el spinner de carga
        CustomAdapter adapter = new CustomAdapter(this, dataList, idUsuario, lista);
        lista.setAdapter(adapter);
    }
}