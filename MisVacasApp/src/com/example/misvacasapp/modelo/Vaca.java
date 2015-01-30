package com.example.misvacasapp.modelo;

import java.sql.Date;

public class Vaca {

	private String id_vaca;
	private String raza;
	private Date fecha_nacimiento;
	private String id_madre;
	private String foto;
	private String id_usuario;

	public Vaca() {
	
	}

	public Vaca(String id_usuario) {
		setId_usuario(id_usuario);
		
	}

	public Vaca(String id_vaca, String raza, Date fecha_nacimiento,
			String id_madre, String foto, String id_usuario) {
		setId_vaca(id_vaca);
		setRaza(raza);
		setFecha_nacimiento(fecha_nacimiento);
		setId_madre(id_madre);
		setFoto(foto);
		setId_usuario(id_usuario);
	}

	public String getId_vaca() {
		return id_vaca;
	}

	public void setId_vaca(String id_vaca) {
		this.id_vaca = id_vaca;
	}

	public String getRaza() {
		return raza;
	}

	public void setRaza(String raza) {
		this.raza = raza;
	}

	public Date getFecha_nacimiento() {
		return fecha_nacimiento;
	}

	public void setFecha_nacimiento(Date fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
	}

	public String getId_madre() {
		return id_madre;
	}

	public void setId_madre(String id_madre) {
		this.id_madre = id_madre;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(String id_usuario) {
		this.id_usuario = id_usuario;
	}

}
