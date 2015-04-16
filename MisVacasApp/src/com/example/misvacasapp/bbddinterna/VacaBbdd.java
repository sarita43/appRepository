package com.example.misvacasapp.bbddinterna;

import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class VacaBbdd extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "vacabbdd.db";
	private static final int DATABASE_VERSION = 1;

	public VacaBbdd(Context context) {
		super(context,DATABASE_NAME,null,DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("drop table if exists vaca");
		db.execSQL(VacaDatosBbdd.tablaVacas);
		//onUpgrade(db, DATABASE_VERSION, DATABASE_VERSION+1);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(VacaDatosBbdd.vaca);
	}

}
