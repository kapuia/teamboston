package hu.boston.tomorrow.services;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class WebServiceConnector {

	Context context;

	public WebServiceConnector(Context context) {
		super();
		this.context = context;
	}

	// TODO - userId-t atadni!
	// private String execute(String url, List<NameValuePair> nameValuePairs)
	private String execute(String url) throws ClientProtocolException,
			IOException {
		String response = "";
		// String paramString = URLEncodedUtils.format(nameValuePairs, "utf-8");

		// url += paramString;

		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);

		ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
			public String handleResponse(final HttpResponse response)
					throws HttpResponseException, IOException {
				StatusLine statusLine = response.getStatusLine();
				if (statusLine.getStatusCode() >= 300) {
					throw new HttpResponseException(statusLine.getStatusCode(),
							statusLine.getReasonPhrase());
				}

				HttpEntity entity = response.getEntity();
				return entity == null ? null : EntityUtils.toString(entity,
						"UTF-8");
			}
		};

		response = httpclient.execute(httpget, responseHandler);
		return response;
	}

	public String getEventsList() throws ClientProtocolException, IOException {
		// TODO - Atirni
		
		String response = execute("http://192.168.2.6/Boston/Events/List"); 
		
		try {
			JSONObject json = new JSONObject(response);
			Log.d("DEBUG", "aaa");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		
		return response;
	}
}
