package com.example.myapplication.view;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplication.entidade.Usuario;
import com.example.myapplication.repository.UsuarioRepository;

import java.util.List;

public class UsuarioViewModel extends AndroidViewModel {

    private final UsuarioRepository usuarioRepository;
    private final LiveData<List<Usuario>> listaUsuarios;

    public UsuarioViewModel(Application application) {
        super(application);
        usuarioRepository = new UsuarioRepository(application);
        listaUsuarios = usuarioRepository.getUsuarios();
    }

    public LiveData<List<Usuario>> getUsuarios() {
        return listaUsuarios;
    }

    public Usuario getUsuario(String nome) {
        Log.d("TAG1",nome);
        return usuarioRepository.getUsuario(nome);
    }

    public void insert(Usuario usuario) {
        usuarioRepository.insert(usuario);
    }
}
