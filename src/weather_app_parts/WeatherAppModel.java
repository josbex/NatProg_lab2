package weather_app_parts;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


/**
 * This class is in charge of handling the data from the yr API.
 * @author Josefine Bexelius
 *
 */
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
	private int currentHour;
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
	
	/**
	 * Creates a document of an xml file.
	 * @param xmlDoc The path to the xml file.
	 * @return document of the file.
	 */
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
	
	/**
	 * Creates a document out of the data from the yr API.
	 * @param con the connection to the yr API.
	 * @return data doument
	 */
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
	
	/**
	 * Initiates the connection to the yr API and
	 * saves the data down to a document variable.
	 */
	public void connectionSetup(){
		try{
			yrURL = new URL(baseURL + addOnURL);
			yrConnection = yrURL.openConnection();
			yrConnection.connect();
			dataDoc = dataToDocument(yrConnection);
			data = dataDoc.getElementsByTagName("temperature");
		}
		catch (MalformedURLException e){
			System.out.println("URL didnt work");
		}
		catch (IOException e){
		}
	}
	
	/**
	 * Sets coordinates of the location that is chosen in the view class.
	 * @param location The city chosen in the GUI
	 * @param Places List of the available places coordinates. 
	 * @return data A string array contains the citys three coordinates, lon, lat and alt.
	 */
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
	
	/**
	 * Looks up todays date, which is needed for the URL.
	 * Also sets the current time for the view. 
	 * @return
	 */
	public String todaysDate(){
		String attributeDate  = "";
		String today = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);  
		currentHour = cal.get(Calendar.HOUR_OF_DAY);
		view.setCurrentTime(currentHour);
		today = sdf.format(date);
		if(Integer.valueOf(time) < 10){
			attributeDate = today + "T0" + time +":00:00Z";
		}
		else{
			attributeDate = today + "T" + time +":00:00Z";
		}
		return attributeDate;
	}	
	  
	/**
	 * Sets the location and time that is chosen by the user.
	 * @param l location
	 * @param t time
	 */
	public void setLocationAndTime(String l, String t){
		this.location = l;
		this.time = t;
	}
	
	/**
	 * Sets the temperature that corresponds to the chosen time and
	 * location. Does this by usig the data document from yr API.
	 */
	public void setTemperature(){
		Node n,m;
		Element e,k;
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

	/**
	 * This method runs when the user pushes buttons in the GUI, so that
	 * the contained methods doesn't run before some variables have been set. 
	 */
	public void update(Observable o, Object arg) {
		city = getPlaceData(location, places);
		addOnURL = "lat="+ city[1] + "&lon=" + city[2] + "&msl=" + city[0];
		dateAndTime = todaysDate();
		long currTime = System.currentTimeMillis()/1000;
		//Only creates a new connection if there isn't old data available
		if(currTime - view.getRequestTimes().get(view.getIndex()-1) < view.getTTL()){ 
			connectionSetup();
		}
		setTemperature();
		view.setTemp(temperature);
	}
}


