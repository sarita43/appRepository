package com.example.misvacasapp.bbddinterna;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MedicamentoBbdd extends SQLiteOpenHelper{

	private static final String DATABASE_NAME = "medicamentobbdd.db";
	private static final int DATABASE_VERSION = 1;

	public MedicamentoBbdd(Context context) {
		super(context,DATABASE_NAME,null,DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(MedicamentoDatosBbdd.tablaMedicamentos);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}
