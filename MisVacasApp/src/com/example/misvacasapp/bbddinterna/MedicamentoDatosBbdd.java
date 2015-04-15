package com.example.misvacasapp.bbddinterna;

public class MedicamentoDatosBbdd {

	public static String tablaMedicamentos = "create table medicamento (id_medicamneto varchar(15) primary key not null," +
			"fecha date,tipo varchar(30),descripcion varchar(200),id_vaca varchar(15)";;

}
