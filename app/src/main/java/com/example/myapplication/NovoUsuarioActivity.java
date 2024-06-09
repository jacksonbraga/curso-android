package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

public class NovoUsuarioActivity extends AppCompatActivity {

    private EditText mEditUsuarioView;
    private EditText mEditSenhaView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_usuario);
        mEditUsuarioView = findViewById(R.id.edit_usuario);
        mEditSenhaView = findViewById(R.id.edit_senha);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(mEditUsuarioView.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                String usuario = mEditUsuarioView.getText().toString();
                String senha = mEditSenhaView.getText().toString();

                String[] valores = { usuario , senha };
                Log.d("TAG5",valores[0]);
                Log.d("TAG6",valores[1]);
                replyIntent.putExtra("DADOS", valores);

                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });
    }
}

