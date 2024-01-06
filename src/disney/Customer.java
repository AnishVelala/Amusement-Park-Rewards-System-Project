package disney;
//base class
public class Customer {
	
	private String firstName;
	private String lastName;
	private int guestID;
	private double amountSpent;
	
	
	
	
	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public int getGuestID() {
		return guestID;
	}


	public void setGuestID(int guestID) {
		this.guestID = guestID;
	}




	public double getAmountSpent() {
		return amountSpent;
	}




	public void setAmountSpent(double amountSpent) {
		this.amountSpent = amountSpent;
	}


	public Customer() {
		
		
		
	}


	public Customer(String firstName, String lastName, int guestID, double amountSpent) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.guestID = guestID;
		this.amountSpent = amountSpent;
	}
	
	
	
	
	
	
	
	
	
	
	

}
