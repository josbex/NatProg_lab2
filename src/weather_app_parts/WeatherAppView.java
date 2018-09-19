package weather_app_parts;

import java.util.ArrayList;
import java.util.Observable;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.*;

import javax.swing.*;

/**
 * This class is in charge of presenting the GUI of the program 
 * and also reacting to the interactions of the user in the program.
 * @author Josefine Bexelius
 *
 */
public class WeatherAppView extends Observable implements ActionListener {

	private JFrame window;
	private JPanel background;
	private JTextField refreshTime;
	private JLabel place;
	private JLabel time;
	private JLabel temp;
	private JLabel tempRes, refMessage;
	private JComboBox<String> placeBox;
	private JComboBox<String> timeBox;
	private JButton resButton;
	private String location;
	private String timeOfDay;
	private String temperature, celsius;
	private int currentHour, ttl;
	private ArrayList<String> placesAndTimes = new ArrayList<String>();
	private ArrayList<Long> requestTimes = new ArrayList<Long>();
	private int indexCounter;

	/**
	 * Constructor of the view, creates the GUI components and
	 * adds listeners to the programs buttons.
	 */
	public WeatherAppView(){
		
		long startTime = System.currentTimeMillis()/1000;
		requestTimes.add(startTime);
		this.celsius =  "\u2103";
		this.window = new JFrame("My WeatherApp");
		this.background = new JPanel();
		this.place = new JLabel("Location:");
		this.placeBox = new JComboBox<String>();
		this.time = new JLabel("Time of day:");
		this.timeBox = new JComboBox<String>();
		this.temp = new JLabel("Temperature:");
		this.tempRes = new JLabel("Press 'OK!' for result");
		this.resButton = new JButton("OK!");
		this.refreshTime = new JTextField();
		this.refMessage = new JLabel("Set refresh rate (in seconds)");
			
		resButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				//Checks so that the users input is valid
				if(placesAndTimes.contains((String) placeBox.getSelectedItem()) && placesAndTimes.contains((String)timeBox.getSelectedItem())){
					location = (String) placeBox.getSelectedItem();
					timeOfDay = (String)timeBox.getSelectedItem();
					long currTime = System.currentTimeMillis()/1000;
					requestTimes.add(currTime);
					indexCounter++;
					ttl = Integer.valueOf(refreshTime.getText()) +60;
					setChanged();
					notifyObservers();
					//Checks so that the requested time is earlier than what's available from the data.
					if(currentHour <= Integer.valueOf(timeOfDay)){
						tempRes.setText("The temperature in " + getLocation() + " at " + getTime() + " o'clock today is: " + getTemp() + celsius);
					}
					else{
						tempRes.setText("Data not available for that time, choose a later time.");
					}
				}
				else{
					tempRes.setText("Invalid input, choose a location and time from the lists.");
				}
			}});
		
	}
	
	/**
	 * This method runs the GUI.
	 */
    public void run() {
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(400, 350); 
        background.setBackground(Color.LIGHT_GRAY);
        
		//Adding contents to the JBoxes and also to the checklist.
        placeBox.setEditable(true);
		placeBox.addItem("Skelleftea");
		placeBox.addItem("Kage");
		placeBox.addItem("Stockholm");
		placesAndTimes.add("Skelleftea");
		placesAndTimes.add("Stockholm");
		placesAndTimes.add("Kage");
		
		timeBox.setEditable(true);
		for(int i = 0; i < 24; i++){
			timeBox.addItem(Integer.toString(i));
			placesAndTimes.add(Integer.toString(i));
		}
		window.add(background);
		appLayout();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
    }
    
    /**
     * This method sets up the layout of the GUI:s components.
     */
    public void appLayout(){
    	BoxLayout b1 = new BoxLayout(background, BoxLayout.PAGE_AXIS);
    	background.setLayout(b1);
    	place.setAlignmentX(Component.CENTER_ALIGNMENT);
		background.add(place);
		background.add(Box.createRigidArea(new Dimension(400,5)));
		placeBox.setMaximumSize(new Dimension(100,20));
		background.add(placeBox);
		background.add(Box.createRigidArea(new Dimension(400,20)));
	
		BoxLayout b2 = new BoxLayout(background, BoxLayout.PAGE_AXIS);
		background.setLayout(b2);
		time.setAlignmentX(Component.CENTER_ALIGNMENT);
		background.add(time);
		background.add(Box.createRigidArea(new Dimension(400,5)));
		timeBox.setMaximumSize(new Dimension(100,20));
		background.add(timeBox);
		background.add(Box.createRigidArea(new Dimension(400,20)));
		
		BoxLayout b4 = new BoxLayout(background, BoxLayout.PAGE_AXIS);
		background.setLayout(b4);
		refMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
		background.add(refMessage);
		background.add(Box.createRigidArea(new Dimension(400,5)));
		refreshTime.setAlignmentX(Component.CENTER_ALIGNMENT);
		refreshTime.setMaximumSize(new Dimension(100, 20));
		background.add(refreshTime);		
		
		BoxLayout b3 = new BoxLayout(background, BoxLayout.PAGE_AXIS);
		background.setLayout(b3);
		temp.setAlignmentX(Component.CENTER_ALIGNMENT);
		background.add(temp);
		background.add(Box.createRigidArea(new Dimension(400,5)));
		tempRes.setAlignmentX(Component.CENTER_ALIGNMENT);
		background.add(tempRes);
		background.add(Box.createRigidArea(new Dimension(400,20)));
		resButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		background.add(resButton);
		background.add(Box.createRigidArea(new Dimension(400,5)));
		

		
    }
    
    /**
     * Sets the current time (only hour) when the program runs.
     * @param currHour the current hour 
     */
    public void setCurrentTime(int currHour){
    	currentHour = currHour;
    }
    
    /**
     * Gets the location that is chosen in the GUI.
     * @return location The chosen location.
     */
    public String getLocation(){
    	return location;
    }
    
    /**
     * Gets the time that is chosen in the GUI.
     * @return timeOfDay The chosen time.
     */
    public String getTime(){
    	return timeOfDay;
    }
    
  
    /**
     * Sets  the temperature that gets displayed in the GUI.
     * @param temp
     */
    public void setTemp(String temp){
    	temperature = temp;
    }
    
	/**
	 * Gets the chosen temperature.
	 * @return temperature
	 */
    public String getTemp(){
		return temperature;
	}
    
    public ArrayList<Long> getRequestTimes(){
    	return requestTimes;
    }
    
    public int getIndex(){
    	return indexCounter;
    }

    public int getTTL(){
    	return ttl;
    }
    
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub	
	}

}



