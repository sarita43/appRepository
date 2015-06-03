package com.example.misvacasapp.modelo;

import java.sql.Date;

/**
 * Clase producci�n
 * 
 * @author Sara Mart�nez L�pez
 * 
 */
public class Produccion {

	// --------------------------Atributos------------------------//
	private int id_produccion;
	private Date fecha;
	private String tipo;
	private String id_usuario;
	private int cantidad;

	// ---------------------------M�todos------------------------//
	/**
	 * Constructor sin argumentos
	 */
	public Produccion() {

	}

	/**
	 * Constructor con argumentos
	 * 
	 * @param id_produccion
	 *            int Identificador de la producci�n
	 * @param fecha
	 *            Date. Fecha de la producci�n
	 * @param tipo
	 *            String. Tipo de producci�n
	 * @param id_usuario
	 *            String. Id del usuario
	 */
	public Produccion(int id_produccion, Date fecha, String tipo,
			String id_usuario, int cantidad) {
		setId_produccion(id_produccion);
		setFecha(fecha);
		setTipo(tipo);
		setId_usuario(id_usuario);
		setCantidad(cantidad);
	}

	/**
	 * Devuelve el identificador de la producci�n
	 * 
	 * @return int. Id de la produci�n
	 */
	public int getId_produccion() {
		return id_produccion;
	}

	/**
	 * Guarda el identificador de la producci�n
	 * 
	 * @param id_produccion
	 *            int
	 */
	public void setId_produccion(int id_produccion) {
		this.id_produccion = id_produccion;
	}

	/**
	 * Devuelve la fecha de la producci�n
	 * 
	 * @return Date. Fecha de la producci�n
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * Guarda la fecha de la producci�n
	 * 
	 * @param fecha
	 *            Date. Fecha
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * Devuelve el tipo de la producci�n
	 * 
	 * @return String. Tipo de la producci�n
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Guarda el tipo de la producci�n
	 * 
	 * @param tipo
	 *            String. Tipo
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * Devuelve la cantidad de producci�n
	 * 
	 * @return int. Cantidad
	 */
	public int getCantidad() {
		return cantidad;
	}

	/**
	 * Guarda la cantidad de producci�n
	 * 
	 * @param cantidad
	 *            int
	 */
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * Devuelve el id del usuario
	 * 
	 * @return String id usuario
	 */
	public String getId_usuario() {
		return id_usuario;
	}

	/**
	 * Guarda el id del usuario
	 * 
	 * @param id_usuario
	 *            String id del usuario
	 */
	public void setId_usuario(String id_usuario) {
		this.id_usuario = id_usuario;
	}
}
