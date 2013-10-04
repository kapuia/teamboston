package hu.boston.tomorrow.task;

import hu.boston.tomorrow.model.Event;
import hu.boston.tomorrow.model.EventContent;
import hu.boston.tomorrow.model.MainModel;
import hu.boston.tomorrow.services.WebServiceConnector;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;

public class GetEventContentsTask extends AsyncTask<Void, Void, JSONArray> {

	private Context mContext;
	private WebServiceConnector mConn;
	private String mEventId;

	public GetEventContentsTask(Context context, String eventId) {
		mContext = context;
		mEventId = eventId;
	}

	@Override
	protected void onPostExecute(JSONArray result) {

		Event event = MainModel.getInstance().getEventById(mEventId);

		if (event == null) {
			return;
		}

		ArrayList<EventContent> contents = new ArrayList<EventContent>();

		for (int i = 0; i < result.length(); i++) {
			try {
				JSONObject json = (JSONObject) result.get(i);

				EventContent content = new EventContent();
				content.setContentId(json.getString("ContentId"));
				content.setTitle(json.getString("Title"));
				
				JSONArray bodyArray = json.getJSONArray("Body");
				ArrayList<String> bodyList =new ArrayList<String>();
				
				for (int j = 0; j < bodyArray.length(); j++) {

					 String s = bodyArray.get(j).toString();
					 bodyList.add(s);
				}

				content.setBody(bodyList);
				content.setOrder(json.getInt("Order"));
				content.setContentType(json.getInt("ContentType"));

				contents.add(content);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		event.setEventContentList(contents);

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
			JSONObject response = mConn.getEventContents(mEventId);
			JSONArray array = response.getJSONArray("Contents");

			return array;

		} catch (Throwable e) {
			return new JSONArray();
		}
	}
}