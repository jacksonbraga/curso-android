package com.example.myapplication.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.myapplication.entidade.Usuario;

import java.util.List;

@Dao
public interface UsuarioDao {

    @Query("SELECT * FROM usuario ORDER BY nome ASC")
    LiveData<List<Usuario>> getUsuarios();

    @Query("SELECT * FROM usuario WHERE nome like :nome")
    Usuario getUsuario(String nome);



    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Usuario usuario);

    @Query("DELETE FROM usuario")
    void deleteAll();
}
