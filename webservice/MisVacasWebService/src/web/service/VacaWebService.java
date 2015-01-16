package web.service;

import java.util.Date;

import basedatos.*;

public class VacaWebService {
	
	public String Vaca(String id_vaca,String id_usuario){
		return new Vaca().vacaString(id_vaca,id_usuario);
	}
	
	public String listaVacas(String id_usuario){
		return new Vaca().listaVacasString(id_usuario);
	}
	
	public void añadirVaca(String id_vaca, String raza, Date fecha_nacimiento,
			String id_madre, String foto, String id_usuario) {
		new Vaca().añadirVaca(id_vaca, raza, fecha_nacimiento, id_madre, foto, id_usuario);
	}
	
	public void eliminarVaca(String id_vaca, String id_usuario) {
		new Vaca().eliminarVaca(id_vaca, id_usuario);
	}
}
