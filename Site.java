/**********************************************************************
 * Site class containing all methods relevant to the multiple types
 * of camping sites available. Parent class to RV & Tent
 * @author Conner Toney and Michael Kolarik
 * @version March 2015
 *********************************************************************/

package package1;

import java.io.Serializable;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;

public abstract class Site implements Serializable, Comparable<Site> {
	private static final long serialVersionUID = 1L;

	/** Name of the person occupying the Site */
	protected String nameReserving;

	/** Date the Site was checked in */
	protected GregorianCalendar checkIn;

	/** Estimated number of days the person is reserving */
	protected int daysStaying;

	/** Date the Site was checked out */
	protected GregorianCalendar checkOutOn;

	/** Site number */
	protected int siteNumber;

	/******************************************************************
	 * Default site constructor; will never be called
	 *****************************************************************/
	public Site() {
		
	}

	/******************************************************************
	 * Site constructor which sets all relevant properties for a site
	 * Will be called from child classes at creation
	 * @param nameReserving name tied to site at check-in
	 * @param checkIn calendar object for check-in date
	 * @param daysStaying desired amount of time to stay checked in
	 * @param checkOutOn estimated day for checkout
	 * @param siteNumber the site which will be occupied by this site
	 *****************************************************************/
	public Site(String nameReserving, GregorianCalendar checkIn,
			int daysStaying, GregorianCalendar checkOutOn,
			int siteNumber) {
		this.nameReserving = nameReserving;
		this.checkIn = checkIn;
		this.daysStaying = daysStaying;
		this.checkOutOn = checkOutOn;
		this.siteNumber = siteNumber;
	}

	/******************************************************************
	 * Gets the name tied to this site object
	 * @return nameReserving the name tied to this site
	 *****************************************************************/
	public String getNameReserving() {
		return nameReserving;
	}

	/******************************************************************
	 * Sets the name for this site object
	 * @param nameReserving the name registered to this site check-in
	 *****************************************************************/
	public void setNameReserving(String nameReserving) {
		this.nameReserving = nameReserving;
	}

	/******************************************************************
	 * Gets this check in date
	 * @return checkIn the check in date for this site
	 *****************************************************************/
	public GregorianCalendar getCheckIn() {
		return checkIn;
	}

	/******************************************************************
	 * Sets the check-in date for this site
	 * @param checkIn the desired check in date
	 *****************************************************************/
	public void setCheckIn(GregorianCalendar checkIn) {
		this.checkIn = checkIn;
	}

	/******************************************************************
	 * Gets the days staying property for this site
	 * @return daysStaying the number of days estimated to stay
	 *****************************************************************/
	public int getDaysStaying() {
		return daysStaying;
	}

	/******************************************************************
	 * Sets the desired days for this site to be checked in
	 * @param daysStaying the number of days to stay
	 *****************************************************************/
	public void setDaysStaying(int daysStaying) {
		this.daysStaying = daysStaying;
	}

	/******************************************************************
	 * Gets this site's check out date
	 * @return checkOutOn the check out date for this site
	 *****************************************************************/
	public GregorianCalendar getCheckOutOn() {
		return checkOutOn;
	}

	/******************************************************************
	 * Sets the check-out date for this site
	 * @param checkOutOn the desired check-out date
	 *****************************************************************/
	public void setCheckOutOn(GregorianCalendar checkOutOn) {
		this.checkOutOn = checkOutOn;
	}

	/******************************************************************
	 * Gets this site's number
	 * @return siteNumber this site number
	 *****************************************************************/
	public int getSiteNumber() {
		return siteNumber;
	}

	/******************************************************************
	 * Sets the desired site number for this site
	 * @param siteNumber the number value for this site
	 *****************************************************************/
	public void setSiteNumber(int siteNumber) {
		this.siteNumber = siteNumber;
	}
	
	/******************************************************************
	 * Abstract method for calculating cost
	 *****************************************************************/
	public abstract double getCost();
	
	/******************************************************************
	 * Used to sort the list of sites in ascending order based on
	 * site numbers
	 * @return the number value tied to the order for this site
	 *****************************************************************/
	public int compareTo(Site n) {
		int compareSite = n.getSiteNumber();		
		return this.getSiteNumber() - compareSite;
	}
}
