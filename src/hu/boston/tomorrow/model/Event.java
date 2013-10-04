package hu.boston.tomorrow.model;

import java.util.ArrayList;
import java.util.Date;

public class Event {

	private String eventId;
	private String title;
	private String description;
	private Date startTime;
	private Date endTime;
	private ArrayList<EventContent> eventContentList;

	private ArrayList<Message> messages;
	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public ArrayList<EventContent> getEventContentList() {
		return eventContentList;
	}
	
	public void setEventContentList(ArrayList<EventContent> eventContentList) {
		this.eventContentList = eventContentList;
	}

	public ArrayList<Message> getMessages() {
		return messages;
	}

	public void setMessages(ArrayList<Message> messages) {
		this.messages = messages;
	}
}