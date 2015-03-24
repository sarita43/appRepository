package com.example.misvacasapp.controlador;

import java.util.ArrayList;

import com.example.misvacasapp.controlador.modelo.iterator.AgregadoVaca;
import com.example.misvacasapp.controlador.modelo.iterator.IteratorListaVaca;
import com.example.misvacasapp.controlador.modelo.llamadaWS.LlamadaVacaWS;
import com.example.misvacasapp.modelo.Vaca;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class VacaControlador {

	private ArrayList<Vaca> lista;
	private Vaca vaca;

	public VacaControlador() {
		lista = new ArrayList<Vaca>();
		vaca = new Vaca();
	}

	public ArrayList<Vaca> listaVacas(final String id_usuario) {
		Thread hilo = new Thread() {
			String res = "";
			Gson json = new GsonBuilder().setPrettyPrinting()
					.setDateFormat("dd-MM-yyyy").create();
			LlamadaVacaWS llamada = new LlamadaVacaWS();

			public void run() {
				res = llamada.LlamadaListaVacas(id_usuario);
				lista = json.fromJson(res, new TypeToken<ArrayList<Vaca>>() {
				}.getType());
			}
		};
		hilo.start();
		return lista;
	}

	public void eliminarVaca(final String id_vaca, final String id_usuario) {
		Thread hilo = new Thread() {
			LlamadaVacaWS llamada = new LlamadaVacaWS();

			public void run() {
				// TODO ELIMINAR LOS MEDICAMENTOS
				llamada.LLamadaEliminarVaca(id_vaca, id_usuario);
			}
		};
		hilo.start();
	}

	public void añadirVaca(final Vaca vaca) {
		Thread hilo = new Thread() {
			Gson json;
			LlamadaVacaWS llamada = new LlamadaVacaWS();

			public void run() {
				String v = json.toJson(vaca);
				llamada.LLamadaAñadirVaca(v);
			}
		};
		hilo.start();
	}
	
	public boolean vacaExistente(String id_vaca, String id_usuario){
		boolean resultado = false;
		lista = listaVacas(id_usuario);
		AgregadoVaca agregado = new AgregadoVaca();
		IteratorListaVaca i = new IteratorListaVaca(agregado);
		while (i.hasNext()) {
			if(i.actualElement().getId_vaca().equals(id_vaca)){
				resultado = true;
			}
		}
		return resultado;
	}
	
	public Vaca getVaca(final String id_vaca,final String id_usuario){
		Thread hilo = new Thread() {
			String res = "";
			Gson json = new GsonBuilder().setPrettyPrinting()
					.setDateFormat("dd-MM-yyyy").create();
			LlamadaVacaWS llamada = new LlamadaVacaWS();
			
			public void run() {
				res = llamada.LlamadaVaca(id_vaca, id_usuario);
				vaca = json.fromJson(res, Vaca.class);
			}
		};
		hilo.start();
		return vaca;
	}

}
