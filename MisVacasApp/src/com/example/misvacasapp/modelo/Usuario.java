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
	/** Direcci�n del usuario */
	private String direccion;
	/** Poblaci�n del usuario */
	private String poblacion;
	/** Tel�fono del usuario */
	private int telefono;
	/** Dni o id del usuario */
	private String dni;
	/** Contrase�a del usuario */
	private String contrase�a;
	/** Rol que tiene el usuario. Usuario o administrador */
	private int rol;

	// M�todos
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
	 *            Direcci�n del usuario
	 * @param poblacion
	 *            Poblaci�n del usuario
	 * @param telefono
	 *            Tel�fono del usuario
	 * @param dni
	 *            DNI o Id del usuario
	 * @param contrase�a
	 *            Contrase�a del usuario
	 * @param rol
	 *            Rol que tiene el usuario. Usuario o administrador
	 * */
	public Usuario(String nombre, String apellido1, String apellido2,
			String direccion, String poblacion, String telefono, String dni,
			String contrase�a, int rol) {
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
		setContrase�a(contrase�a);
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
	 * Devuelve la direcci�n del usuario
	 * 
	 * @return String Direcci�n del ususario
	 * */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * Guarda la direcci�n del usuario
	 * 
	 * @param direccion
	 *            Direcci�n del usuario
	 * */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * Devuelve la poblaci�n del usuario
	 * 
	 * @return String Poblacion del usuario
	 * */
	public String getPoblacion() {
		return poblacion;
	}

	/**
	 * Guarda la poblaci�n
	 * 
	 * @param poblaci�n
	 * */
	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}

	/**
	 * Devuelve el tel�fono
	 * 
	 * @return int Tel�fono del usuario
	 * */
	public int getTelefono() {
		return telefono;
	}

	/**
	 * Guarda el tel�fono
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
	 * Devuelve la contrase�a del usuario
	 * 
	 * @return String Contrase�a del usuario
	 * */
	public String getContrase�a() {
		return contrase�a;
	}

	/**
	 * Guarda la contrase�a del usuario
	 * 
	 * @param contrase�a
	 *            Contrase�a del usuario
	 * */
	public void setContrase�a(String contrase�a) {
		this.contrase�a = contrase�a;
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