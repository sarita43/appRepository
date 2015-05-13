package com.example.misvacasapp.adapter;

import java.util.ArrayList;
import com.example.misvacasapp.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Clase adaptador de la lista de los menus
 * 
 * @see BaseAdapter
 * @author Sara Martinez Lopez
 * */
public class AdapterListaMenu extends BaseAdapter {
	//------------------------Atributos--------------------------------//
	/** Lista para mostrar el menu */
	private ArrayList<String> lista = new ArrayList<String>();
	/** Actividad donde se va a mostrar el adapter */
	private Activity activity;

	//------------------------Métodos----------------------------------//
	/**
	 * Constructor
	 * 
	 * @param activity
	 *            Actividad donde se va a mostrar el adapter
	 * @param lista
	 *            Lista para mostrar el menu
	 * */
	public AdapterListaMenu(Activity activity, ArrayList<String> lista) {
		this.lista = lista;
		this.activity = activity;
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
	public String getItem(int position) {
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
	 * Devuelve la construcción de la vista que se va a mostrar en el menu
	 * 
	 * @param position
	 * @param convertView
	 * @param parent
	 * @return View Vista del menu
	 * */
	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		// Asociamos el layout de la lista que hemos creado
		if (convertView == null) {
			LayoutInflater inf = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inf.inflate(R.layout.lista_menu, null);
		}
		String string = this.getItem(position);
		TextView item = (TextView) v.findViewById(R.id.itemMenu);
		item.setText(string);
		return v;
	}
}