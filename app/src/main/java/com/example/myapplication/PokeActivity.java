package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.GridLayoutManager;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.myapplication.adapter.PokeListaAdapter;
import com.example.myapplication.entidade.Poke;
import com.example.myapplication.entidade.PokeResposta;
import com.example.myapplication.service.PokeApiService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PokeActivity extends AppCompatActivity {

    private static final String TAG = "POKEDEX";
    private Retrofit retrofit;
    private RecyclerView recyclerView;
    private PokeListaAdapter pokeListaAdapter;
    private int offset;
    private boolean aptoParaCargar;
    private GestureDetectorCompat mDetector;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poke);

        recyclerView = findViewById(R.id.recyclerView);
        pokeListaAdapter = new PokeListaAdapter(this);
        recyclerView.setAdapter(pokeListaAdapter);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if (aptoParaCargar) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            Log.i(TAG, " Llegamos al final.");

                            aptoParaCargar = false;
                            offset += 20;
                            recupera(offset);
                        }
                    }
                }
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

       mDetector = new GestureDetectorCompat(this, new MyGestureListener());

        aptoParaCargar = true;
        offset = 0;
        recupera(offset);
    }

    View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return mDetector.onTouchEvent(event);

        }
    };

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            startActivity(UsuarioActivity.getActIntent(PokeActivity.this));
            return true;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection.
        int id = item.getItemId();
        if (id == R.id.cadastro) {
            startActivity(UsuarioActivity.getActIntent(PokeActivity.this));
            return true;
        }
        return true;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_usuario, menu);
        return true;
    }

    private void recupera(int offset) {
        PokeApiService service = retrofit.create(PokeApiService.class);
        Call<PokeResposta> pokemonRespuestaCall = service.recuperarListaPoke(20, offset);

        pokemonRespuestaCall.enqueue(new Callback<PokeResposta>() {
            @Override
            public void onResponse(Call<PokeResposta> call, Response<PokeResposta> response) {
                aptoParaCargar = true;
                if (response.isSuccessful()) {
                    PokeResposta pokeResposta = response.body();
                    ArrayList<Poke> listaPoke = pokeResposta.getResults();

                    pokeListaAdapter.adicionarListaPokemon(listaPoke);
                } else {
                    Log.e(TAG, " onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<PokeResposta> call, Throwable t) {
                aptoParaCargar = true;
                Log.e(TAG, " onFailure: " + t.getMessage());
            }
        });
    }

    public static Intent getActIntent(Activity activity) {
        return new Intent(activity, PokeActivity.class);
    }
}
