package weather_app_parts;

import org.xml.sax.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;



public class WeatherAppModel implements Observer{

	private WeatherAppView view;
	private String baseURL = "https://api.met.no/weatherapi/locationforecast/1.9/?";
	private String addOnURL = "";
	private URL yrURL; 
	private URLConnection yrConnection; 
	private String location;
	private String time;
	private String dateAndTime;
	private String temperature;
	private Document placesDoc;
	private NodeList places;
	private Document dataDoc;
	private NodeList data;
	private String[] city = new String[3];

	public WeatherAppModel(WeatherAppView v) {
		this.view = v;
		v.addObserver(this);
		this.placesDoc = getDocument("./src/Places.xml");
		this.places = placesDoc.getElementsByTagName("locality");
		
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
	
	public Document dataToDocument(URLConnection con) {
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setIgnoringElementContentWhitespace(true);
			factory.setValidating(true);

			DocumentBuilder builder = factory.newDocumentBuilder();
			builder.setErrorHandler(new com.sun.org.apache.xml.internal.security.utils.IgnoreAllErrorHandler());
			
			return builder.parse(con.getInputStream());
		}
		catch (Exception ex){
		}
		return null;
	}
	
	public void connectionSetup(){
		try{
			yrURL = new URL(baseURL + addOnURL);
			yrConnection = yrURL.openConnection();
			yrConnection.connect();
		}
		catch (MalformedURLException e){
			System.out.println("URL didnt work");
		}
		catch (IOException e){
		}
	}
	
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
	

	public String getTemp(){
		return temperature;
	}
	
	public String todaysDate(){
		String attributeDate  = "";
		String today = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		today = sdf.format(date);
		if(Integer.valueOf(time) < 10){
			attributeDate = today + "T0" + time +":00:00Z";
		}
		else{
			attributeDate = today + "T" + time +":00:00Z";
		}
		return attributeDate;
	}	
	  
	public void setLocationAndTime(String l, String t){
		this.location = l;
		this.time = t;
	}
	
	public void setTemperature(){
		Node n,m;
		Element e,k;
		dataDoc = dataToDocument(yrConnection);
		data = dataDoc.getElementsByTagName("temperature");
		String test ="";
		for(int i = 0; i < data.getLength(); i++){
			n = data.item(i);
			m =  n.getParentNode().getParentNode();
			k = (Element) m;
			e = (Element) n;
			test = k.getAttribute("from");
			if(test.equals(dateAndTime)){
				temperature = e.getAttribute("value");
			}
		}	
	}

	//Ser till så att de inte försöker hämta data innan plats och tid valts i vyn.
	public void update(Observable o, Object arg) {
		city = getPlaceData(location, places);
		addOnURL = "lat="+ city[1] + "&lon=" + city[2] + "&msl=" + city[0];
		dateAndTime = todaysDate();
		connectionSetup();
		setTemperature();
		view.setTemp(temperature);
	}
}

//GAMMAL KOD
//____________________________________________

//System.out.println(test);
//System.out.println(test + "if körs");
//System.out.println(location); //Spårutskrift
//System.out.println(city[0] + " " + city[1] + " " + city[2]); //Spårutskrift
//System.out.println(baseURL + addOnURL); //Spårutskrift
//
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
//public void updatingData(){
//	Node n;
//	Element e;
//	int j = 0;
//	NodeList updatedData;
//	String test ="";
//	for(int i = 0; i < data.getLength(); i++){
//		n = data.item(i);
//		e = (Element) n;
//		test = e.getAttribute("from");
//		if(test == dateAndTime){
//			
//		}
//	}
//}
//public void checkTempAtTime(String t){
////(getElementsByTagName) time datatype="forecast" from="2018-09-14T06:00:00Z" to="2018-09-14T06:00:00Z">
//}

//public String[] saveTempd(NodeList d){
//Node n;
//Element e;
//String[] dailyTemps = new String[24];
//for(int i = 0; i<24;i++){
//n = d.item(i);
//e = (Element) n;
//dailyTemps[i] = e.getAttribute("from");
//}
//return dailyTemps;
//}
