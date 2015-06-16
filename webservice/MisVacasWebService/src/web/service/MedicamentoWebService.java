package web.service;

import basedatos.Medicamento;

public class MedicamentoWebService {
	
	/**
	 * Método que devuelve la lista de medicamentos como String.
	 * 
	 * @param id_vaca
	 *            Id de la vaca
	 * @return String Lista de medicamentos como String
	 * */
	public String listaMedicamentos(String id_vaca) {
		return new Medicamento().listaMedicamentoString(id_vaca);
	}

	/**
	 * Método que devuelve el medicamento que hay en la base de datos como
	 * String.
	 * 
	 * @param id_vaca
	 *            Id del animal
	 * @param id_medicamento
	 *            Id del medicamento
	 * @return String Medicamento como String
	 * */
	public String Medicamento(String id_vaca, String id_medicamento) {
		return new Medicamento().medicamentoString(id_vaca, id_medicamento);
	}

	/**
	 * Método que añade un medicamento que se pasa por parametro como String.
	 * 
	 * @param medicamento
	 *            Medicamento como String
	 * */
	public void añadirMedicamento(String medicamento) {
		new Medicamento().añadirMedicamento(medicamento);
	}

	/**
	 * Elimina el medicamento.
	 * 
	 * @param id_medicamento
	 *            Id del medicamento
	 * @param id_vaca
	 *            Id de la vaca
	 * */
	public void eliminarMedicamento(String id_medicamento, String id_vaca) {
		new Medicamento().eliminarMedicamento(id_medicamento, id_vaca);
	}
	
	/**
	 * Método que elimina todos los medicamentos de un animal
	 * @param id_vaca
	 */
	public void eliminarMedicamentos(String id_vaca){
		new Medicamento().eliminarMedicamentos(id_vaca);
	}
}
