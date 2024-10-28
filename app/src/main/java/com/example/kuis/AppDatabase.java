package com.example.belajarroomdb;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import dao.ProdukDao;
import model.Produk;

@Database(entities = {Produk.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ProdukDao produkDao();


}
