package com.example.echowprojectsapp.NetworkTasks.GruposNetworkTasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.echowprojectsapp.Adapters.CustomAdapterBuscarGrupos;
import com.example.echowprojectsapp.Models.buscarGrupo;
import com.example.echowprojectsapp.Utilidades.Imagenes.ImageDownloader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BuscarGruposAsyncTask extends AsyncTask<String, Void, Pair<Integer, List<buscarGrupo>>> {
    private static final String TAG = "BuscarGrupoAsyncTask";
    private Context context;
    private RecyclerView recyclerView;
    private CustomAdapterBuscarGrupos adapter;
    private ProgressDialog progressDialog;

    public BuscarGruposAsyncTask(Context context, RecyclerView recyclerView, CustomAdapterBuscarGrupos adapter) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.adapter = adapter;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Buscando...");
        progressDialog.setCancelable(false);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //progressDialog.show();
    }

    @Override
    protected Pair<Integer, List<buscarGrupo>> doInBackground(String... params) {
        String idusuario = params[0];
        String search = params[1];

        try {
            URL url = new URL("https://phpclusters-156700-0.cloudclusters.net/buscarGrupo.php");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setDoOutput(true);

            JSONObject jsonRequest = new JSONObject();
            jsonRequest.put("nombre", search);
            jsonRequest.put("idusuario", idusuario);

            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            out.write(jsonRequest.toString().getBytes());
            out.flush();
            out.close();

            int responseCode = urlConnection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                inputStream.close();

                List<buscarGrupo> result = parseJsonResponse(response.toString());
                return new Pair<>(responseCode, result);
            } else {
                Log.e(TAG, "Error response code: " + responseCode);
            }

            urlConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Pair<>(HttpURLConnection.HTTP_INTERNAL_ERROR, null);
    }

    @Override
    protected void onPostExecute(Pair<Integer, List<buscarGrupo>> result) {
        progressDialog.dismiss();

        int responseCode = result.first;
        List<buscarGrupo> data = result.second;

        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Parse and process the JSON data
            if (data != null) {
                adapter.setDataList(data);
                adapter.notifyDataSetChanged();
            }
        } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
            // No groups found, show a toast
            Toast.makeText(context, "¡No se encontró ningún grupo!", Toast.LENGTH_SHORT).show();
        } else {
            // Handle other response codes or errors
            Toast.makeText(context, "¡No se encontró ningún grupo!", Toast.LENGTH_SHORT).show();
        }
    }

    private List<buscarGrupo> parseJsonResponse(String json) {
        List<buscarGrupo> dataList = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // Extrae la informacion y crea objetos
                Integer idgrupo = jsonObject.getInt("idgrupo");
                String nombre = jsonObject.getString("nombre");
                String descripcion = jsonObject.getString("descripcion");
                Integer idOwner = jsonObject.getInt("idusuario");
                String usuario = jsonObject.getString("usuario");
                Integer idvisualizacion = jsonObject.getInt("idvisualizacion");
                Bitmap imageResource = ImageDownloader.downloadImage(jsonObject.getString("enlacefoto"));
                Integer totalusuarios = jsonObject.getInt("totalusuarios");
                Integer ismember = jsonObject.getInt("ismember");


                dataList.add(new buscarGrupo(idgrupo, nombre, descripcion,idOwner, usuario, idvisualizacion, imageResource, totalusuarios, ismember));
            }

        } catch (JSONException e) {
            Log.e(TAG, "Error parsing JSON response: " + e.getMessage());
        }

        return dataList;
    }
}
