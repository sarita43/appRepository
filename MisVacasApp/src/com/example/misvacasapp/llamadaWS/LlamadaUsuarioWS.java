package com.example.misvacasapp.llamadaWS;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

/**
 * Clase que hace las llamadas al servicio web del usuario
 * @author Sara Martinez Lopez
 * */
public class LlamadaUsuarioWS {

	//Atributos
	/** Nombre del espacio de nombres*/
	private static final String NAMESPACE = "http://service.web";
	/**URL donde se encuentra el servicio web*/
//	private static String URL = "http://10.0.2.2:8090/axis2/services/UsuarioWebService?wsdl";

	/**Nombre del método*/
	private static String METHOD_NAME;
	/**SOAP Action*/
	private static String SOAP_ACTION;
	/**Solicitud del objeto soap*/
	private static SoapObject request = null;
	/**Contenedor soap*/
	private static SoapSerializationEnvelope envelope = null;
	/**Resultado de la llamada */
	private static SoapPrimitive resultsRequestSOAP = null;
	
	//Métodos
	/**
	 * Método que devuelve el usuario (como String).
	 * El usuario que devuelve tiene que poder ser deserializado con json.formJson
	 * Comprueba que el usuario y la contraseña coinciden 
	 * @param usuario Id del usuario
	 * @param contraseña Contraseña del usuario
	 * @return String Usuario
	 * */
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
			e.printStackTrace();
		}	
		
		return res;
	}
	
	/**
	 * Método que dice si un usuario existe o no 
	 * Si el usuario existe devuelve un 'true' y si no un 'false'
	 * @param usuario Id del usuario
	 * @param contraseña Contraseña del usuario
	 * @return boolean Usuario existente o no
	 * */
	public boolean LlamadaUsuarioExistente(String usuario,String contraseña){
		
		boolean res= false;
		
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
			e.printStackTrace();
		}	
		
		return res;
	}
	
	/**
	 * Método que cambia la contraseña de un usuario
	 * @param dni Id del usuario
	 * @param contraseña Contraseña del usuario
	 * */
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
	
	/**
	 * Método que elimina un usuario
	 * @param id_usuario Id del usuario
	 * */
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
	
	/**
	 * Método que cambia los parametros del usuario: id, nombre, apellidos, dirección, pobñación, teléfono
	 * @param dni Id del usuario
	 * @param nombre Nombre del usuario
	 * @param apellido1 Primer apellido del usuario
	 * @param apellido2 Segundo apellido del usuario
	 * @param direccion Direción del usuario
	 * @param poblacion Población del usuario
	 * @telefono Teléfono del usuario
	 * */
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
