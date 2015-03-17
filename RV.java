/**********************************************************************
 * Tent class used to construct an RV object by calling upon its parent
 * class. Also contains methods which are only applicable to tent sites
 * @author Conner Toney & Michael Kolarik
 * @version March 2015
 *********************************************************************/

package package1;

import java.util.GregorianCalendar;

public class Tent extends Site {

	private static final long serialVersionUID = 1L;
	
	/** Number of occupants on the Site */
	private int numOfOccupants;

	/******************************************************************
	 * Default tent constructor, immediately calls parent
	 *****************************************************************/
	public Tent() {
		super();
	}

	/******************************************************************
	 * Tent constructor which sets all relevant properties for a tent
	 * Calls parent class with all properties but number of occupants, 
	 * which is exclusive to a tent site
	 * @param nameReserving name tied to site at check-in
	 * @param checkIn calendar object for check-in date
	 * @param daysStaying desired amount of time to stay checked in
	 * @param checkOutOn estimated day for checkout
	 * @param siteNumber the site which will be occupied by this site
	 * @param numofTenters the number of people staying on this site
	 *****************************************************************/
	public Tent(String nameReserving, GregorianCalendar checkIn,
			int daysStaying, GregorianCalendar checkOutOn,
			int siteNumber, int numOfTenters) {
		super(nameReserving, checkIn, daysStaying, checkOutOn,
				siteNumber);
		this.numOfOccupants = numOfTenters;
	}

	/******************************************************************
	 * Gets the number of tenters on this site
	 * @return numOfOccupants the number of people staying
	 *****************************************************************/
	public int getNumOfTenters() {
		return numOfOccupants;
	}

	/******************************************************************
	 * Sets the number of tenters for this site
	 * @param numOfTenters the number of occupants
	 *****************************************************************/
	public void setNumOfTenters(int numOfTenters) {
		this.numOfOccupants = numOfTenters;
	}

	/******************************************************************
	 * Method which overrides parent method. Calculates the cost of the
	 * site based upon days staying, number of occupants and base
	 * tent site cost
	 * @return cost of staying
	 *****************************************************************/
	@Override
	public double getCost() {
		return ((3 * daysStaying) * numOfOccupants);
	}
}
