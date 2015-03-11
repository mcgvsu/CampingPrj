package package1;

import java.util.GregorianCalendar;

public class Tent extends Site{

	private static final long serialVersionUID = 1L;
	/** the number of occupants on the Site */
	private int numOfOccupants;

	public Tent() {
		super();
	}

	public Tent(String nameReserving, GregorianCalendar checkIn,
			int daysStaying, GregorianCalendar checkOutOn,
			int siteNumber, int numOfTenters) {
		super(nameReserving, checkIn, daysStaying, checkOutOn, siteNumber);
		this.numOfOccupants = numOfTenters;
	}

	public int getNumOfTenters() {
		return numOfOccupants;
	}

	public void setNumOfTenters(int numOfTenters) {
		this.numOfOccupants = numOfTenters;
	}
	
	//TODO add getCost method
}
