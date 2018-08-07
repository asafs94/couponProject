package elements;

import javax.xml.bind.annotation.XmlRootElement;

import exceptions.ApplicationException;
import exceptions.ErrorType;

/**
 * An Enum containing all types of Coupons. To be later be converted into a
 * string and placed in the DB as a VarChar.
 * 
 * @author asafs94
 *
 */
@XmlRootElement
public enum CouponType {
	RESTAURANTS("Restaurants"), ELECTRICITY("Electricity"), FOOD("Food"), HEALTH("Health"), SPORTS("Sports"), CAMPING(
			"Camping"), TRAVELING("Traveling"), OTHER("Other");

	private String text;
	
	private CouponType() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * A CouponType CTR.
	 * @param type
	 */
	CouponType(String type) {
		this.text = type;
	}
	
	/**
	 * A CouponType getter.
	 * @return
	 */
	public String getText() {
		return this.text;
	}
	
	/**
	 * A CouponType fromString converter. mainly used for working with SQL Databases.
	 * @param text
	 * @return
	 * @throws ApplicationException 
	 */
	public static CouponType fromString(String text) throws ApplicationException {
		CouponType stringToType=null;
		for (CouponType type : CouponType.values()) {
			if (type.getText().equalsIgnoreCase(text)) {
				stringToType=type;
			}else continue;
		}
		if(stringToType==null) {
			throw new ApplicationException(ErrorType.GENERIC_SYSTEM_ERROR);
		}else {
			return stringToType;
		}
		
		
	}

}
