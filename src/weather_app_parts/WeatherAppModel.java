package weather_app_parts;

import org.xml.sax.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.util.Observable;
import java.util.Observer;



public class WeatherAppModel implements Observer{

	private WeatherAppView view;
	private String location;
	private String time;
	private String temperature;
	private String result;
	private Document placesDoc;
	private NodeList places;
	private int len;
	private String[] cities = new String[len];

	public WeatherAppModel(WeatherAppView v) {
		this.view = v;
		v.addObserver(this);
		this.placesDoc = getDocument("./src/Places.xml");
		this.places = placesDoc.getElementsByTagName("locality");
		len = places.getLength();
		
	}
	

	private Document getDocument(String xmlDoc) {
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

			factory.setIgnoringElementContentWhitespace(true);
			factory.setValidating(true);

			DocumentBuilder builder = factory.newDocumentBuilder();
			builder.setErrorHandler(new com.sun.org.apache.xml.internal.security.utils.IgnoreAllErrorHandler());

			return builder.parse(new InputSource(xmlDoc));
		}
		catch (Exception ex){
			System.out.println("File not found");
		}

		return null;
	}
	

//	public String getMessageString() {
//		this.result = "The temperature in " + location + " at " + time + " o'clock is: " + temperature;
//		return result;
//	}
	
	public String[] getPlaceData(String location, NodeList Places){
		Node n;
		Element e;
		NodeList childNodes;
		String data[] = new String[3];
		for(int i = 0; i<Places.getLength();i++){
			n = Places.item(i);
			e = (Element) n;
			if(e.getAttribute("name").equals(location)){
				childNodes = n.getChildNodes();
				n = childNodes.item(1);
				e = (Element) n;
				data[0] = e.getAttribute("altitude");
				data[1] = e.getAttribute("latitude");
				data[2] = e.getAttribute("longitude");
			}
		}
		return data;
	}

	public String getTemp(String[] c){
		//Använder getPlacedata i API:n, sparar ner det i temperature.
		return null;
	}
	
	public void setLocationAndTime(String l, String t){
		this.location = l;
		this.time = t;
	}

	//Ser till så att de inte försöker hämta data innan plats och tid valts i vyn.
	public void update(Observable o, Object arg) {
		cities = getPlaceData(location, places);
		System.out.println(location); //Spårutskrift
		System.out.println(cities[0] + " " + cities[1] + " " + cities[2]); //Spårutskrift
		
	}
	
}

//GAMMAL KOD
//____________________________________________

//public String[] saveCities(NodeList Places){
//Node n;
//Element e;
//String[] cities = new String[3];
//for(int i = 0; i<Places.getLength();i++){
//	n = Places.item(i);
//	e = (Element) n;
//	cities[i] = e.getAttribute("name");
//}
//return cities;
//}
//
//public String getCity(String[] cities, String location){
//for(int i = 0; i<cities.length; i++){
//	if(cities[i].equals(location)){
//		return cities[i];
//	}
//}
//return "";
//}
//
//public String[] getLocalityAttr(NodeList Places){
//Node n;
//Element e;
//String locAttribute[] = new String[Places.getLength()];
//for(int i = 0; i<Places.getLength();i++){
//	n = Places.item(i);
//	e = (Element) n;
//	locAttribute[i] = e.getAttribute("name");
//}
//return locAttribute;
//}

//public String[][] getPlaceData(NodeList Places){
//Node n;
//Element e;
//NodeList childNodes;
//String data[][] = new String[Places.getLength()][3];
//for(int i = 0; i<Places.getLength();i++){
//	n = Places.item(i);
//	e = (Element) n;
//		childNodes = n.getChildNodes();
//		n = childNodes.item(1);
//		e = (Element) n;
//		data[i][0] = e.getAttribute("altitude");
//		data[i][1] = e.getAttribute("latitude");
//		data[i][2] = e.getAttribute("longitude");
//}
//return data;
//}
