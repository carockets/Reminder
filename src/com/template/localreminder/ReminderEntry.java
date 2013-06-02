package com.template.localreminder;

public class ReminderEntry {
	private long id;
	private String title;
	private String description;
	
	public ReminderEntry() {
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getId() {
		return this.id;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String toString(){
		return description;
	}
}
