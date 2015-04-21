package com.example.misvacasapp.bbddinterna;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class VacaBbdd extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "vacabbdd.db";
	private static int DATABASE_VERSION = 1;

	public VacaBbdd(Context context) {
		super(context,DATABASE_NAME,null,DATABASE_VERSION);
	}
	
	public VacaBbdd(Context context,int version) {
		super(context,DATABASE_NAME,null,version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(VacaDatosBbdd.tablaVacas);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.setVersion(db.getVersion()+1);
		db.execSQL(VacaDatosBbdd.vaca);
	}
	
	public void setVersion(int version){
		DATABASE_VERSION = version;
	}
	
}
