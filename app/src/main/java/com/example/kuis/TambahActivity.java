package com.example.kuis;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import model.Mahasiswa;

public class TambahActivity extends AppCompatActivity {

    EditText etNama, etNim;
    Spinner spinnerProdi;
    Button btnSimpan;
    AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        // Inisialisasi EditText, Spinner, dan Button
        etNama = findViewById(R.id.et_nama);
        etNim = findViewById(R.id.et_nim);
        spinnerProdi = findViewById(R.id.spinner_prodi);
        btnSimpan = findViewById(R.id.btn_simpan);

        // Inisialisasi database Room
        appDatabase = Room.databaseBuilder(this, AppDatabase.class, "mahasiswa.db")
                .allowMainThreadQueries()
                .build();

        // Setup Spinner
        setupSpinner();

        // Aksi ketika tombol simpan diklik
        btnSimpan.setOnClickListener(v -> {
            String nama = etNama.getText().toString();
            String nim = etNim.getText().toString();
            String prodi = spinnerProdi.getSelectedItem().toString(); // Ambil data dari Spinner

            if (nama.isEmpty() || nim.isEmpty() || prodi.isEmpty()) {
                Toast.makeText(this, "Nama, NIM, dan Prodi harus diisi", Toast.LENGTH_SHORT).show();
                return;
            }

            // Buat objek Mahasiswa dengan data yang diisi
            Mahasiswa mahasiswa = new Mahasiswa();
            mahasiswa.setNama(nama);
            mahasiswa.setNim(nim);
            mahasiswa.setProdi(prodi);

            // Masukkan data ke database
            new Thread(() -> {
                appDatabase.mahasiswaDao().insert(mahasiswa);
                runOnUiThread(() -> {
                    Toast.makeText(this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                    // Kembali ke MainActivity
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Menutup TambahActivity
                });
            }).start();
        });
    }

    private void setupSpinner() {
        // Setup Spinner dengan data pilihan
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.prodi_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProdi.setAdapter(adapter);
    }
}
