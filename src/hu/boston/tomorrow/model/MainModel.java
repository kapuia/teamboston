package hu.boston.tomorrow.model;

import java.io.File;
import java.util.ArrayList;

import android.net.Uri;

public class MainModel {

	private static MainModel _instance;

	public Uri imageUri;
	public File photo = null;
	
	public ArrayList<Event> events = new ArrayList<Event>();
	
	public static MainModel getInstance() {
		if (_instance == null) {
			_instance = new MainModel();
		}
		return _instance;
	}
}
