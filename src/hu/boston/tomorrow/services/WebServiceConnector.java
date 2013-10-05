package hu.boston.tomorrow.services;

import hu.boston.tomorrow.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public class WebServiceConnector {

	Context context;

	public WebServiceConnector(Context context) {
		super();
		this.context = context;
	}

	private String execute(String url, List<NameValuePair> nameValuePairs) throws ClientProtocolException, IOException {
		String response = "";

		String paramString = URLEncodedUtils.format(nameValuePairs, "utf-8");
		url += paramString;

		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);

		ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
			public String handleResponse(final HttpResponse response) throws HttpResponseException, IOException {
				StatusLine statusLine = response.getStatusLine();
				if (statusLine.getStatusCode() >= 300) {
					throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
				}

				HttpEntity entity = response.getEntity();
				return entity == null ? null : EntityUtils.toString(entity, "UTF-8");
			}
		};

		response = httpclient.execute(httpget, responseHandler);
		return response;
	}

	// TODO - Image
	private String executePost(String url, List<NameValuePair> nameValuePairs) throws ClientProtocolException, IOException {

		String response = "";

		String paramString = URLEncodedUtils.format(nameValuePairs, "utf-8");
		url += paramString;

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);

		List<NameValuePair> postNameValuePairs = new ArrayList<NameValuePair>(1);
		// TODO - Image

		httppost.setEntity(new UrlEncodedFormEntity(postNameValuePairs));

		ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
			public String handleResponse(final HttpResponse response) throws HttpResponseException, IOException {
				StatusLine statusLine = response.getStatusLine();
				if (statusLine.getStatusCode() >= 300) {
					throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
				}

				HttpEntity entity = response.getEntity();
				return entity == null ? null : EntityUtils.toString(entity, "UTF-8");
			}
		};

		response = httpclient.execute(httppost, responseHandler);
		return response;
	}

	public JSONObject getEventsList() throws ClientProtocolException, IOException {

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("userId", Constants.USER_ID));

		String url = Constants.WEB_SERVICE_URL + "Events/List" + "?";

		JSONObject json;

		try {
			json = new JSONObject(execute(url, nameValuePairs));
		} catch (JSONException e) {
			e.printStackTrace();
			return new JSONObject();
		}

		return json;
	}

	public JSONObject getEventContents(String eventId) throws ClientProtocolException, IOException {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("userId", Constants.USER_ID));
		nameValuePairs.add(new BasicNameValuePair("eventId", eventId));

		String url = Constants.WEB_SERVICE_URL + "Events/Contents" + "?";

		JSONObject json;

		try {
			json = new JSONObject(execute(url, nameValuePairs));
		} catch (JSONException e) {
			e.printStackTrace();
			return new JSONObject();
		}

		return json;
	}

	public JSONObject getMessagesList(String eventId) throws ClientProtocolException, IOException {

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("userId", Constants.USER_ID));
		nameValuePairs.add(new BasicNameValuePair("eventId", eventId));

		String url = Constants.WEB_SERVICE_URL + "Events/Messages" + "?";

		JSONObject json;

		try {
			json = new JSONObject(execute(url, nameValuePairs));
		} catch (JSONException e) {
			e.printStackTrace();
			return new JSONObject();
		}

		return json;
	}

	public JSONObject sendMessage(String eventId, String subject, String content) throws ClientProtocolException, IOException {

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("userId", Constants.USER_ID));
		nameValuePairs.add(new BasicNameValuePair("eventId", eventId));
		nameValuePairs.add(new BasicNameValuePair("subject", subject));
		nameValuePairs.add(new BasicNameValuePair("content", content));

		String url = Constants.WEB_SERVICE_URL + "Events/SendMessage" + "?";

		JSONObject json;

		try {
			json = new JSONObject(executePost(url, nameValuePairs));
		} catch (JSONException e) {
			e.printStackTrace();
			return new JSONObject();
		}

		return json;
	}

	public JSONObject getUserProfile(String userId) throws ClientProtocolException, IOException {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("userId", Constants.USER_ID));

		String url = Constants.WEB_SERVICE_URL + "Users/Profile" + "?";

		JSONObject json;

		try {
			json = new JSONObject(execute(url, nameValuePairs));
		} catch (JSONException e) {
			e.printStackTrace();
			return new JSONObject();
		}

		return json;
	}
}