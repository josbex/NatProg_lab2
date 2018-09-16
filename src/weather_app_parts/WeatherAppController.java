package weather_app_parts;

import java.util.Observable;
import java.util.Observer;

/**
 * This class connects the view part of the program with the data part.
 * @author Josefine Bexelius
 *
 */
public class WeatherAppController implements Observer{
	
	private WeatherAppModel data;
	private WeatherAppView view;
	
	public WeatherAppController(WeatherAppModel d, WeatherAppView v){
		this.data = d;
		this.view = v;
		v.addObserver(this);	
	}
	
	/**
	 * This method gets called when a button is pushed when the program runs
	 * to make sure that the correct values are sent to the model class.
	 */
	public void update(Observable arg0, Object arg1) {
		data.setLocationAndTime(view.getLocation(), view.getTime());		
	}
	
}

