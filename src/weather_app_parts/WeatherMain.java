package weather_app_parts;

public class WeatherMain {
	
	
	public static void main(String[] args) {
		WeatherAppView view = new WeatherAppView();
		WeatherAppModel model = new WeatherAppModel(view);
		new WeatherAppController(model, view);
		view.run();



	}

}
