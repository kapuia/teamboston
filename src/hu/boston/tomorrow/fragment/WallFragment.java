package hu.boston.tomorrow.fragment;

import hu.boston.tomorrow.Constants;
import hu.boston.tomorrow.R;
import hu.boston.tomorrow.activity.ImagePagerActivity;
import hu.boston.tomorrow.adapter.FeedAdapter;
import hu.boston.tomorrow.events.MessagesDownloadedEvent;
import hu.boston.tomorrow.managers.EventBusManager;
import hu.boston.tomorrow.model.MainModel;
import hu.boston.tomorrow.model.Message;
import hu.boston.tomorrow.task.GetMessagesTask;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class WallFragment extends Fragment {

	private ListView mListView;
	private FeedAdapter mAdapter;
	private EventBus eventBus;

	private Handler customHandler = new Handler();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		eventBus = EventBusManager.getInstance();
		eventBus.register(this);
		View v = inflater.inflate(R.layout.fragment_wall, container, false);

		GetMessagesTask gsm = new GetMessagesTask(getActivity(),
				MainModel.getInstance().selectedEvent.getEventId());

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			gsm.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		} else {
			gsm.execute();
		}

		mListView = (ListView) v.findViewById(R.id.listView);

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long id) {

				Intent intent = new Intent(getActivity(),
						ImagePagerActivity.class);
				Bundle value = new Bundle();

				Message message = MainModel.getInstance().selectedEvent
						.getMessages().get(position);
				value.putString("image", message.getImage().getUrl());
				value.putString("description", message.getContent());
				intent.putExtras(value);
				getActivity().startActivity(intent);

			}
		});

		customHandler.postDelayed(updateTimerThread, 3000);

		return v;

	}

	private Runnable updateTimerThread = new Runnable() {
		public void run() {

			Log.d("DEBUG", "START ASYNC");

			// TODO - Figyelni, hogy lefutott-e az elozo
			GetMessagesTask gsm = new GetMessagesTask(getActivity(),
					MainModel.getInstance().selectedEvent.getEventId());
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
				gsm.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
			} else {
				gsm.execute();
			}

			customHandler.postDelayed(this, 3000);

		}

	};

	@Subscribe
	public void wallUpdted(MessagesDownloadedEvent event) {
		Log.d("DEBUG", "RESULT!!! "
				+ MainModel.getInstance().selectedEvent.getMessages().size());

		if (mAdapter == null) {
			mAdapter = new FeedAdapter(getActivity(),
					MainModel.getInstance().selectedEvent.getMessages());
			mListView.setAdapter(mAdapter);
		} else {
			mAdapter.setObjects(MainModel.getInstance().selectedEvent
					.getMessages());
			mAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onPause() {
		if (customHandler != null) {
			customHandler.removeCallbacks(updateTimerThread);
			customHandler = null;
			eventBus.unregister(this);
		}
		super.onPause();
	}
}
