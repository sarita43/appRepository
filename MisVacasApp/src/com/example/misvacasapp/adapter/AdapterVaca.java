package com.example.misvacasapp.adapter;

import java.util.ArrayList;

import com.example.misvacasapp.R;
import com.example.misvacasapp.modelo.Vaca;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterVaca extends BaseAdapter{
	
	private Activity activity;
	private ArrayList<Vaca> lista;

	public AdapterVaca(Activity activity, ArrayList<Vaca> lista){
		this.activity = activity;
		this.lista = lista;
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
 
        return v;
	}

}
