package hu.boston.tomorrow.model;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.net.Uri;

public class MainModel {

	public EventContent selectedContent;
	private static MainModel _instance;

	public Uri imageUri;
	public File photo = null;
	
	public ArrayList<Event> events = new ArrayList<Event>();
	public ArrayList<Message> messages = new ArrayList<Message>();
	public ArrayList<User> users = new ArrayList<User>();
	
	public SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	
	public static MainModel getInstance() {
		if (_instance == null) {
			_instance = new MainModel();
		}
		return _instance;
	}
	
	public Event getEventById(String eventId) {
		
		if(events.size() == 0) {
			return null;
		}
		
		for(Event event : events) {
			if(event.getEventId().equals(eventId)) {
				return event;
			}
		}
		
		return null;
	}
}