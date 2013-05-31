package com.template.localreminder;

public class ReminderEntry {
	private String item;
	private long id;
	
	public ReminderEntry() {
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getId() {
		return this.id;
	}
	
	public String toString(){
		return item;
	}
}
