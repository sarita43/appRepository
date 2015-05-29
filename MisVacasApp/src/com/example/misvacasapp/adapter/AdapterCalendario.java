package com.example.misvacasapp.adapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.example.misvacasapp.R;
import com.example.misvacasapp.calendario.CalendarioReproducionesVista;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdapterCalendario extends BaseAdapter {

	private Activity activity;
	private ArrayList<String> diasSemana = new ArrayList<String>();
	private String[] meses = { "Enero", "Febrero", "Marzo", "Abril", "Mayo",
			"Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre",
			"Diciembre" };
	private int diasMaximo;
	private int primerDia;

	public AdapterCalendario(Activity activity,int dia,int mes,int año) {
		this.activity = activity;
		Calendar c = Calendar.getInstance();
		diasMaximo = c.getActualMaximum(c.DAY_OF_MONTH);
		System.out.println(primerDia+"   "+diasMaximo);
		diasSemana.add("L");
		diasSemana.add("M");
		diasSemana.add("X");
		diasSemana.add("J");
		diasSemana.add("V");
		diasSemana.add("S");
		diasSemana.add("D");
		
		for (int i = 7; i < diasMaximo+7; i++) {
			diasSemana.add(Integer.toString(i+1-7));
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return diasSemana.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (convertView == null) {
			LayoutInflater inf = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inf.inflate(R.layout.dia_calendario, null);
		}
		TextView dia = (TextView) v.findViewById(R.id.dia_text);
		dia.setText(diasSemana.get(position));
		if(position==5){
		dia.setBackgroundColor(Color.BLUE);}
		return v;
	}
}
