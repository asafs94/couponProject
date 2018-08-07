package elements;

import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;
/**
 * A Class for depicting a Company in the Coupon System.
 * @author asafs94
 *
 */
@XmlRootElement
public class Company {
	private long id;
	private String compName;
	private String password;
	private String email;
	private Collection<Coupon> coupons;

	// --------------CTRs------------------

	public Company() {
		
	}
	
	/**
	 * A CTR for a company taking in all parameters but id. For creation purposes.
	 * 
	 * @param compName
	 * @param password
	 * @param email
	 * @param coupons
	 */
	public Company(String compName, String password, String email, Collection<Coupon> coupons) {
		super();
		setCompName(compName);
		setPassword(password);
		setEmail(email);
		setCoupons(coupons);
	}

	/**
	 * A CTR taking in all parameters. for reading from database purposes.
	 * 
	 * @param id
	 * @param compName
	 * @param password
	 * @param email
	 * @param coupons
	 */
	public Company(long id, String compName, String password, String email, Collection<Coupon> coupons) {
		super();
		setId(id);
		setCompName(compName);
		setPassword(password);
		setEmail(email);
		setCoupons(coupons); // ToDo: check if there's a need for another CTR without coupons later.
	}

	// -----------Getters and Setters-----------
	/**
	 * Returns this Company object's id.
	 * 
	 * @return <b style="color:purple">long</b> <a style="color:blue">id</a>
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets this Company object's id.
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Returns this Company object's name.
	 * @return String <a style="color:blue">compName</a>
	 */
	public String getCompName() {
		return compName;
	}
	/**
	 * Set's this Company's name.
	 * @param compName
	 */
	public void setCompName(String compName) {
		this.compName = compName;
	}
	/**
	 * Returns this Company object's password.
	 * @return String <a style="color:blue">password</a>
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * Sets this Company's password.
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * Returns this Company object's email.
	 * @return String <a style="color:blue">email</a>
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * Set's this Cpmpany's email.
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Returns this Company's coupons.
	 * @return Collection of Coupon <a style="color:blue">coupons</a>
	 */
	public Collection<Coupon> getCoupons() {
		return coupons;
	}

	/**
	 * Sets this Company's Collection of Coupons.
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
		Company other = (Company) obj;
		if (id != other.id)
			return false;
		return true;
	}

	// ------------End of G&T---------------------

	
}
