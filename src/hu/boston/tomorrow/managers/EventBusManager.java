package hu.boston.tomorrow.managers;

import com.google.common.eventbus.EventBus;

public class EventBusManager {
	private static final EventBus eventBus = new EventBus();

	private EventBusManager() {

	}

	public static EventBus getInstance() {
		return eventBus;
	}
}
