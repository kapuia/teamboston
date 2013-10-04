package hu.boston.tomorrow.model;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.net.Uri;

public class MainModel {

	private static MainModel _instance;

	public Uri imageUri;
	public File photo = null;
	
	public ArrayList<Event> events = new ArrayList<Event>();
	
	public SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	
	public static MainModel getInstance() {
		if (_instance == null) {
			_instance = new MainModel();
		}
		return _instance;
	}
}
