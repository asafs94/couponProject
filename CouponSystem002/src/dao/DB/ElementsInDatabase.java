package dao.DB;

/**
 * A still unused class, need to check if may use it in the future to replace
 * and make better understandable features in DBDAO. need to check about
 * injection possibilities first.
 * THIS CLASS IS STILL UNUSED AND MAY BE DELETED;
 * 
 * @author asafs
 *
 */
public enum ElementsInDatabase {
	COMPANY("company"), COUPON("coupon"), CUSTOMER("customer"), CUSTOMER_COUPON("customer_coupon");

	String tableName;

	private ElementsInDatabase(String tableName) {
		this.tableName = tableName;
	}

	public enum CompanyColumns {
		ID(1, "id"), COMPANY_NAME(2, "comp_name"), PASSWORD(3, "password"), EMAIL(4, "email");

		int columnIndex;
		String columnName;

		private CompanyColumns(int columnIndex, String columnName) {
			this.columnIndex = columnIndex;
			this.columnName = columnName;
		}
	}

	public enum CouponColumns {
		ID(1, "id"), TITLE(2, "title"), START_DATE(3, "start_date"), END_DATE(4, "end_date"), AMOUNT(5, "amount"), TYPE(
				6, "type"), MESSAGE(7, "message"), PRICE(8, "price"), IMAGE(9, "image"), COMPANY_ID(10, "company_id");

		int columnIndex;
		String columnName;

		private CouponColumns(int columnIndex, String columnName) {
			this.columnIndex = columnIndex;
			this.columnName = columnName;
		}

	}

	public enum CustomerColumns {
		ID(1, "id"), CUSTOMER_NAME(2, "cust_name"), PASSWORD(3, "password");

		int columnIndex;
		String columnName;

		private CustomerColumns(int columnIndex, String columnName) {
			this.columnIndex = columnIndex;
			this.columnName = columnName;
		}
	}

	public enum CustomerCouponColumns {
		CUSTOMER_ID(1, "cust_id"), COUPON_ID(2, "coupon_id");

		int columnIndex;
		String columnName;

		private CustomerCouponColumns(int columnIndex, String columnName) {
			this.columnIndex = columnIndex;
			this.columnName = columnName;
		}
	}

}
