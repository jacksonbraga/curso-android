package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.PokeListaAdapter;
import com.example.myapplication.entidade.Usuario;
import com.example.myapplication.view.UsuarioViewModel;

import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;

    private UsuarioViewModel usuarioViewModel;
    private RecyclerView recyclerView;
    private PokeListaAdapter pokeListaAdapter;
    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve entered username and password
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                Usuario usuario = usuarioViewModel.getUsuario(username);
                if(usuario == null) {
                  Toast.makeText(MainActivity.this, "Inválido usuário ou senha", Toast.LENGTH_SHORT).show();
                  return;
                }

                if (usuario.getSenha().equals(password)) {
                    startActivity(PokeActivity.getActIntent(MainActivity.this));
                } else {
                    Toast.makeText(MainActivity.this, "Inválido usuário ou senha\"", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public static Intent getActIntent(Activity activity) {
        return new Intent(activity, MainActivity.class);
    }

}
