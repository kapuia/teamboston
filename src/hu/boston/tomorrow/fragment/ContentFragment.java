package hu.boston.tomorrow.fragment;

import hu.boston.tomorrow.R;
import hu.boston.tomorrow.model.EventContent;
import hu.boston.tomorrow.model.MainModel;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.TextView;

public class ContentFragment extends Fragment {

	private EventContent eventContent;

	public ContentFragment() {
		super();
		eventContent = MainModel.getInstance().selectedContent;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_content, container, false);

		TextView tv = (TextView) v.findViewById(R.id.textView);
		tv.setText(eventContent.getTitle());

		WebView webView = (WebView) v.findViewById(R.id.webView);
		webView.loadData(eventContent.getBody().get(0), "text/html", "utf-8");

		// webView.setInitialScale(1);
		// webView.getSettings().setLoadWithOverviewMode(true);
		// webView.getSettings().setUseWideViewPort(true);
		// webView.getSettings().setJavaScriptEnabled(true);

		// webView.setPadding(0, 0, 0, 0);
		// webView.setInitialScale(getScale());

		// ListView listView = (ListView) v.findViewById(R.id.listView);
		//
		// String[] stringArray = new String[eventContent.getBody().size()];
		// for (int i = 0; i < eventContent.getBody().size(); i++)
		// stringArray[i] = eventContent.getBody().get(i);
		//
		// ContentAdapter adapter = new ContentAdapter(this.getActivity(),
		// eventContent.getBody());
		// listView.setAdapter(adapter);

		return v;
	}

	// private int getScale() {
	// Display display = ((WindowManager)
	// getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
	// int width = display.getWidth();
	// Double val = new Double(width) / new Double(PIC_WIDTH);
	// val = val * 100d;
	// return val.intValue();
	// }
}
