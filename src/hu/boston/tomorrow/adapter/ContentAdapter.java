package hu.boston.tomorrow.adapter;

import hu.boston.tomorrow.R;
import hu.boston.tomorrow.model.EventContent;
import hu.boston.tomorrow.widget.FontableTextView;

import java.net.URI;
import java.util.ArrayList;

import android.content.Context;
import android.net.Uri;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class ContentAdapter extends ArrayAdapter<String> {

	private Context mContext;
	private ArrayList<String> mObjects;

	public ContentAdapter(Context context, ArrayList<String> items) {
		super(context, R.layout.menu_frame, items);

		mContext = context;
		mObjects = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ContentItemViewHolder viewHolder = null;

		String content = mObjects.get(position);

		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.content_item, parent, false);
			viewHolder = new ContentItemViewHolder();
			viewHolder.content = (FontableTextView) convertView.findViewById(R.id.content);
			viewHolder.image = (ImageView) convertView.findViewById(R.id.imageView);
			if (content.startsWith("http://")) {
				viewHolder.image.setImageURI(Uri.parse(content));

			} else {
				Spanned html = Html.fromHtml(content);
				viewHolder.content.setText(html);
			}

		} else {
			viewHolder = (ContentItemViewHolder) convertView.getTag();
		}

		// Picasso.with(mContext).load(mObjects.get(position)).placeholder(null)
		// .into(viewHolder.dummyItem);

		return convertView;
	}

	static class ContentItemViewHolder {
		FontableTextView content;
		ImageView image;
	}
}
