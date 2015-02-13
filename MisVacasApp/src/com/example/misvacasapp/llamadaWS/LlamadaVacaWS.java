package com.example.misvacasapp.llamadaWS;

import java.io.IOException;
import java.util.Date;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

/**
 * Clase que hace las llamadas al servicio web de vaca
 * 
 * @author Sara Martinez Lopez
 * */
public class LlamadaVacaWS {

	//Atributos
	/** Nombre del espacio de nombres*/
	private static final String NAMESPACE = "http://service.web";
	/**URL donde se encuentra el servicio web*/
//	private static String URL = "http://10.0.2.2:8090/axis2/services/VacaWebService?wsdl";

	private static String URL = "http://81.172.100.87:8090/axis2/services/VacaWebService?wsdl";
	/** Nombre del método*/
	private static String METHOD_NAME;
	/** SOAP Action*/
	private static String SOAP_ACTION;

	/**Solicitud del objeto soap*/
	private static SoapObject request = null;
	/** */
	private static SoapSerializationEnvelope envelope = null;
	private static SoapPrimitive resultsRequestSOAP = null;
	
	public String LlamadaListaVacas(String usuario){
		String res= "";
		
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
		} catch (IOException
				| XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return res;
	}
	
	public String LlamadaVaca(String id_vaca,String id_usuario){
		String res= "";
		
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
		} catch (IOException
				| XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return res;
	}
	
	public void LLamadaA�adirVaca(String vaca){
		
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
		} catch (IOException
				| XmlPullParserException e) {
			e.printStackTrace();
		}	
	}
	
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
		} catch (IOException
				| XmlPullParserException e) {
			e.printStackTrace();
		}	
	}
	
}
