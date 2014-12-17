package com.example.misvacasapp.llamadaWS;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

public class LlamadaUsuarioWS {

	private static final String NAMESPACE = "http://service.web";
	//private static String URL = "http://10.0.2.2:8090/axis2/services/UsuarioWebService?wsdl";

	private static String URL = "http://81.172.100.87:8090/axis2/services/UsuarioWebService?wsdl";
	private static String METHOD_NAME;
	private static String SOAP_ACTION;

	private static SoapObject request = null;
	private static SoapSerializationEnvelope envelope = null;
	private static SoapPrimitive resultsRequestSOAP = null;
	
	public String LlamadaUsuario(String usuario,String contraseña){
		String res= "";
		
		METHOD_NAME = "Usuario";
		SOAP_ACTION = "urn:Usuario";

		request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("dni", usuario);
		request.addProperty("contraseña", contraseña);

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
	
public String LlamadaUsuarioExistente(String usuario,String contraseña){
		
		String res= "";
		
		METHOD_NAME = "usuarioExistente";
		SOAP_ACTION = "urn:usuarioExistente";

		request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("dni", usuario);
		request.addProperty("contraseña", contraseña);

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
}
