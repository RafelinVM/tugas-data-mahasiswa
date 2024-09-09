package com.example.kuis.data;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import com.example.kuis.R;
import com.example.kuis.dao.MahasiswaDao;

public class AppDatabase extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_database);
        AppDatabase INSTANCE;

        public abstract MahasiswaDao mahasiswaDao();

        public static AppDatabase getDatabase(final Context context) {
            if (INSTANCE == null) {
                synchronized (AppDatabase.class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                        AppDatabase.class, "kuis_database")
                                .fallbackToDestructiveMigration()
                                .build();
                    }
                }
            }
            return INSTANCE;
        }
    }


}
}