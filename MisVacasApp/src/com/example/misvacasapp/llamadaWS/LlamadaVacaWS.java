package com.example.misvacasapp.llamadaWS;

import java.io.IOException;
import java.util.Date;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

public class LlamadaVacaWS {

	private static final String NAMESPACE = "http://service.web";
//	private static String URL = "http://10.0.2.2:8090/axis2/services/VacaWebService?wsdl";

	private static String URL = "http://81.172.100.87:8090/axis2/services/VacaWebService?wsdl";
	private static String METHOD_NAME;
	private static String SOAP_ACTION;

	private static SoapObject request = null;
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
	
	public void LLamadaAñadirVaca(String id_vaca, String raza, Date fecha_nacimiento,
			String id_madre, String foto, String id_usuario){
		
		METHOD_NAME = "añadirVaca";
		SOAP_ACTION = "urn:añadirVaca";

		request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("id_vaca", id_vaca);
		request.addProperty("raza", raza);
		request.addProperty("fecha_nacimiento", fecha_nacimiento);
		request.addProperty("id_madre", id_madre);
		request.addProperty("foto", foto);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
}
