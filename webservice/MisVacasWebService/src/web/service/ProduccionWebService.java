package web.service;

import basedatos.Produccion;

public class ProduccionWebService {

	/**
	 * Devuelve la lista de producción que hay en la base de datos cloud como
	 * String
	 * 
	 * @param id_usuario
	 * @return String Lista de produccion
	 */
	public String getProducciones(String id_usuario){
		return new Produccion().getProducciones(id_usuario);
	}
	
	/**
	 * Guarda en la base de datos cloud la lista de producción que se pasa por
	 * parámetro como String
	 * 
	 * @param listaProducciones
	 *            String Lista de producción
	 * @param id_usuario
	 *            String id usuario
	 */
	public void setProducciones(String listaProducciones,String id_usuario){
		new Produccion().setProducciones(listaProducciones,id_usuario);
	}
}
