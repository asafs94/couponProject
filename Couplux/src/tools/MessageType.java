package tools;

/**
 * Represents the type of a message:
 * <ul>
 * 
 * <li>{@link tools.MessageType#SUCCESS SUCCESS}</li>
 * <li>{@link tools.MessageType#INFORMATION INFORMATION}</li>
 * <li>{@link tools.MessageType#FALIURE FALIURE}</li>
 * <li>{@link tools.MessageType#ERROR ERROR}</li>
 * <li>{@link tools.MessageType#FORBIDDEN_CONTENT FORBIDDEN_CONTENT}</li>
 * <li>{@link tools.MessageType#LOGIN_NEEDED LOGIN_NEEDED}</li>
 * 
 * </ul>
 * 
 * @author asafs
 *
 */
public enum MessageType {
	/**
	 * A Message which means action Performed Succeeded
	 */
	SUCCESS,
	/**
	 * An informative message.
	 */
	INFORMATION,
	/**
	 * A Message which means action Performed Failed, due to known reasons
	 */
	FALIURE,
	/**
	 * A Message which means action Performed Failed, due to unknown reasons
	 */
	ERROR,
	/**
	 * A Message which means that the content the user is trying to reach is
	 * forbidden to this user type.
	 */
	FORBIDDEN_CONTENT,
	/**
	 * A Message which means that the content the guest is trying to reach is
	 * forbidden to unLogged in users.
	 */
	LOGIN_NEEDED
}
