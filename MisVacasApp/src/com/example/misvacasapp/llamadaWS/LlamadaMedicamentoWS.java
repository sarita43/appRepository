package com.example.misvacasapp.llamadaWS;

import java.io.IOException;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

/**
 * Clase que hace las llamadas al servicio web de los medicamentos
 * 
 * @author Sara Martinez Lopez
 * */
public class LlamadaMedicamentoWS {
	// Atributos
	/** Nombre del espacio de nombres */
	private static final String NAMESPACE = "http://service.web";
	/** URL donde se encuentra el servicio web */
	 private static String URL =
	 "http://192.168.1.36:8090/axis2/services/MedicamentoWebService?wsdl";
	//private static String URL = "http://81.172.100.105:8090/axis2/services/MedicamentoWebService?wsdl";
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
	 * Método que devuelve el medicamento de una vaca (como String). El
	 * medicamento que devuelve tiene que poder ser deserializado con
	 * json.formJson Comprueba que el usuario y la contraseña coinciden
	 * 
	 * @param usuario
	 *            Id del usuario
	 * @param contraseña
	 *            Contraseña del usuario
	 * @return String Usuario
	 * */
	public String LlamadaMedicamento(String id_vaca, String id_medicamento) {
		String res = "";
		METHOD_NAME = "Medicamento";
		SOAP_ACTION = "urn:Medicamento";
		request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("id_vaca", id_vaca);
		request.addProperty("id_medicamento", id_medicamento);
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
	 * Método que devuelve la lista de medicamentos (como String) que tiene una
	 * vaca El String de la lista que devuelve puede ser deserializada con
	 * json.fromJson
	 * 
	 * @param id_vaca
	 *            Id de la vaca
	 * @return String Lista de medicamentos
	 * */
	public String LlamadaListaMedicamentos(String id_vaca) {
		String res = "";
		METHOD_NAME = "listaMedicamentos";
		SOAP_ACTION = "urn:listaMedicamentos";
		request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("id_vaca", id_vaca);
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
	 * Método que añade un medicamento (como String) a una vaca El medicamento
	 * tiene que ser un tipo String que pueda ser deserializado con
	 * json.fronJson a un tipo Medicamento
	 * 
	 * @medicamento Tipo string del medicamento
	 * */
	public void LLamadaAñadirMedicamento(String medicamento) {
		METHOD_NAME = "añadirMedicamento";
		SOAP_ACTION = "urn:añadirMedicamento";
		request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("medicamento", medicamento);
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
	 * Método que elimina un medicamento de la lista de medicamentos de la vaca
	 * 
	 * @param id_medicamento
	 *            Id del medicamento
	 * @param id_vaca
	 *            Id de la vaca
	 * */
	public void LLamadaEliminarMedicamento(String id_medicamento, String id_vaca) {
		METHOD_NAME = "eliminarMedicamento";
		SOAP_ACTION = "urn:eliminarMedicamento";
		request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("id_medicamento", id_medicamento);
		request.addProperty("id_vaca", id_vaca);
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
	 * Método que devuelve la lista de ids de medicamentos(como String) de una
	 * vaca
	 * 
	 * @param id_vaca
	 *            Id de la vaca
	 * @return String Lista de ids
	 * */
	public String LLamadaGetId_Medicamentos(String id_vaca) {
		String resultado = "";
		METHOD_NAME = "getId_medicamentos";
		SOAP_ACTION = "urn:getId_medicamentos";
		request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("id_vaca", id_vaca);
		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = false;
		envelope.setOutputSoapObject(request);
		HttpTransportSE transporte = new HttpTransportSE(URL);
		try {
			transporte.call(SOAP_ACTION, envelope);
			resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();
			resultado = resultsRequestSOAP.toString();
		} catch (IOException | XmlPullParserException e) {
			e.printStackTrace();
		}
		return resultado;
	}

	/**
	 * Método que elimina todos los medicamentos de un animal
	 * 
	 * @param id_vaca
	 */
	public void LLamadaEliminarMedicamentos(String id_vaca) {
		
		METHOD_NAME = "eliminarMedicamentos";
		SOAP_ACTION = "urn:eliminarMedicamentos";
		request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("id_vaca", id_vaca);
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