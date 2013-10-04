package hu.boston.tomorrow.task;

import hu.boston.tomorrow.services.WebServiceConnector;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

// TODO - Atirni
public class GetEventsTask extends AsyncTask<Void, Void, ArrayList<String>> {

	private Context mContext;
	private WebServiceConnector mConn;
	
	public GetEventsTask(Context context) {
		mContext = context;
	}
	
	@Override
	protected void onPostExecute(
			ArrayList<String> result) {
		
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
	protected ArrayList<String> doInBackground(
			Void... params) {

		try {
			
			String a = mConn.getEventsList();
			Log.d("DEBUG", "GET BACK DATA " + a);
			
			//GSMParser parser = new GSMParser(conn, context);
			//parser.parseFromNet();
			//return parser.getDatas().sortCompetitions();

		} catch (Throwable e) {
			//return new LinkedHashMap<Integer, ArrayList<Person>>();
		}
		
		return new ArrayList<String>();
	}
}