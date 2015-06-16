package com.example.misvacasapp.adapter;

import java.util.ArrayList;
import com.example.misvacasapp.R;
import com.example.misvacasapp.singleton.TableSeleccionado;
import com.example.misvacasapp.modelo.Vaca;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
 * @author Sara Mart�nez L�pez
 * */
public class AdapterVaca extends BaseAdapter implements Adapter{
	// -----------------------------Atributos-------------------------------//
	/** Actividad donde se va a mostrar el adapter */
	private Activity activity;
	/** Lista para mostrar el menu */
	private ArrayList<Vaca> lista;
	/**
	 * Tabla hash que indica si el item esta seleccionado o no y si hay que
	 * seleccionarlo o no
	 */
	private TableSeleccionado seleccionado;

	// ------------------------------M�todos----------------------------------//
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
	 * Devuelve el tama�o de la lista
	 * 
	 * @return int Tama�o de la lista
	 * */
	@Override
	public int getCount() {
		return this.lista.size();
	}

	/**
	 * Devuelve el item de una posici�n
	 * 
	 * @param position
	 *            Posici�n de la lista
	 * @return String Item de la posicion position
	 * */
	@Override
	public Vaca getItem(int position) {
		return this.lista.get(position);
	}

	/**
	 * Devuelve un id del item M�todo inutilizado
	 * 
	 * @param position
	 *            Posici�n del item
	 * @return long Devuelve 0
	 * */
	@Override
	public long getItemId(int position) {
		return 0;
	}

	/**
	 * Devuelve la construcci�n de la vista que se va a mostrar en el menu
	 * 
	 * @param position
	 * @param convertView
	 * @param parent
	 * @return View Vista del menu
	 * */
	@SuppressLint("InflateParams")
	@SuppressWarnings("deprecation")
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
		ImageView imagen = (ImageView) v.findViewById(R.id.imagenVaca);
		byte[] decodedString = Base64.decode(vaca.getFoto(), Base64.DEFAULT);
		Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,
				decodedString.length);
		Drawable i = new BitmapDrawable(decodedByte);
		imagen.setBackground(i);
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