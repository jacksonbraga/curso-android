package com.example.myapplication.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class UsuarioViewHolder extends RecyclerView.ViewHolder {
    private final TextView usuarioItemView;
    //private final TextView senhaItemView;

    private UsuarioViewHolder(View itemView) {
        super(itemView);
        usuarioItemView = itemView.findViewById(R.id.view_usuario);
        //senhaItemView = itemView.findViewById(R.id.view_senha);
    }

    public void bind(String usuario, String senha) {
        usuarioItemView.setText(usuario);
        //senhaItemView.setText(senha);
    }

    public static UsuarioViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_usuario, parent, false);
        return new UsuarioViewHolder(view);
    }
}
