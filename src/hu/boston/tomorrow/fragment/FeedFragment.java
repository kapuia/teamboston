package hu.boston.tomorrow.fragment;

import hu.boston.tomorrow.R;
import hu.boston.tomorrow.adapter.EventsAdapter;
import hu.boston.tomorrow.model.MainModel;
import hu.boston.tomorrow.task.GetEventsTask;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class FeedFragment extends Fragment {

	private ListView mListView;
	private EventsAdapter mAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_feed, container, false);

		GetEventsTask gsm = new GetEventsTask(getActivity());

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			gsm.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		} else {
			gsm.execute();
		}

		mAdapter = new EventsAdapter(getActivity(), MainModel.getInstance().events);

		mListView = (ListView) v.findViewById(R.id.listView);
		mListView.setAdapter(mAdapter);

		return v;

	}
}
