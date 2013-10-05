package hu.boston.tomorrow.services.imagestream;

import java.util.ArrayList;
import java.util.HashMap;

import android.util.Log;

public class ImageStreamDataPreserver {
	private ArrayList<String> urlsForFeedList = new ArrayList<String>();
	private ArrayList<String> descriptionsForFeedList = new ArrayList<String>();
	private ArrayList<String> urlsForRedBullFeedList = new ArrayList<String>();
	private ArrayList<String> descriptionsForRedBullFeedList = new ArrayList<String>();
	private ArrayList<String> urlsForBehindList = new ArrayList<String>();
	private ArrayList<String> descriptionsForBehindList = new ArrayList<String>();
	
	public static HashMap<String, int[]> mImageDictionary = new HashMap<String, int[]>(); 
	
	private static ImageStreamDataPreserver instance = null;

	protected ImageStreamDataPreserver() {
		mImageDictionary.put("test", new int[] {100,100});
	}

	public static ImageStreamDataPreserver getInstance() {
		if (instance == null) {
			instance = new ImageStreamDataPreserver();
		}
		return instance;
	}

	public ArrayList<String> getUrlsForFeedList() {
		return urlsForFeedList;
	}

	public ArrayList<String> getDescriptionsForFeedList() {
		return descriptionsForFeedList;
	}

	public void setUrlsForFeedList(ArrayList<String> urlsForFeedList) {
		this.urlsForFeedList = urlsForFeedList;
	}

	public void setDescriptionsForFeedList(
			ArrayList<String> descriptionsForFeedList) {
		this.descriptionsForFeedList = descriptionsForFeedList;
	}

	public ArrayList<String> getUrlsForRedBullFeedList() {
		return urlsForRedBullFeedList;
	}

	public ArrayList<String> getDescriptionsForRedBullFeedList() {
		return descriptionsForRedBullFeedList;
	}

	public void setUrlsForRedBullFeedList(
			ArrayList<String> urlsForRedBullFeedList) {
		this.urlsForRedBullFeedList = urlsForRedBullFeedList;
	}

	public void setDescriptionsForRedBullFeedList(
			ArrayList<String> descriptionsForRedBullFeedList) {
		this.descriptionsForRedBullFeedList = descriptionsForRedBullFeedList;
	}

	public ArrayList<String> getUrlsForBehindList() {
		return urlsForBehindList;
	}

	public ArrayList<String> getDescriptionsForBehindList() {
		return descriptionsForBehindList;
	}

	public void setUrlsForBehindList(ArrayList<String> urlsForBehindList) {
		this.urlsForBehindList = urlsForBehindList;
	}

	public void setDescriptionsForBehindList(
			ArrayList<String> descriptionsForBehindList) {
		this.descriptionsForBehindList = descriptionsForBehindList;
	}
}
