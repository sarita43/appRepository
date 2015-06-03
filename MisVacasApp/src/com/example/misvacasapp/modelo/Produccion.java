package com.example.misvacasapp.modelo;

import java.sql.Date;

/**
 * Clase producción
 * 
 * @author Sara Martínez López
 * 
 */
public class Produccion {

	// --------------------------Atributos------------------------//
	private int id_produccion;
	private Date fecha;
	private String tipo;
	private String id_usuario;
	private int cantidad;

	// ---------------------------Métodos------------------------//
	/**
	 * Constructor sin argumentos
	 */
	public Produccion() {

	}

	/**
	 * Constructor con argumentos
	 * 
	 * @param id_produccion
	 *            int Identificador de la producción
	 * @param fecha
	 *            Date. Fecha de la producción
	 * @param tipo
	 *            String. Tipo de producción
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
	 * Devuelve el identificador de la producción
	 * 
	 * @return int. Id de la produción
	 */
	public int getId_produccion() {
		return id_produccion;
	}

	/**
	 * Guarda el identificador de la producción
	 * 
	 * @param id_produccion
	 *            int
	 */
	public void setId_produccion(int id_produccion) {
		this.id_produccion = id_produccion;
	}

	/**
	 * Devuelve la fecha de la producción
	 * 
	 * @return Date. Fecha de la producción
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * Guarda la fecha de la producción
	 * 
	 * @param fecha
	 *            Date. Fecha
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * Devuelve el tipo de la producción
	 * 
	 * @return String. Tipo de la producción
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Guarda el tipo de la producción
	 * 
	 * @param tipo
	 *            String. Tipo
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * Devuelve la cantidad de producción
	 * 
	 * @return int. Cantidad
	 */
	public int getCantidad() {
		return cantidad;
	}

	/**
	 * Guarda la cantidad de producción
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
