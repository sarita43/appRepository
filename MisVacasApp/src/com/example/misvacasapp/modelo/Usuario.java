package com.example.misvacasapp.modelo;

/**
 * Clase usuario
 * 
 * @author Sara Martinez Lopez
 * */
public class Usuario {
	// Atributos
	/** Nombre del usuario */
	private String nombre;
	/** Primer apellido */
	private String apellido1;
	/** Segundo apellido */
	private String apellido2;
	/** Dirección del usuario */
	private String direccion;
	/** Población del usuario */
	private String poblacion;
	/** Teléfono del usuario */
	private int telefono;
	/** Dni o id del usuario */
	private String dni;
	/** Contraseña del usuario */
	private String contraseña;
	/** Rol que tiene el usuario. Usuario o administrador */
	private int rol;

	// Métodos
	/** Constructor del usuario sin atributos */
	public Usuario() {
	}

	/**
	 * Constructor del usuario con atributos
	 * 
	 * @param nombre
	 *            Nombre del usuario
	 * @param apellido1
	 *            Primer apellido del usuario
	 * @param apellido2
	 *            Segundo apellido del usuario
	 * @param direccion
	 *            Dirección del usuario
	 * @param poblacion
	 *            Población del usuario
	 * @param telefono
	 *            Teléfono del usuario
	 * @param dni
	 *            DNI o Id del usuario
	 * @param contraseña
	 *            Contraseña del usuario
	 * @param rol
	 *            Rol que tiene el usuario. Usuario o administrador
	 * */
	public Usuario(String nombre, String apellido1, String apellido2,
			String direccion, String poblacion, String telefono, String dni,
			String contraseña, int rol) {
		setNombre(nombre);
		setApellido1(apellido1);
		setApellido2(apellido2);
		setDireccion(direccion);
		setPoblacion(poblacion);
		if (telefono != null) {
			setTelefono(Integer.parseInt(telefono));
		} else {
			setTelefono(0);
		}
		setDni(dni);
		setContraseña(contraseña);
		setRol(rol);
	}

	/**
	 * Devuelve el nombre del usuario
	 * 
	 * @return String nombre
	 * */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Guarda el nombre del usuario
	 * 
	 * @param nombre
	 *            Nombre del usuario
	 * */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Devuelve el primer apellido
	 * 
	 * @return String Primer apellido
	 * */
	public String getApellido1() {
		return apellido1;
	}

	/**
	 * Guarda el primero apellido
	 * 
	 * @param apellido1
	 *            Primer apellido
	 * */
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	/**
	 * Devuelve el segundo apellido
	 * 
	 * @param String
	 *            Segundo apellido
	 * */
	public String getApellido2() {
		return apellido2;
	}

	/**
	 * Guarda el segundo apellido
	 * 
	 * @param apellido2
	 *            Segundo apellido
	 * */
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	/**
	 * Devuelve la dirección del usuario
	 * 
	 * @return String Dirección del ususario
	 * */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * Guarda la dirección del usuario
	 * 
	 * @param direccion
	 *            Dirección del usuario
	 * */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * Devuelve la población del usuario
	 * 
	 * @return String Poblacion del usuario
	 * */
	public String getPoblacion() {
		return poblacion;
	}

	/**
	 * Guarda la población
	 * 
	 * @param población
	 * */
	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}

	/**
	 * Devuelve el teléfono
	 * 
	 * @return int Teléfono del usuario
	 * */
	public int getTelefono() {
		return telefono;
	}

	/**
	 * Guarda el teléfono
	 * 
	 * @param telefono
	 *            Telefono del usuario
	 * */
	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	/**
	 * Devuelve el dni o id del usuario
	 * 
	 * @return String Dni o id del usuario
	 * */
	public String getDni() {
		return dni;
	}

	/**
	 * Guarda el dni o id del usuario
	 * 
	 * @param dni
	 *            Dni o Id del usuario
	 * */
	public void setDni(String dni) {
		this.dni = dni;
	}

	/**
	 * Devuelve la contraseña del usuario
	 * 
	 * @return String Contraseña del usuario
	 * */
	public String getContraseña() {
		return contraseña;
	}

	/**
	 * Guarda la contraseña del usuario
	 * 
	 * @param contraseña
	 *            Contraseña del usuario
	 * */
	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	/**
	 * Devuelve el rol del usuario
	 * 
	 * @return int Rol del usuario
	 * */
	public int getRol() {
		return rol;
	}

	/**
	 * Guarda el rol del usuario
	 * 
	 * @param rol
	 *            Rol del usuario
	 * */
	public void setRol(int rol) {
		this.rol = rol;
	}
}