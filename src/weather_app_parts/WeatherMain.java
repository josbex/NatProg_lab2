package weather_app_parts;

public class WeatherMain {
	
	
	public static void main(String[] args) {
		WeatherAppModel model = new WeatherAppModel();
		WeatherAppView view = new WeatherAppView(model);
		view.run();
		
		System.out.println(model.getMessageString());

	}

}
