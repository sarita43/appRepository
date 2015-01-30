package web.service;

import basedatos.Medicamento;

public class MedicamentoWebService {
	
	public String listaMedicamentos(String id_vaca){
		return new Medicamento().listaMedicamentoString(id_vaca);
	}
	
	public String Medicamento(String id_vaca,String id_medicamento){
		return new Medicamento().medicamentoString(id_vaca, id_medicamento);
	}
	
	public void añadirMedicamento(String medicamento) {
		new Medicamento().añadirMedicamento(medicamento);
	}
	public void eliminarMedicamento(String id_medicamento, String id_vaca) {
		new Medicamento().eliminarMedicamento(id_medicamento, id_vaca);
	}
	
}
