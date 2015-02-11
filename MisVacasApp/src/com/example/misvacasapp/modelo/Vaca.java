package com.example.misvacasapp.modelo;

import java.sql.Date;

public class Vaca {

	private String id_vaca;
	private String raza;
	private Date fecha_nacimiento;
	private String id_madre;
	private byte[] foto;
	private String id_usuario;
	private String sexo;

	public Vaca() {
	
	}

	public Vaca(String id_usuario) {
		setId_usuario(id_usuario);
		
	}

	public Vaca(String id_vaca, String raza, Date fecha_nacimiento,
			String id_madre, String id_usuario, String sexo, byte[] foto) {
		setId_vaca(id_vaca);
		setRaza(raza);
		setFecha_nacimiento(fecha_nacimiento);
		setId_madre(id_madre);
		setFoto(foto);
		setId_usuario(id_usuario);
		setSexo(sexo);
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

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public String getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(String id_usuario) {
		this.id_usuario = id_usuario;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

}
