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

public class SendMessageTask extends AsyncTask<Void, Void, JSONArray> {

	private Context mContext;
	private WebServiceConnector mConn;
	private String mEventId;
	private String mSubject;
	private String mContent;
	
	public SendMessageTask(Context context, String eventId, String subject, String content) {
		mContext = context;
		mEventId = eventId;
		mSubject = subject;
		mContent = content;
	}
	
	@Override
	protected void onPostExecute(
			JSONArray result) {
		
//		MainModel.getInstance().messages = new ArrayList<Message>();
//		SimpleDateFormat format = MainModel.getInstance().format;
//		ImageModel imageModel;
//		User sender;
//		
//		for(int i = 0; i < result.length(); i++) {
//			try {
//				JSONObject json = (JSONObject) result.get(i);
//				
//				Message message = new Message();
//				message.setMessageId(json.getString("MessageId"));
//				message.setSubject(json.getString("Subject"));
//				message.setContent(json.getString("Content"));
//
//				try {  
//					message.setDate(format.parse(json.getString("Date")));    
//				} catch (ParseException e) {  
//					message.setDate(new Date());
//				    e.printStackTrace();  
//				}
//				
//				// Image
//				imageModel = new ImageModel();
//				try {
//					JSONObject imageModelObject = json.getJSONObject("Image");
//					if(imageModelObject != null) {
//						imageModel.setUrl(imageModelObject.getString("Url"));
//					}
//				} catch (JSONException e) {
//					
//				}
//				
//				// Sender
//				sender = new User();
//				JSONObject senderObject = json.getJSONObject("Sender");
//				sender.setUserId(senderObject.getString("UserId"));
//				sender.setUserName(senderObject.getString("Username"));
//				sender.setDisplayName(senderObject.getString("DisplayName"));
//				
//				message.setSender(sender);
//				
//				MainModel.getInstance().messages.add(message);
//				
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//		}
		
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
			JSONObject response = mConn.sendMessage(mEventId, mSubject, mContent);
			JSONArray array = response.getJSONArray("Messages"); 
			
			return array;

		} catch (Throwable e) {
			return new JSONArray();
		}
	}
	
}
