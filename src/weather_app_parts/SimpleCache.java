package weather_app_parts;

import java.time.LocalDateTime;

import org.w3c.dom.NodeList;



//Not used
public class SimpleCache {
	
	private int timeToLive, cacheTime;
	private NodeList data;
	private boolean check = false;
	
	public SimpleCache(int ttl, int cT, NodeList d){
		this.data = d;
		this.cacheTime = cT;
		this.timeToLive = ttl;
	}

	public boolean checkIfCached(){
		checkTTL();
		if(data == null){
			check = false; //om detta sker måste ett nytt SimpleCache objekt skapas, med en ny cacheTid.
		}
		else{
			check = true;
		}
		return check;
	}
	
	public NodeList cachedData(){
		return data;
	}
	
	
	public void checkTTL(){
		LocalDateTime date = LocalDateTime.now();
		int currTime = date.toLocalTime().toSecondOfDay();
		if((currTime - (cacheTime+timeToLive)) < 0){
			data = null;
		}
	}
	
}
