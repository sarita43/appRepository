package com.example.misvacasapp.modelo;

import java.util.Date;

public class Medicamento {

	private int id_medicamento;
	private Date fecha;
	private String tipo;
	private String descripcion;
	private String id_vaca;

	public Medicamento() {

	}

	public Medicamento(int id_medicamento, Date fecha, String tipo,
			String descripcion, String id_vaca) {
		setId_medicamento(id_medicamento);
		setFecha(fecha);
		setTipo(tipo);
		setDescripcion(descripcion);
		setId_vaca(id_vaca);
	}

	public int getId_medicamento() {
		return id_medicamento;
	}

	public void setId_medicamento(int id_medicamento) {
		this.id_medicamento = id_medicamento;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getId_vaca() {
		return id_vaca;
	}

	public void setId_vaca(String id_vaca) {
		this.id_vaca = id_vaca;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
