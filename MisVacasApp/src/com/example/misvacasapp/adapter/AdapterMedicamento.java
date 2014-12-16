package com.example.misvacasapp.adapter;

import java.util.ArrayList;

import com.example.misvacasapp.R;
import com.example.misvacasapp.modelo.Medicamento;
import com.example.misvacasapp.modelo.Vaca;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterMedicamento extends BaseAdapter{

	private Activity activity;
	private ArrayList<Medicamento> lista;

	public AdapterMedicamento(Activity activity, ArrayList<Medicamento> lista){
		this.activity = activity;
		this.lista = lista;
	}
	
	@Override
	public int getCount() {
		return this.lista.size();
	}

	@Override
	public Medicamento getItem(int position) {
		return this.lista.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = convertView;
		 
        //Asociamos el layout de la lista que hemos creado
        if(convertView == null){
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.lista_medicamentos, null);
        }
 
    
        Medicamento medicamento = this.lista.get(position);
        
        TextView tipo = (TextView) v.findViewById(R.id.tipoTexto);
        tipo.setText("Tipo: "+medicamento.getTipo());
       
        TextView fecha = (TextView) v.findViewById(R.id.fechaTexto);
        fecha.setText("	Fecha: "+medicamento.getId_vaca());
 
        return v;
	}

}
