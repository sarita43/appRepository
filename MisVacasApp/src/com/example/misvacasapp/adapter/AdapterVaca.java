package com.example.misvacasapp.adapter;

import java.util.ArrayList;

import com.example.misvacasapp.R;
import com.example.misvacasapp.TableSeleccionado;
import com.example.misvacasapp.modelo.Vaca;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterVaca extends BaseAdapter{
	
	private Activity activity;
	private ArrayList<Vaca> lista;
	private TableSeleccionado seleccionado;

	public AdapterVaca(Activity activity, ArrayList<Vaca> lista,TableSeleccionado seleccionado){
		this.activity = activity;
		this.lista = lista;
		setSeleccionado(seleccionado);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.lista.size();
	}

	@Override
	public Vaca getItem(int position) {
		// TODO Auto-generated method stub
		return this.lista.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Generamos una convertView por motivos de eficiencia
        View v = convertView;
 
        //Asociamos el layout de la lista que hemos creado
        if(convertView == null){
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.lista_vacas, null);
        }

        Vaca vaca = this.lista.get(position);
        
        ImageView imagen = (ImageView) v.findViewById(R.id.imagenVaca);
       
        TextView cargo = (TextView) v.findViewById(R.id.idVaca_texto);
        cargo.setText("Id: "+vaca.getId_vaca());
        v.setBackgroundResource(!getSeleccionado().getTable().get(position)?R.drawable.abc_item_background_holo_dark:R.drawable.abc_list_selector_disabled_holo_dark);
        return v;
	}

	public TableSeleccionado getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(TableSeleccionado seleccionado) {
		this.seleccionado = seleccionado;
	}

}
