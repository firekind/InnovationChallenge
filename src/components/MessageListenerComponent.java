package components;

import callback.Callback;
import engine.Message;
import entity.Entity;

public class MessageListenerComponent extends Component{

	private Callback c;
	private Object[] data;

	public MessageListenerComponent(Entity attachedTo, Callback c, Object... data) {
		super(ComponentID.messageListener, attachedTo);
		this.c = c;
		this.data = data;
		
	}
	
	public void sendMessage(Message message) {
		c.execute(data, message);
	}

}
