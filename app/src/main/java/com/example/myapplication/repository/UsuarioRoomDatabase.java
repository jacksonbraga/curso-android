package com.example.myapplication.repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.myapplication.dao.UsuarioDao;
import com.example.myapplication.entidade.Usuario;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Usuario.class}, version = 2, exportSchema = false)
public abstract class UsuarioRoomDatabase extends RoomDatabase {

    abstract UsuarioDao usuarioDao();

    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile UsuarioRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static UsuarioRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UsuarioRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    UsuarioRoomDatabase.class, "usuario_database")
                            .addCallback(usuarioDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static Callback usuarioDatabaseCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                UsuarioDao dao = INSTANCE.usuarioDao();
                dao.deleteAll();
                Usuario usuario = new Usuario("demo", "demo");
                dao.insert(usuario);
            });
        }
    };
}
