package com.entrega1.persitence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.entrega1.dto.NotaDTO;

@Database(entities = (NotaDTO.class), version = 1, exportSchema = false)
public abstract class NotaDatabase extends RoomDatabase {

    public abstract NotaDao notaDao();

    private static NotaDatabase instance;

    public static NotaDatabase getDatabase (final Context context){
        if (instance == null) {
            synchronized (NotaDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context,
                                                    NotaDatabase.class,
                                                "notas.db"
                                                    ).allowMainThreadQueries().build();
                }
            }
        }
        return instance;

    }

}
