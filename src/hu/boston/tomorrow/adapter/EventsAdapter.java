package hu.boston.tomorrow.adapter;

import hu.boston.tomorrow.R;
import hu.boston.tomorrow.model.Event;
import hu.boston.tomorrow.model.MainModel;
import hu.boston.tomorrow.widget.FontableTextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class EventsAdapter extends ArrayAdapter<Event> {

	private Context mContext;
	private ArrayList<Event> mObjects;
	private SimpleDateFormat mDateFormat;

	public EventsAdapter(Context context, ArrayList<Event> items) {
		super(context, R.layout.menu_frame, items);

		mContext = context;
		mObjects = items;

		mDateFormat = new SimpleDateFormat("EEEE, MMMM, d");
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		EventItemViewHolder viewHolder = null;

		if (convertView == null) {

			convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_item, parent, false);

			viewHolder = new EventItemViewHolder();
			viewHolder.title = (FontableTextView) convertView.findViewById(R.id.title);
			viewHolder.place = (FontableTextView) convertView.findViewById(R.id.place);
			viewHolder.date = (FontableTextView) convertView.findViewById(R.id.date);
			viewHolder.attendeesCount = (FontableTextView) convertView.findViewById(R.id.attendes);
			viewHolder.description = (FontableTextView) convertView.findViewById(R.id.description);
			viewHolder.image = (ImageView) convertView.findViewById(R.id.image);

			convertView.setTag(viewHolder);

		} else {
			viewHolder = (EventItemViewHolder) convertView.getTag();
		}

		viewHolder.title.setText(mObjects.get(position).getTitle());
		// viewHolder.place.setText(mObjects.get(position).get)

		viewHolder.description.setText(mObjects.get(position).getDescription());
		viewHolder.date.setText(mDateFormat.format(mObjects.get(position).getStartTime()) + "\n"
				+ mDateFormat.format(mObjects.get(position).getEndTime()));

		if (mObjects.get(position).getImageModel() != null && mObjects.get(position).getImageModel().getUrl() != null)
			Picasso.with(mContext).load(mObjects.get(position).getImageModel().getUrl()).into(viewHolder.image);

		// Picasso.with(mContext).load(mObjects.get(position)).placeholder(null)
		// .into(viewHolder.dummyItem);

		return convertView;
	}

	static class EventItemViewHolder {
		FontableTextView title;
		FontableTextView place;
		FontableTextView date;
		FontableTextView date_ago;
		FontableTextView attendeesCount;
		FontableTextView description;
		ImageView image;
	}
}