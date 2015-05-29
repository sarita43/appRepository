package web.service;

import basedatos.Produccion;

public class ProduccionWebService {

	public String getProducciones(String id_usuario){
		return new Produccion().getProducciones(id_usuario);
	}
	
	public void setProducciones(String listaProducciones,String id_usuario){
		new Produccion().setProducciones(listaProducciones,id_usuario);
	}
}
