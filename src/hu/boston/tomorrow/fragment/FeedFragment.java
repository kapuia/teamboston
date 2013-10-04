package hu.boston.tomorrow.fragment;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import hu.boston.tomorrow.R;
import hu.boston.tomorrow.adapter.EventsAdapter;
import hu.boston.tomorrow.adapter.FeedAdapter;
import hu.boston.tomorrow.events.MessagesDownloadedEvent;
import hu.boston.tomorrow.managers.EventBusManager;
import hu.boston.tomorrow.model.MainModel;
import hu.boston.tomorrow.task.GetEventsTask;
import hu.boston.tomorrow.task.GetMessagesTask;
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
	private FeedAdapter mAdapter;
	private EventBus eventBus;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		eventBus = EventBusManager.getInstance();
		eventBus.register(this);
		View v = inflater.inflate(R.layout.fragment_feed, container, false);

		GetMessagesTask gsm = new GetMessagesTask(getActivity(), MainModel.getInstance().selectedEvent.getEventId());

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			gsm.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		} else {
			gsm.execute();
		}

		mListView = (ListView) v.findViewById(R.id.listView);
		
		return v;
		
		

	}

	@Subscribe
	public void wallUpdted(MessagesDownloadedEvent event) {
		mAdapter = new FeedAdapter(getActivity(), MainModel.getInstance().selectedEvent.getMessages());
		mListView.setAdapter(mAdapter);
	}
}
