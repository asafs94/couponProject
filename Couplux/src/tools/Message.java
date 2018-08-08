package tools;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * A Class representing an object which is passed to the client side.
 * This object will be shown at the client side.
 * contains 3 parameters:
 * <p>
 * <ol>
 * <li>{@link java.lang.String String} {@link tools.Message#title title}</li>
 * <li>{@link java.lang.String String}  {@link tools.Message#content content}</li>
 * <li>{@link tools.MessageType MessageType} {@link tools.Message#type type}</li>
 * </ol>
 * </p>
 * @author asafs
 *
 */
@XmlRootElement
public class Message {
	
	/**
	 * Represents the Message's title.
	 */
	String title;
	/**
	 * Represents the Message's content.
	 */
	String content;
	/**
	 * The {@link tools.MessageType type} of the message.
	 */
	MessageType type;
	
	public Message() {
	}
		
	
	public Message(String title, String content, MessageType type) {
		super();
		this.title = title;
		this.content = content;
		this.type= type;
		
	}



	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


	public MessageType getType() {
		return type;
	}


	public void setType(MessageType type) {
		this.type = type;
	}


	
	
	
	
}
