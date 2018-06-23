package engine;

import entity.Entity;

public class MessageHandler {

	public void broadcast(Message message) {
		for(int i=0;i<message.getTargets().size();i++) {
			Entity e = message.getTargets().get(i);
			e.messageListener.sendMessage(message);
		}
	}
	
	
}
