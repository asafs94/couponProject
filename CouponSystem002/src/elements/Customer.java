package elements;

import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;
/**
 * A class depicting a Customer.
 * @author asafs94
 *
 */
@XmlRootElement
public class Customer {
	private long id;
	private String custName;
	private String password;
	private Collection<Coupon> coupons;
	
	
	public Customer() {}
	
	/**
	 * A CTR for a customer, taking in all parameters.
	 * For reading a Customer from the database.
	 * @param id
	 * @param custName
	 * @param password
	 * @param coupons
	 */
	public Customer(long id, String custName, String password, Collection<Coupon> coupons) {
		super();
		this.id = id;
		this.custName = custName;
		this.password = password;
		this.coupons = coupons;
	}

	/**
	 * A CTR for a customer, taking in all parameters, but id.
	 * For creation of a new Customer.
	 * @param custName
	 * @param password
	 * @param coupons
	 */
	public Customer(String custName, String password, Collection<Coupon> coupons) {
		super();
		this.custName = custName;
		this.password = password;
		this.coupons = coupons;
	}

	//--------------- Getters and Setters--------------
	
/**
 * Returns a Customer's name.	
 * @return String <a style="color:blue">custName</a>
 */
	public String getCustName() {
		return custName;
	}
/**
 * Returns a Customer's id.
 * @return <b style="color:purple">long</b> <a style="color:blue">id</a>
 */
	public long getId() {
		return id;
	}
/**
 * Sets this Customer's id.
 * @param id
 */
	public void setId(long id) {
		this.id = id;
	}
/**
 * Sets this Customer's name.
 * @param custName
 */
	public void setCustName(String custName) {
		this.custName = custName;
	}
/**
 * Returns this Customer's password.
 * @return String <a style="color:blue">password</a>
 */
	public String getPassword() {
		return password;
	}
/**
 * Sets this Customer's password.
 * @param password
 */
	public void setPassword(String password) {
		this.password = password;
	}
/**
 * Return's a Collection of this customer's coupons.
 * @return Collection of Coupon <a style="color:blue">coupons</a>
 */
	public Collection<Coupon> getCoupons() {
		return coupons;
	}
/**
 * Set's this Customer's Collection of coupons.
 * @param coupons
 */
	public void setCoupons(Collection<Coupon> coupons) {
		this.coupons = coupons;
	}

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + (int) (id ^ (id >>> 32));
	return result;
}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Customer other = (Customer) obj;
	if (id != other.id)
		return false;
	return true;
}
	
	
	
	
}
