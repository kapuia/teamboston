package hu.boston.tomorrow.events;

import hu.boston.tomorrow.model.Event;

public class EventContentDownloadedEvent {
	
	private Event event;
	public EventContentDownloadedEvent(Event event)
	{
	this.event = event;
	}
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}

}
