package com.example.misvacasapp.bbddinterna;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ProduccionBbdd extends SQLiteOpenHelper {

	// ------------------------------Atributos----------------------------//
	/** Nombre de la base de datos de la produccion de carne y leche */
	private static final String DATABASE_NAME = "produccionbbdd.db";
	/** Versión de la base de datos */
	private static final int DATABASE_VERSION = 1;

	// -----------------------------Métodos------------------------------//
	/**
	 * Contructor de la clase
	 * 
	 * @param context
	 */
	public ProduccionBbdd(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * Método que ejecuta la sentencia sql para crear la base de datos
	 * 
	 * @param db
	 *            SQLiteDatabase Base de datos
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(ProduccionDatosBbdd.tablaProduccion);
	}

	/**
	 * Método que ejecuta la sentencia sql para actualizar la base de datos
	 * 
	 * @param db
	 *            SQLiteDatabase Base de datos
	 * @param oldVersion
	 *            int Version de la base de datos
	 * @param newVersion
	 *            int Nueva version de la base de datos
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(ProduccionDatosBbdd.produccion);
	}
}