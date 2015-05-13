package com.example.misvacasapp.bbddinterna;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Clase que se encarga de ejecutar las operaciores sobre la base de datos.
 * Crearla o actualizarla
 * 
 * @author sara
 * 
 */
public class MedicamentoBbdd extends SQLiteOpenHelper {

	// -------------------------------Atributos------------------------------//
	/** Nombre de la base de datos de los medicamentos **/
	private static final String DATABASE_NAME = "medicamentobbdd.db";
	/** Versión de la base de datos de los medicamentos **/
	private static final int DATABASE_VERSION = 1;

	// --------------------------------Métodos------------------------------//
	/**
	 * Contructor de la clase
	 * 
	 * @param context
	 *            Contexto
	 */
	public MedicamentoBbdd(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * Método que ejecuta la sentencia sql para crear la base de datos
	 * 
	 * @param db
	 *            SQLiteDataBase Base de datos
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// db.execSQL("drop table if exists medicamento");
		db.execSQL(MedicamentoDatosBbdd.tablaMedicamentos);
	}

	/**
	 * Método que ejecuta la sentencia sql para actualizar la base de datos
	 * 
	 * @param db
	 *            SQLiteDataBase Base de datos
	 * @param oldVersion
	 *            int Version de la base de datos
	 * @param newVersion
	 *            int Nueva version de la base de datos
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(MedicamentoDatosBbdd.medicamento);
	}

}
