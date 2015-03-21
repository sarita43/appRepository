package com.example.misvacasapp.controlador.vista.adapter;

import java.util.ArrayList;
import com.example.misvacasapp.R;
import com.example.misvacasapp.controlador.vista.singleton.TableSeleccionado;
import com.example.misvacasapp.modelo.Vaca;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Clase adaptador de la lista de las vacas
 * 
 * @see BaseAdapter
 * @author Sara Martinez Lopez
 * */
public class AdapterVaca extends BaseAdapter {
	// Atributos
	/** Actividad donde se va a mostrar el adapter */
	private Activity activity;
	/** Lista para mostrar el menu */
	private ArrayList<Vaca> lista;
	/**
	 * Tabla hash que indica si el item esta seleccionado o no y si hay que
	 * seleccionarlo o no
	 */
	private TableSeleccionado seleccionado;

	// Métodos
	/**
	 * Constructor
	 * 
	 * @param activity
	 *            Actividad donde se va a mostrar el adapter
	 * @param lista
	 *            Lista para mostrar la lista de vacas
	 * @param seleccionado
	 *            Tabla hash que indica si el item esta seleccionado o no y si
	 *            hay que seleccionarlo o no
	 * */
	public AdapterVaca(Activity activity, ArrayList<Vaca> lista,
			TableSeleccionado seleccionado) {
		this.activity = activity;
		this.lista = lista;
		setSeleccionado(seleccionado);
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
	public Vaca getItem(int position) {
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
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (convertView == null) {
			LayoutInflater inf = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inf.inflate(R.layout.lista_vacas, null);
		}
		Vaca vaca = this.lista.get(position);
		TextView cargo = (TextView) v.findViewById(R.id.idVaca_texto);
		cargo.setText("Id: " + vaca.getId_vaca());
		ImageView imagen =(ImageView)v.findViewById(R.id.imagenVaca);
		byte[] decodedString = Base64.decode(vaca.getFoto(), Base64.DEFAULT);
		Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
		imagen.setImageBitmap(decodedByte);
		v.setBackgroundResource(!getSeleccionado().getTable().get(position) ? R.drawable.abc_item_background_holo_dark
				: R.drawable.abc_list_selector_disabled_holo_dark);
		return v;
	}

	/**
	 * Debuelve la tabla hash de los items seleccionados
	 * 
	 * @return seleccionado Tabla hash de items seleccionados
	 * */
	public TableSeleccionado getSeleccionado() {
		return seleccionado;
	}

	/**
	 * Guarda la tabla hash de los items seleccionados
	 * 
	 * @param seleccionado
	 *            Tabla del los items seleccionados
	 * */
	public void setSeleccionado(TableSeleccionado seleccionado) {
		this.seleccionado = seleccionado;
	}
}