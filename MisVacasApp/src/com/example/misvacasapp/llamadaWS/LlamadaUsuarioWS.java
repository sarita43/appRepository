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
	
	public void actualizarContraseña(String dni,String contraseña){
		METHOD_NAME = "actualizarContraseña";
		SOAP_ACTION = "urn:actualizarContraseña";

		request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("dni", dni);
		request.addProperty("contraseña", contraseña);

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
	
	public void eliminarUsuario(String id_usuario){
		METHOD_NAME = "eliminarUsuario";
		SOAP_ACTION = "urn:eliminarUsuario";

		request = new SoapObject(NAMESPACE, METHOD_NAME);
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
	
	public void actualizarUsuario(String dni, String nombre, String apellido1,
			String apellido2, String direccion, String poblacion, int telefono){
		METHOD_NAME = "actualizarUsuario";
		SOAP_ACTION = "urn:actualizarUsuario";

		request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("dni", dni);
		request.addProperty("nombre", nombre);
		request.addProperty("apellido1",apellido1);
		request.addProperty("apellido2", apellido2);
		request.addProperty("direccion", direccion);
		request.addProperty("poblacion", poblacion);
		request.addProperty("telefono", telefono);

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
