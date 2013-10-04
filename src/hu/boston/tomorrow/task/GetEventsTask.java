package hu.boston.tomorrow.task;

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
import android.util.Log;

public class GetEventsTask extends AsyncTask<Void, Void, JSONArray> {

	private Context mContext;
	private WebServiceConnector mConn;
	
	public GetEventsTask(Context context) {
		mContext = context;
	}
	
	@Override
	protected void onPostExecute(
			JSONArray result) {
		
		MainModel.getInstance().events = new ArrayList<Event>();
		
		for(int i = 0; i < result.length(); i++) {
			try {
				JSONObject json = (JSONObject) result.get(i);
				
				Event event = new Event();
				event.setEventId(json.getString("EventId"));
				event.setTitle(json.getString("Title"));
				event.setDescription(json.getString("Description"));
				
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");  
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
		
//		if(result.size() > 0) {
//			GSMDataPreserver.getInstance().setCalendarItems(
//				GSMParser.getInstance().tmpCalendarItems);
//			GSMDataPreserver.getInstance().setSortedData(result);
//		}
		
		Log.d("DEBUG", "POST EXECUTE");
		
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		mConn = new WebServiceConnector(mContext);
		super.onPreExecute();
	}

	@Override
	protected JSONArray doInBackground(
			Void... params) {

		try {
			
			//JSONArray response = mConn.getEventsList();
			JSONObject response = mConn.getEventsList();
			Log.d("DEBUG", "GET BACK DATA " + response);
			
			JSONArray array = response.getJSONArray("Events"); 
			
			return array;
			
			//GSMParser parser = new GSMParser(conn, context);
			//parser.parseFromNet();
			//return parser.getDatas().sortCompetitions();

		} catch (Throwable e) {
			return new JSONArray();
		}
	}
}