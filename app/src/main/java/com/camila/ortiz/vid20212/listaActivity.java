package com.camila.ortiz.vid20212;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class listaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        RecyclerView recyclerView = findViewById(R.id.lista_pokemon);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        Retrofit retrofit = new Retrofit.Builder()

                .baseUrl("https://upn.lumenes.tk/pokemons/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        servicio service = retrofit.create(servicio.class);

        Call<List<pokemon>> listaCall = service.LISTACall();

        listaCall.enqueue(new Callback<List<pokemon>>() {
            @Override
            public void onResponse(Call<List<pokemon>> call, Response<List<pokemon>> response) {

                Log.e("info ", String.valueOf(response.code()));
                List<pokemon> pokemonList = response.body();
                adaptador adapterList = new adaptador(pokemonList, listaActivity.this);
                recyclerView.setAdapter(adapterList);

            }

            @Override
            public void onFailure(Call<List<pokemon>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
            }
        });
    }
}