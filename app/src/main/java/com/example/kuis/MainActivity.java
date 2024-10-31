package com.example.kuis;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import adapter.MahasiswaAdapter;
import dao.MahasiswaDao;
import model.Mahasiswa;

public class MainActivity extends AppCompatActivity {

    private ListView lvMahasiswa;
    private FloatingActionButton fabTambah;
    private AppDatabase appDatabase;
    private List<Mahasiswa> mahasiswaList;
    private MahasiswaAdapter mahasiswaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvMahasiswa = findViewById(R.id.lv_mahasiswa);
        fabTambah = findViewById(R.id.fab_tambah);

        // Initialize database
        appDatabase = Room.databaseBuilder(this, AppDatabase.class, "mahasiswa.db")
                .allowMainThreadQueries()
                .build();

        // Display data
        tampilkanData();

        // Event listener for adding new mahasiswa
        fabTambah.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, TambahActivity.class);
            startActivity(intent);
        });

        // Event listener for item click in ListView
        lvMahasiswa.setOnItemClickListener((parent, view, position, id) -> {
            Mahasiswa selectedMahasiswa = mahasiswaList.get(position);
            Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
            intent.putExtra("MAHASISWA_ID", selectedMahasiswa.getId()); // Send mahasiswa ID
            intent.putExtra("MAHASISWA_NAME", selectedMahasiswa.getNama()); // Send mahasiswa name
            intent.putExtra("MAHASISWA_NIM", selectedMahasiswa.getNim()); // Send mahasiswa NIM
            intent.putExtra("MAHASISWA_PRODI", selectedMahasiswa.getProdi()); // Send mahasiswa prodi
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh data when coming back to this activity
        tampilkanData();
    }

    private void tampilkanData() {
        MahasiswaDao mahasiswaDao = appDatabase.mahasiswaDao();
        mahasiswaList = mahasiswaDao.getAll(); // Fetch data
        mahasiswaAdapter = new MahasiswaAdapter(this, mahasiswaList); // Convert data
        lvMahasiswa.setAdapter(mahasiswaAdapter); // Display data
    }
}
