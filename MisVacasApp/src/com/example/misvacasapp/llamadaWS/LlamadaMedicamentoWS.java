package com.example.misvacasapp.llamadaWS;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

public class LlamadaMedicamentoWS {

	private static final String NAMESPACE = "http://service.web";
	private static String URL = "http://10.0.2.2:8090/axis2/services/MedicamentoWebService?wsdl";

//	 private static String URL =
//	 "http://81.172.100.87:8090/axis2/services/MedicamentoWebService?wsdl";
	private static String METHOD_NAME;
	private static String SOAP_ACTION;

	private static SoapObject request = null;
	private static SoapSerializationEnvelope envelope = null;
	private static SoapPrimitive resultsRequestSOAP = null;

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
}
