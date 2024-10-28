package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.kuis.R;

import java.util.List;

import model.Mahasiswa;

public class MahasiswaAdapter extends BaseAdapter {
    private final Context context;
    private final List<Mahasiswa> mahasiswaList;
    private final String[] prodiArray = {"MI", "Linstrik"};

    public MahasiswaAdapter(Context context, List<Mahasiswa> mahasiswaList) {
        this.context = context;
        this.mahasiswaList = mahasiswaList;
    }

    @Override
    public int getCount() {
        return mahasiswaList.size();
    }

    @Override
    public Object getItem(int position) {
        return mahasiswaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Reuse view jika sudah ada untuk meningkatkan performa
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item,null);
        }

        // Menghubungkan komponen dengan XML
        EditText etNama = convertView.findViewById(R.id.et_nama);
        EditText etNim = convertView.findViewById(R.id.et_nim);
        Spinner spinnerProdi = convertView.findViewById(R.id.spinner_prodi);

        // Set nilai dari mahasiswa
        Mahasiswa mahasiswa = mahasiswaList.get(position);
        etNama.setText(mahasiswa.getNama());
        etNim.setText(mahasiswa.getNim());

        // Setup Spinner untuk Prodi
        ArrayAdapter<String> prodiAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, prodiArray);
        spinnerProdi.setAdapter(prodiAdapter);

        // Set pilihan prodi sesuai data
        String prodi = mahasiswa.getProdi();
        if (prodi != null) {
            int spinnerPosition = prodiAdapter.getPosition(prodi);
            spinnerProdi.setSelection(spinnerPosition);
        }

        return convertView;
    }
}
