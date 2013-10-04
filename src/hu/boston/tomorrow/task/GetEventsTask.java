package hu.boston.tomorrow.task;

import hu.boston.tomorrow.events.EventsDownloadedEvent;
import hu.boston.tomorrow.managers.EventBusManager;
import hu.boston.tomorrow.model.Event;
import hu.boston.tomorrow.model.MainModel;
import hu.boston.tomorrow.services.WebServiceConnector;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;

import com.google.common.eventbus.EventBus;

public class GetEventsTask extends AsyncTask<Void, Void, JSONArray> {

	private Context mContext;
	private WebServiceConnector mConn;
	private EventBus eventBus;

	public GetEventsTask(Context context) {
		mContext = context;
		eventBus = EventBusManager.getInstance();
	}

	@Override
	protected void onPostExecute(JSONArray result) {

		MainModel.getInstance().events = new ArrayList<Event>();

		SimpleDateFormat format = MainModel.getInstance().format;

		for (int i = 0; i < result.length(); i++) {
			try {
				JSONObject json = (JSONObject) result.get(i);

				Event event = new Event();
				event.setEventId(json.getString("EventId"));
				event.setTitle(json.getString("Title"));
				event.setDescription(json.getString("Description"));

				try {
					event.setStartTime(format.parse(json.getString("StartTime")));
					event.setEndTime(format.parse(json.getString("EndTime")));
				} catch (ParseException e) {
					event.setStartTime(new Date());
					event.setEndTime(new Date());
					e.printStackTrace();
				}

				MainModel.getInstance().events.add(event);

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		eventBus.post(new EventsDownloadedEvent());

		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		mConn = new WebServiceConnector(mContext);
		super.onPreExecute();
	}

	@Override
	protected JSONArray doInBackground(Void... params) {

		try {
			JSONObject response = mConn.getEventsList();
			JSONArray array = response.getJSONArray("Events");

			return array;

		} catch (Throwable e) {
			return new JSONArray();
		}
	}
}