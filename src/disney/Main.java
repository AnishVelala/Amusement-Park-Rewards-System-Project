package disney;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.*;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Scanner;

//Anish Velala



public class Main {
	
	// Declare an array to store regular customer data
	private static Customer[] array_regular = null;
	
	// Declare an array to store preferred customer data
	private static Object[] array_preferred = null;
	
	
	public static void main(String[] args) {
		
		
		 //array holds information about the customer's status (e.g., "Regular", "Gold", or "Platinum") and total spent by customer
		String [] custDetails = new String[2];
		
		
	/*	Object[] obj = new Object[10];a
		obj[1]= new Customer();
		obj[2] = new Gold();
		
		if (obj[1] instanceof Customer)
			
			
		else (obj[2] instanceof Gold)	
			
		*/
	
        
	
		// creates scanner and prompts the user to enter a filename for the three input files.
		Scanner scanner = new Scanner(System.in);
	       System.out.print("Enter the filename  ");
	       String filename1 = scanner.nextLine();
	        System.out.print("Enter the filename  ");
	    String filename2 = scanner.nextLine();
	       System.out.print("Enter the filename  ");
	       String filename3 = scanner.nextLine();
	        
	  
	  
	 
		
		  //String filename2 = "preferred3.dat"; 
		 //String filename3 = "orders3.dat";
		  
		 // String filename1 = "customer3.dat";
		 
	        
		
		
		  BufferedReader reader;
			try {
				  // Calculate the number of lines in the file.
				int numLines=countLines(filename1);
				array_regular = new Customer[numLines];
				reader = new BufferedReader(new FileReader(filename1));
		 String line;
		        int row = 0;
		        String [] tempCustomer = new String [4];
	            while ((line = reader.readLine()) != null) {
	            	  // Split the line into customer details.
	            	tempCustomer= line.split(" ");
	            	// Create a Customer object and set its attributes.
	                // Store the Customer object in the array of regular customers.
	            	Customer customer= new Customer();
	                customer.setGuestID(Integer.parseInt(tempCustomer[0]));
	            	customer.setFirstName(tempCustomer[1]);
	            	customer.setLastName(tempCustomer[2]);
	            	customer.setAmountSpent(Double.parseDouble(tempCustomer[3]));
	            	array_regular[row] = customer;
	            	row++;
	            }	
	            
	            
	            
	            
	          try {
	        	  numLines=countLines(filename2);	
		          array_preferred = new Customer[numLines];
		          
				reader = new BufferedReader(new FileReader(filename2));
	            int row1 = 0;
	            String [] preferredCustomer= new String[5];
	            while ((line = reader.readLine()) != null){
	            	preferredCustomer= line.split(" ");
	            	// Check if the amount spent by the customer is greater than 200 (indicating a Platinum customer).
	      if ((Double.parseDouble(preferredCustomer[3]) > 200)) {
	            		Platinum platinumCustomer= new Platinum();
	            		 // Set the attributes of the Platinum customer based on the input data.
		            	 platinumCustomer.setGuestID(Integer.parseInt( preferredCustomer[0]));
		            	 platinumCustomer.setFirstName(preferredCustomer[1]);
		            	 platinumCustomer.setLastName(preferredCustomer[2]);
		            	 platinumCustomer.setAmountSpent(Double.parseDouble(preferredCustomer[3]));
		            	 platinumCustomer.setBonusBucks(Integer.parseInt(preferredCustomer[4]));
		            	 array_preferred[row1] = platinumCustomer;
			
	            	}
	            	
	            	else {
	            		
	            	Gold goldCustomer= new Gold();
	            	 goldCustomer.setGuestID(Integer.parseInt( preferredCustomer[0]));
		            	goldCustomer.setFirstName(preferredCustomer[1]);
		            	goldCustomer.setLastName(preferredCustomer[2]);
		            	goldCustomer.setAmountSpent(Double.parseDouble(preferredCustomer[3]));
		            	goldCustomer.setDiscountPct(preferredCustomer[4]);
		            	array_preferred[row1] = goldCustomer;
		            	
		            	
		            	
	            	}
		            	row1++;
	            	
	            }
	          } catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}	
	            
	            
	            
	            
	            reader = new BufferedReader(new FileReader(filename3));
	           int customerID =0; 
	           double totalCost = 0;
	        // Initialize an array to store order details (customer ID, size, drink type, quantity).
	  		        String [] orders = new String [4];
	  		        
	  		        
	  		      while ((line = reader.readLine()) != null) {
	  		    	
	  	            	orders= line.split(" ");
	  		    	    if (!validateInput(orders))
	  		    	    	continue;
	  		    	    
	  		    	   
	  	            	
	  	            	customerID= Integer.parseInt(orders[0]);
	  	           // Calculate the total cost of the order using order details and predefined functions.
         totalCost =  (getGraphicCost( orders[1], Double.parseDouble(orders[3]))  +  calculateDrinkPrice(orders[2],orders[1])) * Integer.parseInt(orders[4]);
	  	            	
	  	  double totalSpent=0; 
	  	  double tempTotalSpent= 0;
	  	// Get the customer's status and total spent from their records.
          custDetails  = getCustomerStatusandTotalspent(customerID, array_regular, array_preferred);
          
          if (custDetails != null) {
          
         if (custDetails[0].equals("Regular")){
        	// Calculate the temporary total spent by adding the current order's cost to the existing total spent.
        	tempTotalSpent= totalCost +  Double.parseDouble(custDetails[1]);;
        	totalSpent = tempTotalSpent;
        	
        	 if (tempTotalSpent >= 50) {
        		// Calculate the discount percentage for Gold customers based on the total spent.
        		  int discountPct = getGolcCustomerDiscountPercentage(totalSpent);
        		  // Apply the discount to the total cost of the order.
        		  totalCost = calculateTotaltAfterDiscount(totalCost, discountPct);
        		  // Recalculate the total spent after applying the discount.
        		  totalSpent = totalCost + Double.parseDouble(custDetails[1]);
        		// Convert the customer from Regular to Gold and update their records.
        	 		convertRegularToGold(customerID,totalSpent);
        	 	// Update the amount and discount percentage for Gold/Platinum customers.
        	 		updateGoldPlatCustomerAmount(customerID, totalSpent);
        	 	}
        	 
        	 else {
        		 // Update the total amount spent for Regular customers without a discount.
        	 updateCustomerAmount(customerID, totalSpent);
        	 }
          }
         
         else if(custDetails[0].equals("Gold")) {
        	// Calculate the discount percentage for Gold customers based on the total spent with the current order.
        	  int discountPct = getGolcCustomerDiscountPercentage(totalCost +  Double.parseDouble(custDetails[1]));
        	// Calculate the total spent after applying the discount.
        	 totalSpent= calculateTotaltAfterDiscount(totalCost, discountPct);
        	// Update the amount and discount percentage for Gold/Platinum customers.
        	 updateGoldPlatCustomerAmount(customerID, totalSpent +  Double.parseDouble(custDetails[1]));
        	 // Check if the updated total spent is greater than $200 to possibly upgrade to Platinum.
        	 if (totalSpent +  Double.parseDouble(custDetails[1]) > 200) {
        		 // Upgrade the Gold customer to Platinum.
        		 updateGoldToPlatinum(customerID);
        	 }
           	
	  	 } else if (custDetails[0].equals("Platinum")) {
	  		 // For Platinum customers, calculate the total bonus bucks they have earned.
	  		 
	  		 int totalBucks = getExistingBonusBucks(customerID);
	  		 // Check if the customer has earned any bonus bucks.
	  		 if (totalBucks > 0 ) {
	  			 // Subtract the bonus bucks from the total cost.
	  		  totalCost = totalCost-totalBucks;     
	  		 // Update the total spent with the current order.
	  		  totalSpent = totalCost + Double.parseDouble(custDetails[1]);  
	  		            
	  		 }
	  	   // Calculate the bonus bucks based on the updated total cost.
             int bonusBucks = getBonusBucksFofCurrentSpent(totalCost);
             // Update the bonus bucks and the total amount spent for the Platinum customer.
             updatePlatinumCustomerBonusBucks(customerID, bonusBucks);
             updateGoldPlatCustomerAmount(customerID, totalSpent);
         }
         
         
         
         
         else
        	 // If the customer type is neither "Gold" nor "Platinum," update the total amount spent.
        	 updateGoldPlatCustomerAmount(customerID, totalSpent);
          }
        
	   }          
         
	      
	            } catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
	            
			writeCustomerDataToFile(array_regular);
			writePreferredCustomerDataToFile(array_preferred);
	       //System.out.print("Done");
			
	}
	
	
	

	//This function determines the customer's status and retrieves their total amount spent from the provided arrays.
	private static String[] getCustomerStatusandTotalspent( int customerID,Customer[] array_regular, Object[] array_preferred) {
	

	  String[] custDetails = new String[3];
		
		for (int i=0;i< array_regular.length; i++) {
			
			if (array_regular[i] != null && array_regular[i].getGuestID() == customerID) {
			
			if (array_regular[i].getGuestID()==customerID ) {
				
			custDetails[1]= String.valueOf(array_regular[i].getAmountSpent());
				
			custDetails[0]=  "Regular";
			
			return custDetails;
			
			}
		}
		}
		
		if (array_preferred == null)
			return null;
		
		
		Object obj = null;
		for (int i=0;i< array_preferred.length; i++) {
			obj = array_preferred[i];
			
			if (obj instanceof Gold ){
				if (((Gold) obj).getGuestID()== customerID){
					
					custDetails[1]= String.valueOf(((Gold) array_preferred[i]).getAmountSpent());
					custDetails[0]=  "Gold";
					custDetails[2]=String.valueOf(((Gold) array_preferred[i]).getDiscountPct());
							
							
					return custDetails;
					
				}
					
				
			}
			
			if (obj instanceof Platinum ){
				if (((Platinum) obj).getGuestID()== customerID){
					
					custDetails[1]= String.valueOf(((Platinum) array_preferred[i]).getAmountSpent());
					custDetails[0]=  "Platinum";
					custDetails[2]=String.valueOf(((Platinum) array_preferred[i]).getBonusBucks());
					
					return custDetails;
				}
			}
			
		}
		
		
		return null;
	
	}


//This function calculates the graphic cost based on the size and graphic cost parameters provided, taking into account predefined values for different sizes.
	public static double getGraphicCost(String size, double graphicCost) {
	    double diameter = 0;
	    double height = 0;

	    if (size.equals("M")) {
	        diameter = 4.5;
	        height = 5.75;
	    } else if (size.equals("L")) {
	        diameter = 5.5;
	        height = 7;
	    } else if (size.equals("S")) {
	        diameter = 4;
	        height = 4.5;
	    }

	    double radius = diameter / 2;
	    double pi = Math.PI;

	    double surfaceArea = 2 * pi * radius * height;
	    return surfaceArea * graphicCost;
	}
//This function calculates the total price for a drink based on its type and size. 
//It uses predefined price per ounce values for different drink types and sizes.
//If the provided drink type or size is not recognized, it returns -1 to indicate an unknown type.
		public  static double calculateDrinkPrice(String drinkType, String size) {
			
			
			
		    double pricePerOunce;
		    int sizeinOunces=0;
		    
		    switch (drinkType) {
	        case "soda":
	            pricePerOunce = 0.20;
	            break;
	        case "tea":
	            pricePerOunce = 0.12;
	            break;
	        case "punch":
	            pricePerOunce = 0.15;
	            break;
	        default:
	            return -1; // Unknown drink type
	    }
		    
		    switch (size) {
		        case "M":
		        	sizeinOunces = 20;
		            break;
		        case "L":
		        	sizeinOunces = 32;
		            break;
		        case "S":
		        	sizeinOunces  = 12;
		            break;
		        default:
		            return -1; // Unknown drink type
		    }

		   

		   return(pricePerOunce * sizeinOunces);
		    
		}

			
//This function calculates the total cost after applying a discount. It takes the initial total cost and a discount percentage as input parameters. 
//The discount is applied by subtracting the discount amount (calculated as a percentage of the total cost) from the initial total cost, resulting in the final cost after the discount is applied.
		public static double calculateTotaltAfterDiscount(double totalCost, int percentage ) {
		
			double temp = ((double) percentage / 100) * totalCost;
			totalCost=totalCost-temp;
			return totalCost;
		}
		
		
		
	//This function calculates the total cost after applying a discount. It takes the initial total cost and a discount percentage as input parameters. 
		//The discount percentage is provided as a string with a "%" symbol.
public static double calculateTotaltAfterDiscount(double totalCost, String percentage ) {
	
	double pct = Double.parseDouble(percentage.replaceAll("%", ""));
	
	double temp=(pct/100) * totalCost;
	totalCost=totalCost-temp;
	return totalCost;
}
	


public static void convertRegularToGold(int customerID , double totalCost){
    for (int i = 0; i < array_regular.length; i++) {
        if (array_regular[i] != null && array_regular[i].getGuestID() == customerID) {   //ask
            // Create a new Gold customer with the information from the regular customer
            Gold goldCustomer = new Gold();
            goldCustomer.setGuestID(array_regular[i].getGuestID());
            goldCustomer.setFirstName(array_regular[i].getFirstName());
            goldCustomer.setLastName(array_regular[i].getLastName());
            goldCustomer.setAmountSpent(totalCost);
            
            //Customer regularCustomer = array_regular[i];
            double totalSpent = goldCustomer.getAmountSpent();
           int discountPercentage = getGolcCustomerDiscountPercentage(totalCost);
        
            //goldCustomer.setDiscountPct("5%"); // Set an initial discount percentage
            
            goldCustomer.setDiscountPct(discountPercentage + "%");
            
            if( array_preferred!= null)  {
            
            // Add the new Gold customer to the array of preferred customers
            array_preferred = Arrays.copyOf(array_preferred, array_preferred.length + 1);
            array_preferred[array_preferred.length - 1] = goldCustomer;
            
            }
            else {
            array_preferred = new Customer[1];
            array_preferred[0]=goldCustomer;
            }
            	

            // Remove the regular customer from the array of regular customers
            array_regular[i] = null;
            
            
            
            removeNullValues(array_regular);
            
            break;
        }
    }
}





public static Customer[] removeNullValues(Customer[] inputArray) {
    int nonNullCount = 0;

    // Count the non-null elements in the input array
    for (Customer customer : inputArray) {
        if (customer != null) {
            nonNullCount++;
        }
    }

    // Create a new array to hold the non-null elements
    Customer[] newArray = new Customer[nonNullCount];
    int newIndex = 0;

    // Copy non-null elements from the input array to the new array
    for (Customer customer : inputArray) {
        if (customer != null) {
            newArray[newIndex] = customer;
            newIndex++;
        }
    }

    return newArray;
}



public static void updateGoldToPlatinum(int customerID) {
    for (int i = 0; i < array_preferred.length; i++) {
        Object obj = array_preferred[i];

        if (obj instanceof Gold && ((Gold) obj).getGuestID() == customerID) {
            // Create a new Platinum customer with the information from the Gold customer
            Platinum platinumCustomer = new Platinum();
            platinumCustomer.setGuestID(((Gold) obj).getGuestID());
            platinumCustomer.setFirstName(((Gold) obj).getFirstName());
            platinumCustomer.setLastName(((Gold) obj).getLastName());
            platinumCustomer.setAmountSpent(((Gold) obj).getAmountSpent());
            
            
            
            platinumCustomer.setBonusBucks(getBonusBucks(platinumCustomer.getAmountSpent())); 

            // Add the new Platinum customer to the array of preferred customers
            array_preferred[i] = platinumCustomer;

            // Optionally, you can break out of the loop if you want to update only one customer
            break;
        }
    }
}

public static void updateCustomerAmount(int customerID, double newAmount) {
    for (int i = 0; i < array_regular.length; i++) {
        if (array_regular[i] != null && array_regular[i].getGuestID() == customerID) {
            // Update the amount for the matching customer
        	array_regular[i].setAmountSpent(newAmount);
            return; // Exit the loop once the customer is updated
        }
    }
   
}

public static void updateGoldPlatCustomerAmount(int customerID, double newAmount) {
	
	String discountPct = getGolcCustomerDiscountPercentage(newAmount) + "%";
	
    for (int i = 0; i < array_preferred.length; i++) {
        if (array_preferred[i] != null ) {
            // Check the customer type and update the amount accordingly
            if (array_preferred[i] instanceof Gold    &&  (((Gold) array_preferred[i]).getGuestID() == customerID))      {
                ((Gold) array_preferred[i]).setAmountSpent(newAmount);
                ((Gold) array_preferred[i]).setDiscountPct(discountPct);
                return;
            } else if (array_preferred[i] instanceof Platinum && (((Platinum) array_preferred[i]).getGuestID() == customerID)) {
                ((Platinum) array_preferred[i]).setAmountSpent(newAmount);
                return;
            }
            
        }
    }
  

	
}	



public static void writeCustomerDataToFile( Customer[] customers) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter( "customer.dat"))) {
        for (Customer customer : customers) {
            if (customer != null) {
            	double amount = customer.getAmountSpent();
            	DecimalFormat decimalFormat = new DecimalFormat("0.00");
            	String formattedAmount = decimalFormat.format(amount);

            	
            	
                String data = customer.getGuestID() + " " + customer.getFirstName() + " " + customer.getLastName() + " " + formattedAmount;
                writer.write(data);
                writer.newLine();
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}





public static void writePreferredCustomerDataToFile( Object[] preferredCustomers) {
	
	
	
	if (preferredCustomers==null) 
		return;
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("preferred.dat"))) {
    	
    	 DecimalFormat decimalFormat = new DecimalFormat("0.00");
    	 
        for (Object obj : preferredCustomers) {
            if (obj instanceof Gold) {
                Gold goldCustomer = (Gold) obj;
                String data = goldCustomer.getGuestID() + " " + goldCustomer.getFirstName() + " " + goldCustomer.getLastName() + " "
                        + decimalFormat.format(goldCustomer.getAmountSpent() )+ " " + goldCustomer.getDiscountPct();  
        
                writer.write(data);
            } else if (obj instanceof Platinum) {
                Platinum platinumCustomer = (Platinum) obj;
                String data = platinumCustomer.getGuestID() + " " + platinumCustomer.getFirstName() + " " + platinumCustomer.getLastName() + " "
                        + decimalFormat.format(platinumCustomer.getAmountSpent() ) + " " + platinumCustomer.getBonusBucks();
                writer.write(data);
            }
            writer.newLine();
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}


public static boolean validateInput (String[] line) {
	
	
	
		
		if (line.length != 5 )
				 return false;
		// Check if the second element represents a valid drink size
		if (!("M".equals(line[1]) || "L".equals(line[1]) || "S".equals(line[1])) )
		  return false;
		
		// Check if the third element represents a valid drink type
		if (!("punch".equals(line[2]) || "tea".equals(line[2]) || "soda".equals(line[2])))
			  return false;
			
		
		
		try {
            Double.parseDouble(line[0]);
        } catch (NumberFormatException e) {
            return false;
        }
		
		
		try {
            Double.parseDouble(line[3]);
        } catch (NumberFormatException e) {
            return false;
        }
		
		
		try {
            Double.parseDouble(line[4]);
        } catch (NumberFormatException e) {
            return false;
            
        }
		
		

	
	
	return true;
	
	
}


public static int countLines(String filename) throws Exception{
    int lineCount = 0;
    
     BufferedReader reader = new BufferedReader(new FileReader(filename));
        while (reader.readLine() != null) {
            lineCount++;
        }
  
    
    return lineCount;
}


public static int getGolcCustomerDiscountPercentage(double amountSpent) {
	
	   
	   // 15% discount for spending $150 or more
       if (amountSpent >= 150) {
	        return 15; // 15% discount for spending $150 or more
	    } else if (amountSpent >= 100) {
	        return 10; // 10% discount for spending $100 or more
	    } else if (amountSpent >= 50) {
	        return 5; // 5% discount for spending $50 or more
	    } else {
	        return 0; // No discount if spending is below $50
	    }

}

public static int getBonusBucks(double amountSpent) {
	 if (amountSpent <= 200) {
	        return 0; // No bonus bucks for amounts less than or equal to $200
	    }
	
	 int bonusBucks = (int) ((amountSpent - 200) / 5);

	    return bonusBucks;
	
}


public static int getBonusBucksFofCurrentSpent(double totalcost) {
	
	
	 int bonusBucks = (int) ((totalcost) / 5);

	    return bonusBucks;
	
}



public static void updatePlatinumCustomerBonusBucks(int customerID, int bonusBucks) {
    for (int i = 0; i < array_preferred.length; i++) {
        Object obj = array_preferred[i];
     // Check if the current element in the array is an instance of the Platinum class
        if (obj instanceof Platinum && ((Platinum) obj).getGuestID() == customerID) {
        	 // If the customer ID matches, update their bonus bucks
            ((Platinum) obj).setBonusBucks(bonusBucks);
            return;
        }
    }

}


public static int getExistingBonusBucks(int customerID) {
	
	for (int i = 0; i < array_preferred.length; i++) {
        Object obj = array_preferred[i];

        if (obj instanceof Platinum && ((Platinum) obj).getGuestID() == customerID) {
        	// If the customer ID matches, return their existing bonus bucks value
           return( ((Platinum) obj).getBonusBucks());
           
        }
    }
	
	
	return 0;
	
	
}

}


