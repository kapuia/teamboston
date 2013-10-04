package hu.boston.tomorrow.task;

import hu.boston.tomorrow.model.ImageModel;
import hu.boston.tomorrow.model.MainModel;
import hu.boston.tomorrow.model.Message;
import hu.boston.tomorrow.model.User;
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

public class GetUserProfileTask extends AsyncTask<Void, Void, JSONObject> {

	private Context mContext;
	private WebServiceConnector mConn;
	private String mUserId;

	public GetUserProfileTask(Context context, String userId) {
		mContext = context;
		mUserId = userId;
	}

	// TODO - Eltarolni
	@Override
	protected void onPostExecute(JSONObject result) {

		User user = new User();

		try {
			user.setUserId(result.getString("UserId"));
			user.setUserName(result.getString("Username"));
			user.setDisplayName(result.getString("DisplayName"));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		mConn = new WebServiceConnector(mContext);
		super.onPreExecute();
	}

	@Override
	protected JSONObject doInBackground(Void... params) {

		try {
			JSONObject response = mConn.getUserProfile(mUserId);
			return response;

		} catch (Throwable e) {
			return new JSONObject();
		}
	}

}
