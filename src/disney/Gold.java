package disney;

public class Gold extends Customer {
	
	private String discountPct;

	

	public Gold(String firstName, String lastName, int guestID, double amountSpent, String discountPct) {
		super(firstName, lastName, guestID, amountSpent);
		this.discountPct = discountPct;
	}

public Gold() {
	
	
}


	public String getDiscountPct() {
		return discountPct;
	}



	public void setDiscountPct(String discountPct) {
		this.discountPct = discountPct;
	}
	
	

	
	

}
