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

public class UpdateActivity extends AppCompatActivity {

    private EditText etNama, etNim;
    private Spinner spinnerProdi;
    private Button btnUpdate, btnDelete;
    private AppDatabase appDatabase;
    private int mahasiswaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        // Initialize EditText, Spinner, and Button
        etNama = findViewById(R.id.et_nama);
        etNim = findViewById(R.id.et_nim);
        spinnerProdi = findViewById(R.id.spinner_prodi);
        btnUpdate = findViewById(R.id.btn_update);
        btnDelete = findViewById(R.id.btn_delete);

        // Initialize database
        appDatabase = Room.databaseBuilder(this, AppDatabase.class, "mahasiswa.db")
                .allowMainThreadQueries()
                .build();

        // Setup Spinner
        setupSpinner();

        // Get data from Intent
        Intent intent = getIntent();
        mahasiswaId = intent.getIntExtra("MAHASISWA_ID", -1);

        if (mahasiswaId != -1) {
            // Fetch and display data
            new Thread(() -> {
                Mahasiswa mahasiswa = appDatabase.mahasiswaDao().findById(mahasiswaId);
                runOnUiThread(() -> {
                    if (mahasiswa != null) {
                        etNama.setText(mahasiswa.getNama());
                        etNim.setText(mahasiswa.getNim());
                        // Set Spinner selection
                        ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) spinnerProdi.getAdapter();
                        int position = adapter.getPosition(mahasiswa.getProdi());
                        spinnerProdi.setSelection(position);
                    } else {
                        Toast.makeText(this, "Data mahasiswa tidak ditemukan", Toast.LENGTH_SHORT).show();
                    }
                });
            }).start();

            // Handle update button click
            btnUpdate.setOnClickListener(v -> {
                String updatedNama = etNama.getText().toString();
                String updatedNim = etNim.getText().toString();
                String updatedProdi = spinnerProdi.getSelectedItem().toString();

                if (updatedNama.isEmpty() || updatedNim.isEmpty() || updatedProdi.isEmpty()) {
                    Toast.makeText(this, "Nama, NIM, dan Prodi harus diisi", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Update data in the database
                Mahasiswa mahasiswa = new Mahasiswa();
                mahasiswa.setId(mahasiswaId); // Set ID for update
                mahasiswa.setNama(updatedNama);
                mahasiswa.setNim(updatedNim);
                mahasiswa.setProdi(updatedProdi);

                new Thread(() -> {
                    appDatabase.mahasiswaDao().update(mahasiswa);
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Data Berhasil Diperbarui", Toast.LENGTH_SHORT).show();
                        // Return to MainActivity
                        Intent intentToMain = new Intent(this, MainActivity.class);
                        startActivity(intentToMain);
                        finish(); // Close UpdateActivity
                    });
                }).start();
            });

            // Handle delete button click
            btnDelete.setOnClickListener(v -> {
                new Thread(() -> {
                    Mahasiswa mahasiswa = appDatabase.mahasiswaDao().findById(mahasiswaId);
                    if (mahasiswa != null) {
                        appDatabase.mahasiswaDao().delete(mahasiswa);
                        runOnUiThread(() -> {
                            Toast.makeText(this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                            // Return to MainActivity
                            Intent intentToMain = new Intent(this, MainActivity.class);
                            startActivity(intentToMain);
                            finish(); // Close UpdateActivity
                        });
                    } else {
                        runOnUiThread(() -> Toast.makeText(this, "Mahasiswa tidak ditemukan", Toast.LENGTH_SHORT).show());
                    }
                }).start();
            });
        } else {
            Toast.makeText(this, "ID Mahasiswa tidak valid", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupSpinner() {
        // Implement setup for spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.prodi_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProdi.setAdapter(adapter);
    }
}
