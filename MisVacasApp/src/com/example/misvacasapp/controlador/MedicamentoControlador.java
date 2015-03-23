package com.example.misvacasapp.controlador;

import java.util.ArrayList;

import com.example.misvacasapp.controlador.modelo.llamadaWS.LlamadaMedicamentoWS;
import com.example.misvacasapp.modelo.Medicamento;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class MedicamentoControlador {
	
	private ArrayList<Medicamento> listaMedicamentos;
	private Medicamento medicamento;
	
	public MedicamentoControlador(){
		listaMedicamentos = new ArrayList<Medicamento>();
		medicamento = new Medicamento();
	}
	
	public ArrayList<Medicamento> listaMedicamentos(final String id_vaca){
		Thread hilo = new Thread() {
			String res = "";
			Gson json = new GsonBuilder().setPrettyPrinting()
					.setDateFormat("dd-MM-yyyy").create();
			LlamadaMedicamentoWS llamada = new LlamadaMedicamentoWS();

			public void run() {
				res = llamada.LlamadaListaMedicamentos(id_vaca);
				listaMedicamentos = json.fromJson(res,
						new TypeToken<ArrayList<Medicamento>>() {
						}.getType());
			}
		};
		hilo.start();
		return listaMedicamentos;
	}
	
	public void eliminarMedicamento(final int id_medicamento,final String id_vaca) {
		Thread hilo = new Thread() {
			LlamadaMedicamentoWS llamada = new LlamadaMedicamentoWS();

			public void run() {
				llamada.LLamadaEliminarMedicamento(
						Integer.toString(id_medicamento), id_vaca);

			}
		};
		hilo.start();
	}

	public Medicamento getMedicamento(final String id_vaca,final String id_medicamento){
		Thread hilo = new Thread() {
			String res = "";
			Gson json = new GsonBuilder().setPrettyPrinting()
					.setDateFormat("dd-MM-yyyy").create();
			LlamadaMedicamentoWS llamada = new LlamadaMedicamentoWS();
			public void run() {
				res = llamada.LlamadaMedicamento(id_vaca, id_medicamento);
				medicamento = json.fromJson(res, Medicamento.class);
			}
		};
		hilo.start();
		return medicamento;
	}
}
