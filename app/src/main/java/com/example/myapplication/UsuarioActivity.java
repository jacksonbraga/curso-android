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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.UsuarioListaAdapter;
import com.example.myapplication.entidade.Usuario;
import com.example.myapplication.view.UsuarioViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class UsuarioActivity extends AppCompatActivity  {

    public static final int NEW_USUARIO_ACTIVITY_REQUEST_CODE = 1;
    private GestureDetectorCompat mDetector;

    private UsuarioViewModel usuarioViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final UsuarioListaAdapter adapter = new UsuarioListaAdapter(new UsuarioListaAdapter.UsuarioDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);
        usuarioViewModel.getUsuarios().observe(this, usuarios -> {
            adapter.submitList(usuarios);
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(UsuarioActivity.this, NovoUsuarioActivity.class);
            startActivityForResult(intent, NEW_USUARIO_ACTIVITY_REQUEST_CODE);
        });

        mDetector = new GestureDetectorCompat(this, new MyGestureListener());
        recyclerView.setOnTouchListener(touchListener);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_USUARIO_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String[] dados = data.getStringArrayExtra("DADOS");
            if(dados[0] != null && dados[1] != null) {
              Usuario usuario = new Usuario(dados[0],dados[1]);
              usuarioViewModel.insert(usuario);
            }

        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
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
            startActivity(PokeActivity.getActIntent(UsuarioActivity.this));
            return true;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.poke) {
            startActivity(PokeActivity.getActIntent(UsuarioActivity.this));
            return true;
        }
        return true;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_poke, menu);
        return true;
    }

    public static Intent getActIntent(Activity activity) {
        return new Intent(activity, UsuarioActivity.class);
    }
}