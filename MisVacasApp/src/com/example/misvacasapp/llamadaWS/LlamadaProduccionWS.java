package com.example.misvacasapp.llamadaWS;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

/**
 * Clase que hace las llamadas al servicio web de la produccion de leche y carne
 * 
 * @author Sara Martinez Lopez
 * */
public class LlamadaProduccionWS {

	//--------------------------------Atributos-------------------------------------------//
		/** Nombre del espacio de nombres */
		private static final String NAMESPACE = "http://service.web";
		/** URL donde se encuentra el servicio web */
		 private static String URL =
		 "http://10.0.2.2:8090/axis2/services/ProduccionWebService?wsdl";
//		private static String URL = "http://81.172.100.105:8090/axis2/services/ProduccionWebService?wsdl";
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

		//------------------------------------Métodos------------------------------------------//
		public String getProducciones(String id_usuario){
			String res = "";
			METHOD_NAME = "getProducciones";
			SOAP_ACTION = "urn:getProducciones";
			request = new SoapObject(NAMESPACE, METHOD_NAME);
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
		
		public void setProducciones(String listaProducciones,String id_usuario){
			METHOD_NAME = "setProducciones";
			SOAP_ACTION = "urn:setProducciones";
			request = new SoapObject(NAMESPACE, METHOD_NAME);
			request.addProperty("listaProducciones", listaProducciones);
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
