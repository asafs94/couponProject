package exceptions;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * A main Application Exception of Coupon System.
 * 
 * @author asafs94
 *
 */
@XmlRootElement
public class ApplicationException extends Exception {

	private static final long serialVersionUID = 1L;
	private ErrorType type;

	/**
	 * A CTR for the exception, accepting an Exception object and an ErrorType
	 * <b style="color:purple">enum</b>, describing the error.
	 * 
	 * @param e
	 * @param type
	 */
	public ApplicationException(Exception e, ErrorType type) {
		super(e);
		this.setType(type);
	}

	/**
	 * A CTR for the Exception, accepting an ErrorType
	 * <b style="color:purple">enum</b> describing the error.
	 * 
	 * @param type
	 */
	public ApplicationException(ErrorType type) {
		this.setType(type);
	}

	/**
	 * A getter for the ErrorType of this Exception.
	 * 
	 * @return ErrorType <a style="color:blue">type</a>
	 */
	public ErrorType getErrorType() {
		return type;
	}

	/**
	 * Sets the type of this Exception.
	 * 
	 * @param type
	 */
	public void setType(ErrorType type) {
		this.type = type;
	}

}
