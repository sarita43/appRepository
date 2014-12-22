package com.example.misvacasapp.adapter;

import java.util.ArrayList;

import com.example.misvacasapp.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdapterListaMenu extends BaseAdapter{
	
	private ArrayList<String> lista = new ArrayList<String>();
	private Activity activity;
	
	public AdapterListaMenu(Activity activity, ArrayList<String> lista){
		this.lista = lista;
		this.activity = activity;
	}
	
	@Override
	public int getCount() {
		return this.lista.size();
	}

	@Override
	public String getItem(int position) {
		return this.lista.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		 
        //Asociamos el layout de la lista que hemos creado
        if(convertView == null){
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.lista_menu, null);
        }
        
        String string = this.getItem(position);
        
        TextView item = (TextView)v.findViewById(R.id.itemMenu);
        item.setText(string);
        
        return v;
	}
}
