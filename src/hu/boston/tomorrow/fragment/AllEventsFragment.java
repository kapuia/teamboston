package hu.boston.tomorrow.fragment;

import hu.boston.tomorrow.R;
import hu.boston.tomorrow.adapter.EventsAdapter;
import hu.boston.tomorrow.events.EventChangedEvent;
import hu.boston.tomorrow.events.EventsDownloadedEvent;
import hu.boston.tomorrow.managers.EventBusManager;
import hu.boston.tomorrow.model.Event;
import hu.boston.tomorrow.model.MainModel;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class AllEventsFragment extends Fragment {

	private ListView mListView;
	private EventsAdapter mAdapter;
	private EventBus eventBus;

	public AllEventsFragment() {

		eventBus = EventBusManager.getInstance();
		eventBus.register(this);
	}

	// TODO - Loading anim until the data is unavailable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_allevents, container, false);

		// TODO - Check if events is null
		mAdapter = new EventsAdapter(getActivity(), MainModel.getInstance().events);

		mListView = (ListView) v.findViewById(R.id.listView);
		mListView.setAdapter(mAdapter);

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

				Event event = mAdapter.getItem(arg2);
				MainModel.getInstance().selectedEvent = event;

				eventBus.post(new EventChangedEvent());
			}
		});

		return v;
	}

	@Subscribe
	public void eventsDownloaded(EventsDownloadedEvent event) {

		mAdapter = new EventsAdapter(getActivity(), MainModel.getInstance().events);
		mListView.setAdapter(mAdapter);

	}
}
