package weather_app_parts;

/**
 * This class runs the weatherapp.
 * 
 * @author Josefine Bexelius
 *
 */
public class WeatherMain {
	
	public static void main(String[] args) {
		WeatherAppView view = new WeatherAppView();
		WeatherAppModel model = new WeatherAppModel(view);
		new WeatherAppController(model, view);
		view.run();
	}

}
