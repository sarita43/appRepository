package web.service;

import basedatos.*;

public class VacaWebService {
	
	public String Vaca(String id_vaca,String id_usuario){
		return new Vaca().vacaString(id_vaca,id_usuario);
	}
	
	public String listaVacas(String id_usuario){
		return new Vaca().listaVacasString(id_usuario);
	}
	
	public void a�adirVaca(String vaca) {
		new Vaca().a�adirVaca(vaca);
	}
	
	public void eliminarVaca(String id_vaca, String id_usuario) {
		new Vaca().eliminarVaca(id_vaca, id_usuario);
	}
	
}
