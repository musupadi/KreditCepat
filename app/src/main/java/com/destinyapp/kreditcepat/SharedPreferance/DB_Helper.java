package com.destinyapp.kreditcepat.SharedPreferance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DB_Helper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "kreditcepat.db";
    private static final int DATABASE_VERSION = 3;
    public static final String TABLE_SESSION = "session";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_NAMA = "nama";
    public static final String COLUMN_ALAMAT = "alamat";
    public static final String COLUMN_TELPON = "telpon";
    public static final String COLUMN_NIK = "nik";
    public static final String COLUMN_LEVEL = "level";

    public DB_Helper(Context context){super(
            context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_SESSION+" (" +
                COLUMN_ID+" TEXT PRIMARY KEY, "+
                COLUMN_EMAIL+" TEXT NOT NULL, "+
                COLUMN_NAMA+" TEXT NOT NULL, "+
                COLUMN_TELPON+" TEXT NOT NULL, "+
                COLUMN_ALAMAT+" TEXT NOT NULL, "+
                COLUMN_NIK+" TEXT NOT NULL, "+
                COLUMN_LEVEL+" TEXT NOT NULL);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SESSION);
        this.onCreate(db);
    }
    public void saveSession(String id,String username,String nama,String telpon,String alamat,String nik,String level){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID,id);
        values.put(COLUMN_EMAIL,username);
        values.put(COLUMN_NAMA,nama);
        values.put(COLUMN_TELPON,telpon);
        values.put(COLUMN_ALAMAT,alamat);
        values.put(COLUMN_NIK,nik);
        values.put(COLUMN_LEVEL,level);
        db.insert(TABLE_SESSION,null,values);
        db.close();
    }
    public void userLogout(Context context){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_SESSION+"");
        Toast.makeText(context, "Logout Berhasil", Toast.LENGTH_SHORT).show();
    }
    public Cursor checkSession(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query ="SELECT * FROM "+TABLE_SESSION+"";
        Cursor cursor = db.rawQuery(query,null);
        return cursor;
    }
}
