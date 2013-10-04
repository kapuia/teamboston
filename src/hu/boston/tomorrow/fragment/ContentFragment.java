package hu.boston.tomorrow.fragment;

import hu.boston.tomorrow.R;
import hu.boston.tomorrow.adapter.ContentAdapter;
import hu.boston.tomorrow.model.EventContent;
import hu.boston.tomorrow.model.MainModel;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

		ListView listView = (ListView) v.findViewById(R.id.listView);

		String[] stringArray = new String[eventContent.getBody().size()];
		for (int i = 0; i < eventContent.getBody().size(); i++)
			stringArray[i] = eventContent.getBody().get(i);

		ContentAdapter adapter = new ContentAdapter(this.getActivity(), eventContent.getBody());
		listView.setAdapter(adapter);
		return v;
	}
}
