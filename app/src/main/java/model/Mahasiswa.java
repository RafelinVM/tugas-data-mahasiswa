package model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "mahasiswa")
public class Mahasiswa {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nama;
    private String nim;
    private String prodi;

    // Getter and Setter methods
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getNim() { return nim; }
    public void setNim(String nim) { this.nim = nim; }

    public String getProdi() { return prodi; }
    public void setProdi(String prodi) { this.prodi = prodi; }
}
