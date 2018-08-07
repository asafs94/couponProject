package exceptions;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * An <b style="color:purple">enum</b> that describes the error type that caused an exception in Coupon System..
 * @author asafs94
 *
 */

@XmlRootElement
public enum ErrorType {
	GENERIC_SYSTEM_ERROR,
	CUSTOMER_DOESNT_EXIST, COMPANY_DOESNT_EXIST,
	COMPANY_EXISTS_BY_NAME, CUSTOMER_EXISTS_BY_NAME, COUPON_EXISTS_BY_NAME,
	COMPANY_NAME_DOESNT_MATCH, CUSTOMER_NAME_DOESNT_MATCH, COUPON_NAME_DOESNT_MATCH,
	WRONG_TYPE_ENTERED_INTO_CHECKER,
	CUSTOMER_ALREADY_HAS_COUPON, COUPON_EXPIRED_OR_OUT_OF_STOCK,
	LOGIN_UNSUCCESSFULL, 
	NAME_TOO_LONG, PASSWORD_TOO_LONG;
}
