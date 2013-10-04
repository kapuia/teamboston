package hu.boston.tomorrow.fragment;

import hu.boston.tomorrow.Constants;
import hu.boston.tomorrow.R;
import hu.boston.tomorrow.task.GetEventContentsTask;
import hu.boston.tomorrow.task.GetEventsTask;
import hu.boston.tomorrow.task.GetMessagesTask;
import hu.boston.tomorrow.task.GetUserProfileTask;
import hu.boston.tomorrow.task.SendMessageTask;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FeedFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_feed, container, false);

		//GetEventsTask gsm = new GetEventsTask(getActivity());
		//GetEventContentsTask gsm = new GetEventContentsTask(getActivity(), Constants.DUMMY_EVENT_ID);
		//GetMessagesTask gsm = new GetMessagesTask(getActivity(), Constants.DUMMY_EVENT_ID);
		//SendMessageTask gsm = new SendMessageTask(getActivity(), Constants.DUMMY_EVENT_ID, "bla", "HI!");
		GetUserProfileTask gsm = new GetUserProfileTask(getActivity(), Constants.DUMMY_USER_ID);
		
		//TaskHandler.getInstance().add(gsm);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			gsm.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		} else {
			gsm.execute();
		}

		return v;
	}
}
