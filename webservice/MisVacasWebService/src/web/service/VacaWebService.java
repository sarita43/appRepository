package web.service;

import basedatos.*;

public class VacaWebService {
	/**
	 * M�todo que devuelve el animal como String.
	 * 
	 * @param id_vaca
	 *            Id del animal
	 * @param id_usuario
	 *            DNI del usuario o Id del usuario
	 * @return String Vaca como String
	 * */
	public String Vaca(String id_vaca, String id_usuario) {
		return new Vaca().vacaString(id_vaca, id_usuario);
	}

	/**
	 * M�todo que devuelve la lista de animales como String.
	 * 
	 * @param id_vaca
	 *            Id de la vaca
	 * @return String Lista de medicamentos como String
	 * */
	public String listaVacas(String id_usuario) {
		return new Vaca().listaVacasString(id_usuario);
	}
	
	public String listaVacasBaseDatos(){
		return new Vaca().listaVacasString();
	}

	/**
	 * M�todo que a�ade un animal que se pasa por parametro como String.
	 * 
	 * @param vaca
	 *            Vaca que como String para deserializar
	 * */
	public void a�adirVaca(String vaca) {
		new Vaca().a�adirVaca(vaca);
	}

	/**
	 * M�todo que elimina un animal
	 * 
	 * @param id_vaca
	 *            Id de la vaca
	 * @param id_usuario
	 *            Id del usuario
	 * */
	public void eliminarVaca(String id_vaca, String id_usuario) {
		new Vaca().eliminarVaca(id_vaca, id_usuario);
	}
}