package com.user.bdsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class BaseDatos extends SQLiteOpenHelper{
        private static final String DATABASE_NAME = "MiBaseDeDatos.db";
        private static final String TABLE_NAME = "mi_tabla";
        private static final String COL_1 = "ID";
        private static final String COL_2 = "NOMBRE";

        public BaseDatos(Context context) {
            super(context, DATABASE_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE TEXT)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }

        public boolean insertarDatos(String nombre) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_2, nombre);
            long result = db.insert(TABLE_NAME, null, contentValues);
            return result != -1;
        }

        public Cursor obtenerDatos() {
            SQLiteDatabase db = this.getWritableDatabase();
            return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        }

}
