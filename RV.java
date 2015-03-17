/**********************************************************************
 * RV class used to construct an RV object by calling upon its parent
 * class. Also contains methods which are only applicable to RV sites
 * @author Conner Toney & Michael Kolarik
 * @version March 2015
 *********************************************************************/

package package1;

import java.util.GregorianCalendar;

public class RV extends Site {

	private static final long serialVersionUID = 1L;
	
	/** Power supplied to the Site */
	private int power; //30, 40, 50

	/******************************************************************
	 * Default RV constructor, immediately calls parent
	 *****************************************************************/
	public RV() {
		super();
	}

	/******************************************************************
	 * RV constructor which sets all relevant properties for an RV site
	 * Calls parent class with all properties but power, which is 
	 * exclusive to an RV site
	 * @param nameReserving name tied to site at check-in
	 * @param checkIn calendar object for check-in date
	 * @param daysStaying desired amount of time to stay checked in
	 * @param checkOutOn estimated day for checkout
	 * @param siteNumber the site which will be occupied by this site
	 * @param power the desired amount of power required for this site
	 *****************************************************************/
	public RV(String nameReserving, GregorianCalendar checkIn,
			int daysStaying, GregorianCalendar checkOutOn,
			int siteNumber, int power) {
		super(nameReserving, checkIn, daysStaying, checkOutOn,
				siteNumber);
		this.power = power;
	}

	/******************************************************************
	 * Gets the power supplied to this RV site
	 * @return power the power for this site
	 *****************************************************************/
	public int getPower() {
		return power;
	}

	/******************************************************************
	 * Sets the required power to be supplied to this RV site
	 * @param power the power desired for this site
	 *****************************************************************/
	public void setPower(int power) {
		this.power = power;
	}

	/******************************************************************
	 * Method which overrides parent method. Calculates the cost of the
	 * site based upon days staying and base RV site cost
	 * @return the cost of staying
	 *****************************************************************/
	@Override
	public double getCost() {
		return (30 * daysStaying);
	}
}
