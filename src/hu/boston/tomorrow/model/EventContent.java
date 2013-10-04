package hu.boston.tomorrow.model;

import java.util.ArrayList;

public class EventContent {

	private String contentId;

	private String title;

	private ArrayList<String> body;

	private ImageModel image;

	private int order;

	private int contentType;

	public EventContent() {

	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ArrayList<String> getBody() {
		return body;
	}

	public void setBody(ArrayList<String> body) {
		this.body = body;
	}

	public ImageModel getImage() {
		return image;
	}

	public void setImage(ImageModel image) {
		this.image = image;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getContentType() {
		return contentType;
	}

	public void setContentType(int contentType) {
		this.contentType = contentType;
	}
}