package weather_app_parts;

import org.xml.sax.*;
import org.w3c.dom.*;

import javax.lang.model.util.Elements;
import javax.xml.parsers.*;


public class WeatherAppModel {

	private String location;
	private String time;
	private String temperature;
	private String result;
	private Document placesDoc;
	private NodeList places;

	public WeatherAppModel(){
		this.placesDoc = getDocument("./src/Places.xml");
		this.places = placesDoc.getElementsByTagName("locality");
		
	}

	private Document getDocument(String xmlDoc) {
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

			factory.setIgnoringElementContentWhitespace(true);
			factory.setValidating(true);

			DocumentBuilder builder = factory.newDocumentBuilder();

			return builder.parse(new InputSource(xmlDoc));
		}
		catch (Exception ex){
			System.out.println("File not found");
		}

		return null;
	}

	public String getMessageString() {
		this.result = "The temperature in " + location + " at " + time + " o'clock is: " + temperature;
		return result;
	}

	public double[] getPlaceData(String location){
		double data[];

		//Kod som använder location för att få ut värdena alt, lon o lat.

		return null;
	}

	public String getTemp(){
		//Använder getPlacedata i API:n, sparar ner det i temperature.
		return null;
	}
	
	public void setLocationAndTime(String l, String t){
		this.location = l;
		this.time = t;
	}
	



	
//	public void listPlaces(){
//		System.out.println(places.toString());
//	}
//	
//	public static void main(String[] args) {
//		listPlaces()
//	}

}
