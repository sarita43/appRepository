package com.example.misvacasapp.modelo;

public class Usuario {

	private String nombre;
	private String apellido1;
	private String apellido2;
	private String direccion;
	private String poblacion;
	private int telefono;
	private String dni;
	private String contraseña;
	private int rol;

	public Usuario() {

	}

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
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public int getRol() {
		return rol;
	}

	public void setRol(int rol) {
		this.rol = rol;
	}
}
