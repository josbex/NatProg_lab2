package weather_app_parts;

import java.util.Observable;
import java.util.Observer;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.*;


public class WeatherAppView implements ActionListener{

	private WeatherAppModel data;
	private JFrame window;
	private JPanel background;
	private JLabel place;
	private JLabel time;
	private JLabel temp;
	private JLabel tempRes;
	private JComboBox<String> placeBox;
	private JComboBox<String> timeBox;
	private JButton resButton;
	private String location;
	private String timeOfDay;
//	private String input[];

	public WeatherAppView(WeatherAppModel d){
		
		this.data = d;
		this.window = new JFrame("My WeatherApp");
		this.background = new JPanel();
		this.place = new JLabel("Location:");
		this.placeBox = new JComboBox<String>();
		this.time = new JLabel("Time of day:");
		this.timeBox = new JComboBox<String>();
		this.temp = new JLabel("Temperature:");
		this.tempRes = new JLabel("Press 'OK!' for result");
		this.resButton = new JButton("OK!");
		
		resButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				location = (String) placeBox.getSelectedItem();
				timeOfDay = (String)timeBox.getSelectedItem();
				data.setLocationAndTime(location, timeOfDay);
				tempRes.setText(data.getMessageString());

			}});
	}
	
//	public void setResult(){
//		resButton.addActionListener(new ActionListener(){
//
//			public void actionPerformed(ActionEvent arg0) {
//				tempRes.setText(data.getMessageString());
//
//			}});
//	}

//	public void getLocation(){
//		placeBox.addActionListener(new ActionListener(){
//
//			public void actionPerformed(ActionEvent arg0) {
//				location = (String) placeBox.getSelectedItem();
//			}});
//	}
//
//	public void getTime(){
//		timeBox.addActionListener(new ActionListener(){
//
//			public void actionPerformed(ActionEvent arg0) {
//				timeOfDay = (String)timeBox.getSelectedItem();
//			}});
//	}
	
//	public void userInput(){
////		input = new String[2];
//		resButton.addActionListener(new ActionListener(){
//
//			public void actionPerformed(ActionEvent arg0) {
//				data.setLocationAndTime(location, timeOfDay);
//			}});
//	}
	
//	public String[] getUserInput(){
//		return input;
//	}
	
    public void run() {
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(400, 300); 
        background.setBackground(Color.LIGHT_GRAY);
        
		placeBox.setEditable(true);
		placeBox.addItem("Skelleftea");
		placeBox.addItem("Kage");
		placeBox.addItem("Stockholm");
		
		timeBox.setEditable(true);
		for(int i = 1; i <= 24; i++){
			timeBox.addItem(Integer.toString(i));
		}
		window.add(background);
		appLayout();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
    }
    
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
		
    }
    
	

	public void update(Observable arg0, Object arg1) {

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}


}
