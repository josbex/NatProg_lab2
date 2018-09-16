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
	private JLabel place;
	private JLabel time;
	private JLabel temp;
	private JLabel tempRes, refMessage;
	private JComboBox<String> placeBox;
	private JComboBox<String> timeBox;
	private JButton resButton, refreshButton;
	private String location;
	private String timeOfDay;
	private String temperature, celsius;
	private int currentHour;
	private boolean refreshData = true;
	private ArrayList<String> placesAndTimes = new ArrayList<String>();

	/**
	 * Constructor of the view, creates the GUI components and
	 * adds listeners to the programs buttons.
	 */
	public WeatherAppView(){
		
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
		this.refreshButton = new JButton("Refresh data");
		this.refMessage = new JLabel("Press 'Refresh data' and then 'OK' for latest data");
			
		resButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				//Checks so that the users input is valid
				if(placesAndTimes.contains((String) placeBox.getSelectedItem()) && placesAndTimes.contains((String)timeBox.getSelectedItem())){
					location = (String) placeBox.getSelectedItem();
					timeOfDay = (String)timeBox.getSelectedItem();
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
		
		//sets variable to true when button is clicked, making it so that new data is used.
		refreshButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				refreshData = true;
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
		
		BoxLayout b4 = new BoxLayout(background, BoxLayout.PAGE_AXIS);
		background.setLayout(b4);
		background.add(Box.createRigidArea(new Dimension(400,20)));
		refreshButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		background.add(refreshButton);
		background.add(Box.createRigidArea(new Dimension(400,5)));
		refMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
		background.add(refMessage);
		
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
     * Sets the refreshData to true or false, 
     * which decides if cache or new data should be used.
     * @param notCaching
     */
    public void setRefreshData(boolean notCaching){
    	refreshData = notCaching;
    }
    
    /**
     * Gets the value of the refreshData variable.
     * @return refreshData
     */
    public boolean getRefreshData(){
    	return refreshData;
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

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub	
	}

}

