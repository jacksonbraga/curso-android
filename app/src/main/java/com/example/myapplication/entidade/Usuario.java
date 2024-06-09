package com.example.myapplication.entidade;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;
@Entity(tableName = "usuario")
public class Usuario {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "nome")
    private String nome;

    @ColumnInfo(name = "senha")
    private String senha;

    public Usuario(@NonNull String nome, String senha) {
        this.nome = nome;
        this.senha = senha;

    }

    @NonNull
    public String getNome() {
        return this.nome;
    }

    @NonNull
    public String getSenha() {
        return this.senha;
    }

}
