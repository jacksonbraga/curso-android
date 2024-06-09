package com.example.myapplication.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.myapplication.dao.UsuarioDao;
import com.example.myapplication.entidade.Usuario;

import java.util.List;

public class UsuarioRepository {

    private UsuarioDao usuarioDao;
    private final LiveData<List<Usuario>> listaUsuarios;

    private final UsuarioRoomDatabase db;

    public UsuarioRepository(Application application) {
        db = UsuarioRoomDatabase.getDatabase(application);
        usuarioDao = db.usuarioDao();
        listaUsuarios = usuarioDao.getUsuarios();
    }

    public LiveData<List<Usuario>> getUsuarios() {
        return listaUsuarios;
    }

    public Usuario getUsuario(String nome) {
        usuarioDao = db.usuarioDao();
        Log.d("TAG2",nome);
        return usuarioDao.getUsuario(nome);
    }
    public void insert(Usuario usuario) {
        UsuarioRoomDatabase.databaseWriteExecutor.execute(() -> {
            usuarioDao.insert(usuario);
        });
    }
}
