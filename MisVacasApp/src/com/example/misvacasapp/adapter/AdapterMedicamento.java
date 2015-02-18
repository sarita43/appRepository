package com.example.misvacasapp.adapter;

import java.util.ArrayList;
import com.example.misvacasapp.R;
import com.example.misvacasapp.modelo.Medicamento;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Clase adaptador de la lista de los medicamentos
 * 
 * @see BaseAdapter
 * @author Sara Martinez Lopez
 * */
public class AdapterMedicamento extends BaseAdapter {
	// Atributos
	/** Actividad donde se va a mostrar el adapter */
	private Activity activity;
	/** Lista para mostrar el listado de medicamentos */
	private ArrayList<Medicamento> lista;

	/**
	 * Constructor
	 * 
	 * @param activity
	 *            Actividad donde se va a mostrar el adapter
	 * @param lista
	 *            Lista para mostrar la lista de medicamentos
	 * */
	public AdapterMedicamento(Activity activity, ArrayList<Medicamento> lista) {
		this.activity = activity;
		this.lista = lista;
	}

	/**
	 * Devuelve el tamaño de la lista
	 * 
	 * @return int Tamaño de la lista
	 * */
	@Override
	public int getCount() {
		return this.lista.size();
	}

	/**
	 * Devuelve el item de una posición
	 * 
	 * @param position
	 *            Posición de la lista
	 * @return String Item de la posicion position
	 * */
	@Override
	public Medicamento getItem(int position) {
		return this.lista.get(position);
	}

	/**
	 * Devuelve un id del item Método inutilizado
	 * 
	 * @param position
	 *            Posición del item
	 * @return long Devuelve 0
	 * */
	@Override
	public long getItemId(int position) {
		return 0;
	}

	/**
	 * Devuelve la construcción de la vista que se va a mostrar en la lista de
	 * medicamentos
	 * 
	 * @param position
	 * @param convertView
	 * @param parent
	 * @return View Vista de la lista de medicamentos
	 * */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		// Asociamos el layout de la lista que hemos creado
		if (convertView == null) {
			LayoutInflater inf = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inf.inflate(R.layout.lista_medicamentos, null);
		}
		Medicamento medicamento = this.lista.get(position);
		TextView tipo = (TextView) v.findViewById(R.id.tipoTexto);
		tipo.setText("Tipo: " + medicamento.getTipo());
		TextView fecha = (TextView) v.findViewById(R.id.fechaTexto);
		fecha.setText("	Fecha: " + medicamento.getFecha());
		return v;
	}
}