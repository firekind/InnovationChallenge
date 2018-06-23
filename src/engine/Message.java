package engine;

import java.util.LinkedList;

import entity.Entity;

public class Message {

	private LinkedList<Entity> targets;
	private String message;
	private Object[] data;
	
	public Message(LinkedList<Entity> targets, String message, Object...data) {
		this.targets = targets;
		this.message = message;
		this.data = data;
	}
	
	public String getMessage() {
		return message;
	}
	
	public Object[] getData() {
		return data;
	}

	public LinkedList<Entity> getTargets() {
		return targets;
	}	
}
