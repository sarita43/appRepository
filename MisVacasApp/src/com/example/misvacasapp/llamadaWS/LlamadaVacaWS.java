package com.example.misvacasapp.llamadaWS;

import java.io.IOException;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

/**
 * Clase que hace las llamadas al servicio web de las vacas
 * 
 * @author Sara Martinez Lopez
 * */
public class LlamadaVacaWS {
	// Atributos
	/** Nombre del espacio de nombres */
	private static final String NAMESPACE = "http://service.web";
	/** URL donde se encuentra el servicio web */
	// private static String URL =
	// "http://10.0.2.2:8090/axis2/services/VacaWebService?wsdl";
	private static String URL = "http://81.172.100.105:8090/axis2/services/VacaWebService?wsdl";
	/** Nombre del método */
	private static String METHOD_NAME;
	/** SOAP Action */
	private static String SOAP_ACTION;
	/** Solicitud del objeto soap */
	private static SoapObject request = null;
	/** Contenedor soap */
	private static SoapSerializationEnvelope envelope = null;
	/** Resultado de la llamada */
	private static SoapPrimitive resultsRequestSOAP = null;

	// Métodos
	/**
	 * Método que devuelve la lista de vacas (como String) que tiene un usuario
	 * El String que devuelve se puede deserializar con json.fromJson Si el
	 * usuario no tiene ninguna vaca el resultado es la lista con una vaca
	 * dentro con id=0
	 * 
	 * @param usuario
	 *            Id del usuario
	 * @return String Lista de vacas
	 * */
	public String LlamadaListaVacas(String usuario) {
		String res = "";
		METHOD_NAME = "listaVacas";
		SOAP_ACTION = "urn:listaVacas";
		request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("id_usuario", usuario);
		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = false;
		envelope.setOutputSoapObject(request);
		HttpTransportSE transporte = new HttpTransportSE(URL);
		try {
			transporte.call(SOAP_ACTION, envelope);
			resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();
			res = resultsRequestSOAP.toString();
		} catch (IOException | XmlPullParserException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public String LlamadaListaVacas() {
		String res = "";
		METHOD_NAME = "listaVacasBaseDatos";
		SOAP_ACTION = "urn:listaVacasBaseDatos";
		request = new SoapObject(NAMESPACE, METHOD_NAME);
		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = false;
		envelope.setOutputSoapObject(request);
		HttpTransportSE transporte = new HttpTransportSE(URL);
		try {
			transporte.call(SOAP_ACTION, envelope);
			resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();
			res = resultsRequestSOAP.toString();
		} catch (IOException | XmlPullParserException e) {
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * Método que devuelve una vaca de un usuario, sabiendo el id de la vaca y
	 * del usuario Devuelve la vaca como un String para que pueda ser
	 * deserializado con json.fromJson
	 * 
	 * @param id_vaca
	 *            Id de la vaca
	 * @param id_usuario
	 *            Id del usuario
	 * @return String Vaca de un usuario
	 * */
	public String LlamadaVaca(String id_vaca, String id_usuario) {
		String res = "";
		METHOD_NAME = "Vaca";
		SOAP_ACTION = "urn:Vaca";
		request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("id_vaca", id_vaca);
		request.addProperty("id_usuario", id_usuario);
		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = false;
		envelope.setOutputSoapObject(request);
		HttpTransportSE transporte = new HttpTransportSE(URL);
		try {
			transporte.call(SOAP_ACTION, envelope);
			resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();
			res = resultsRequestSOAP.toString();
		} catch (IOException | XmlPullParserException e) {
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * Método que añade una vaca a la lista de vacas del usuario La vaca tiene
	 * que ser un tipo String que pueda ser deserializado con json.fronJson a un
	 * tipo Vaca
	 * 
	 * @param vaca
	 *            Tipo string de una vaca
	 * */
	public void LLamadaAñadirVaca(String vaca) {
		METHOD_NAME = "añadirVaca";
		SOAP_ACTION = "urn:añadirVaca";
		request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("vaca", vaca);
		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = false;
		envelope.setOutputSoapObject(request);
		HttpTransportSE transporte = new HttpTransportSE(URL);
		try {
			transporte.call(SOAP_ACTION, envelope);
			resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();
		} catch (IOException | XmlPullParserException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método que elimina una vaca de la lista de vacas del usuario
	 * 
	 * @param id_vaca
	 *            Id de la vaca
	 * @param id_usuario
	 *            Id del usuario
	 * */
	public void LLamadaEliminarVaca(String id_vaca, String id_usuario) {
		METHOD_NAME = "eliminarVaca";
		SOAP_ACTION = "urn:eliminarVaca";
		request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("id_vaca", id_vaca);
		request.addProperty("id_usuario", id_usuario);
		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = false;
		envelope.setOutputSoapObject(request);
		HttpTransportSE transporte = new HttpTransportSE(URL);
		try {
			transporte.call(SOAP_ACTION, envelope);
			resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();
		} catch (IOException | XmlPullParserException e) {
			e.printStackTrace();
		}
	}
}