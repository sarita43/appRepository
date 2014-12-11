package com.example.misvacasapp.llamadaWS;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

public class LlamadaVacaWS {

	private static final String NAMESPACE = "http://service.web";
	private static String URL = "http://10.0.2.2:8090/axis2/services/VacaWebService?wsdl";

	//private static String URL = "http://192.168.1.100:8090/axis2/services/UsuarioWebService?wsdl";
	private static String METHOD_NAME;
	private static String SOAP_ACTION;

	private static SoapObject request = null;
	private static SoapSerializationEnvelope envelope = null;
	private static SoapPrimitive resultsRequestSOAP = null;
	
	public String LlamadaVaca(String usuario){
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
	
}
