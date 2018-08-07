package elements;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * A Class for depicting a Coupon in the Coupon System
 * 
 * @author asafs94
 *
 */
@XmlRootElement
public class Coupon {
	private long id;
	private String title;
	private Date startDate;
	private Date endDate;
	private int amount;
	private CouponType type;
	private String message;
	private double price;
	private String image;
	/**
	 * The ID of the company this coupon belongs to. This parameter has been added,
	 * since i decided to skip a company_coupon table. because it is not logically
	 * correct. a Join Table is made when the ration between two columns is Many to
	 * Many, here the ratio is One To Many
	 */
	private long company_id;

	/**
	 * A CTR for Coupon Containing all parameters. For reading a coupon object from
	 * the database.
	 * 
	 * @param id
	 * @param title
	 * @param startDate
	 * @param endDate
	 * @param amount
	 * @param type
	 * @param message
	 * @param price
	 * @param image
	 * @param company_id
	 */

	public Coupon() {}
	
	public Coupon(long id, String title, Date startDate, Date endDate, int amount, CouponType type, String message,
			double price, String image, long company_id) {
		super();
		this.id = id;
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.type = type;
		this.message = message;
		this.price = price;
		this.image = image;
		this.company_id = company_id;
	}

	/**
	 * A CTR containing all parameters but id. For a creation of a new coupon.
	 * 
	 * @param title
	 * @param startDate
	 * @param endDate
	 * @param amount
	 * @param type
	 * @param message
	 * @param price
	 * @param image
	 * @param company_id
	 */
	public Coupon(String title, Date startDate, Date endDate, int amount, CouponType type, String message, double price,
			String image, long company_id) {
		super();
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.type = type;
		this.message = message;
		this.price = price;
		this.image = image;
		this.company_id = company_id;
	}

	// ------------------Getters and Setters---------------------

	/**
	 * Return's this Coupon's title.
	 * 
	 * @return String <a style="color:blue">title</a>
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets this Coupon's title
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Return's this Coupon's Creation date.
	 * 
	 * @return Date <a style="color:blue">startDate</a>
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * Sets this Coupon's Creation Date.
	 * 
	 * @param startDate
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Returns this Coupon's Expiration Date.
	 * 
	 * @return Date <a style="color:blue">endDate</a>
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * Set's this Coupon's Expiration Date.
	 * 
	 * @param endDate
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * Returns the amount of this Coupon in Stock.
	 * 
	 * @return <b style="color:purple">int</b> <a style="color:blue">amount</a>
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * Sets the amount of this Coupon in stock.
	 * 
	 * @param amount
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

	/**
	 * Returns the type of this Coupon.
	 * 
	 * @return CouponType <a style="color:blue">type</a>
	 */
	public CouponType getType() {
		return type;
	}

	/**
	 * Sets the type of this Coupon.
	 * 
	 * @param type
	 */
	public void setType(CouponType type) {
		this.type = type;
	}

	/**
	 * Return's the message of this Coupon.
	 * 
	 * @return String <a style="color:blue">message</a>
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message of this Coupon.
	 * 
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Returns the price of this Coupon.
	 * 
	 * @return <b style="color:purple">double</b> <a style="color:blue">price</a>
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * Sets this Coupon's price.
	 * 
	 * @param price
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * Returns the String of this Coupon's image.
	 * 
	 * @return String <a style="color:blue">image</a>
	 */
	public String getImage() {
		return image;
	}

	/**
	 * Sets the String of this Coupon's image.
	 * 
	 * @param image
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * Returns this Coupon's id.
	 * 
	 * @return <b style="color:purple">long</b> <a style="color:blue">id</a>
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets this Coupon's id.
	 * 
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Returns this Coupon's company id.
	 * 
	 * @return <b style="color:purple">long</b>
	 *         <a style="color:blue">company_id</a>.
	 */
	public long getCompany_id() {
		return company_id;
	}

	/**
	 * Sets this Coupon's company id.
	 * 
	 * @param company_id
	 */
	public void setCompany_id(long company_id) {
		this.company_id = company_id;
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
		Coupon other = (Coupon) obj;
		if (id != other.id)
			return false;
		return true;
	}

	// -----------G&S || End --------------

	// Coupon HashCode and Equals:

}
