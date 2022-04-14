package com.example.sqlite110.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class DBController extends SQLiteOpenHelper {

    public DBController(Context context) {
        super(context, "ProdiTI", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table teman (id omterger primary key, nama text, telepon text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists teman");
        onCreate(db);
    }

    //mdl
    //insert data
    public void  insertData(HashMap<String, String> queryValues){
        SQLiteDatabase basisdata = this.getWritableDatabase();
        ContentValues nilai = new ContentValues();
        nilai.put("nama", queryValues.get("nama"));
        nilai.put("telpon", queryValues.get("telpon"));
        basisdata.insert("teman", null, nilai);
        basisdata.close();
    }
    public ArrayList<HashMap<String,String>> getAllTeman(){
        ArrayList<HashMap<String,String>> daftarTeman;
        daftarTeman = new ArrayList<HashMap<String, String>>();
        String selectQuery = "Select * from teman";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do {
                //ambil data
                HashMap<String,String> map = new HashMap<>();
                map.put("id",cursor.getString(0));
                map.put("nama",cursor.getString(1));
                map.put("telpon",cursor.getString(2));
                //memasukkan ke daftar teman
                daftarTeman.add(map);
            } while (cursor.moveToNext());
        }
        db.close();
        return daftarTeman;
    }
    public void UpdateData(HashMap<String,String> queryValue){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues nilai = new ContentValues();
        nilai.put("nama", queryValue.get("nama"));
        nilai.put("telpon", queryValue.get("telpon"));
        db.update("teman", nilai, "id=?", new String[]{queryValue.get("id")});
        db.close();
    }
    public void DeleteData(HashMap<String,String> queryValue)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("teman", "id=?",new String[]{queryValue.get("id")});
    }
}
