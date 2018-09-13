package weather_app_parts;

import java.util.Observable;
import java.util.Observer;

public class WeatherAppController implements Observer{
	
	private WeatherAppModel data;
	private WeatherAppView view;
	private String result;
	
	public WeatherAppController(WeatherAppModel d, WeatherAppView v){
		this.data = d;
		this.view = v;
		v.addObserver(this);
	
		
		
	}
	
	
	//Kom ihåg en get resultmetod i modelklassen.
	public String getMessageString() {
		this.result = "The temperature in " + view.getLocation() + " at " + view.getTime() + " o'clock is: " + " (Yr data)";
		return result;
	}
	
	//Ser till så att location och time skrivs till model när appen används.
	public void update(Observable arg0, Object arg1) {
		data.setLocationAndTime(view.getLocation(), view.getTime());
		result = getMessageString();
		view.setMessage(result);
		
	}

}
