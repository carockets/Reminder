package com.template.localreminder;

public class ReminderEntry {
	private long id;
	private int alertId;
	private String title;
	private String description;
	private String alertToString;
	
	public ReminderEntry (long id, String title, String description) {
		this.id = id;
		this.title = title;
		this.description = description;
	}
	
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
	
	public void setAlertId(int id) {
		this.alertId = id;
	}
	
	public int getAlertId() {
		return alertId;
	}
	
	public void setAlertText (String alertText) {
		this.alertToString = alertText;
	}
	
	public String getAlertText () {
		return alertToString;
	}
	
	public String toString(){
		return description;
	}
}
