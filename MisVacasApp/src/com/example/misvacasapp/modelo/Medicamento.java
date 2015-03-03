package com.example.misvacasapp.modelo;

import java.sql.Date;

/**
 * Clase medicameno
 * @author Sara Martinez Lopez
 * */
public class Medicamento {

	//Atributos
	/**Id medicamento*/
	private int id_medicamento;
	/**Fecha de medicamentos */
	private Date fecha;
	/**Tipo de medicamento*/
	private String tipo;
	/**Descripcion del medicamento*/
	private String descripcion;
	/** Id de la vaca*/
	private String id_vaca;

	//MÃ©todos
	
	/**Constructor del medicamento sin atributos*/
	public Medicamento() {

	}
	
	/**
	 * Constructor del medicamento con atributos
	 * @param id_medicamento Id medicamento
	 * @param fecha Fecha del medicamento
	 * @param tipo Tipo de medicamento
	 * @param descripcion DescripciÃ³n del medicamento
	 * @param id_vaca Id de la vaca
	 * */
	public Medicamento(int id_medicamento, Date fecha, String tipo,
			String descripcion, String id_vaca) {
		setId_medicamento(id_medicamento);
		setFecha(fecha);
		setTipo(tipo);
		setDescripcion(descripcion);
		setId_vaca(id_vaca);
	}

	/**
	 * Devuelve el id del medicamento
	 * @return int Id medicamento
	 * */
	public int getId_medicamento() {
		return id_medicamento;
	}

	/**
	 * Guarda el id del medicamento
	 * @param id_medicamento Id del medicamento
	 * */
	public void setId_medicamento(int id_medicamento) {
		this.id_medicamento = id_medicamento;
	}

	/**
	 * Devuelve la fecha del medicamento
	 * @return java.sql.Date Fecha del medicamento
	 * */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * Guarda la fecha del medicamento
	 * @param fecha Fecha del medicamento
	 * */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * Devuelve el tipo del medicamento
	 * @return String Tipo del medicamento
	 * */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Guarda el tipo del medicamento
	 * @param tipo Tipo del medicamento
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	/**
	 * Devuelve el id de la vaca
	 * @return String Id de la vaca
	 * */
	public String getId_vaca() {
		return id_vaca;
	}

	/**
	 * Guarda el id de la vaca
	 * @param id_vaca Id de la vaca
	 * */
	public void setId_vaca(String id_vaca) {
		this.id_vaca = id_vaca;
	}

	/**
	 * Devuelve la descripción del medicamento
	 * @return String Descripción del medicamento
	 * */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Guarda la descripción del medicamento
	 * @param descripcion Descripción del medicamento
	 * */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
