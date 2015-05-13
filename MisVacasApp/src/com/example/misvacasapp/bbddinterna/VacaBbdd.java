package com.example.misvacasapp.bbddinterna;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class VacaBbdd extends SQLiteOpenHelper {

	//------------------------------Atributos----------------------------//
	/**Nombre de la base de datos de los animales*/
	private static final String DATABASE_NAME = "vacabbdd.db";
	/**Versi�n de la base de datos*/
	private static final int DATABASE_VERSION = 1;

	//-----------------------------M�todos------------------------------//
	/**
	 * Contructor de la clase
	 * @param context
	 */
	public VacaBbdd(Context context) {
		super(context,DATABASE_NAME,null,DATABASE_VERSION);
	}

	/**
	 * M�todo que ejecuta la sentencia sql para crear la base de datos
	 * @param db SQLiteDatabase Base de datos
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(VacaDatosBbdd.tablaVacas);
	}

	/**
	 * M�todo que ejecuta la sentencia sql para actualizar la base de datos
	 * @param db SQLiteDatabase Base de datos
	 * @param oldVersion
	 *            int Version de la base de datos
	 * @param newVersion
	 *            int Nueva version de la base de datos
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(VacaDatosBbdd.vaca);
	}
}
