package com.example.misvacasapp.modelo;

import java.sql.Date;

/**
 * Clase vaca
 * 
 * @author Sara Martinez Lopez
 * */
public class Vaca {
	// --------------------------------Atributos--------------------------------//
	/** Id del animal */
	private String id_vaca;
	/** Raza del animal */
	private String raza;
	/** Fecha de nacimiento del animal */
	private Date fecha_nacimiento;
	/** Id de la madre del animal */
	private String id_madre;
	/** Foto del animal o foto por defecto */
	private String foto;
	/** Id del usuario del animal */
	private String id_usuario;
	/** Sexo del animal */
	private String sexo;

	// -------------------------------Métodos--------------------------------//
	/**
	 * Constructor del animal sin atributos
	 * */
	public Vaca() {
	}

	/**
	 * Constructor d
	 * 
	 * 
	 * public Vaca(String id_usuario) { setId_usuario(id_usuario); }
	 * */
	/**
	 * Constructor del animal con atributos
	 * 
	 * @param id_vaca
	 *            Id del animal
	 * @param raza
	 *            Raza del animal
	 * @param fecha_nacimiento
	 *            Fecha de nacimiento
	 * @param id_madre
	 *            Id de la madre ddel animal
	 * @param id_usuario
	 *            Id del usuario del animal
	 * @param sexo
	 *            Sexo del animal
	 * @param bitmapdata
	 *            Foto del animal
	 * */
	public Vaca(String id_vaca, String raza, Date fecha_nacimiento,
			String id_madre, String id_usuario, String sexo, String bitmapdata) {
		setId_vaca(id_vaca);
		setRaza(raza);
		setFecha_nacimiento(fecha_nacimiento);
		setId_madre(id_madre);
		setFoto(bitmapdata);
		setId_usuario(id_usuario);
		setSexo(sexo);
	}

	/**
	 * Devuelve el id del animal
	 * 
	 * @return String Id del animal
	 * */
	public String getId_vaca() {
		return id_vaca;
	}

	/**
	 * Guarda el id del animal
	 * 
	 * @param id_vaca
	 *            Id del animal
	 * */
	public void setId_vaca(String id_vaca) {
		this.id_vaca = id_vaca;
	}

	/**
	 * Devuelve la raza del animal
	 * 
	 * @return String Raza del animal
	 * */
	public String getRaza() {
		return raza;
	}

	/**
	 * Guarda la raza del animal
	 * 
	 * @param raza
	 *            Raza del animal
	 * */
	public void setRaza(String raza) {
		this.raza = raza;
	}

	/**
	 * Devuelve la fecha de nacimiento
	 * 
	 * @return Date Fecha de nacimiento
	 * */
	public Date getFecha_nacimiento() {
		return fecha_nacimiento;
	}

	/**
	 * Guarda la fecha de nacimiento del animal
	 * 
	 * @param fecha_nacimiento
	 *            Guarda la fecha del nacimiento del animal
	 * */
	public void setFecha_nacimiento(Date fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
	}

	/**
	 * Devuelve el id de la madre del animal
	 * 
	 * @return String Id de la madre del animal
	 * */
	public String getId_madre() {
		return id_madre;
	}

	/**
	 * Guarda el id de la madre del animal
	 * 
	 * @param id_madre
	 *            Id de la madre del animal
	 * */
	public void setId_madre(String id_madre) {
		this.id_madre = id_madre;
	}

	/**
	 * Devuelve la foto del animal
	 * 
	 * @return byte[] Foto del animal
	 * */
	public String getFoto() {
		return foto;
	}

	/**
	 * Guarda la foto del animal
	 * 
	 * @param bitmapdata
	 *            Foto del animal
	 * */
	public void setFoto(String bitmapdata) {
		this.foto = bitmapdata;
	}

	/**
	 * Devuelve el id del usuario del animal
	 * 
	 * @return String Id del usuario
	 * */
	public String getId_usuario() {
		return id_usuario;
	}

	/**
	 * Guarda el id del usuario del animal
	 * 
	 * @param id_usuario
	 *            Usuario del animal
	 * */
	public void setId_usuario(String id_usuario) {
		this.id_usuario = id_usuario;
	}

	/**
	 * Devuelve el sexo del animal
	 * 
	 * @return String Sexo del animal
	 * */
	public String getSexo() {
		return sexo;
	}

	/**
	 * Guarda el sexo del animal
	 * 
	 * @param sexo
	 *            Sexo del animal
	 * */
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
}