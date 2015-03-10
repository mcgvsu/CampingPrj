package package1;

import java.io.Serializable;
import java.util.GregorianCalendar;

public class Site implements Serializable {
	private static final long serialVersionUID = 1L;

	/** the name of the person occupying the Site */
	protected String nameReserving;

	/** the date the Site was checked in */
	protected GregorianCalendar checkIn;

	/** the estimated number of days the person is reserving */
	protected int daysStaying;

	/** the date the Site was checked out */
	protected GregorianCalendar checkOutOn;

	/** the Site number */
	protected int siteNumber;

	//TODO finish this 
	public Site() {
	}

	public Site(String nameReserving, GregorianCalendar checkIn,
			int daysStaying, GregorianCalendar checkOutOn,
			int siteNumber) {
		this.nameReserving = nameReserving;
		this.checkIn = checkIn;
		this.daysStaying = daysStaying;
		this.checkOutOn = checkOutOn;
		this.siteNumber = siteNumber;
	}

	public String getNameReserving() {
		return nameReserving;
	}

	public void setNameReserving(String nameReserving) {
		this.nameReserving = nameReserving;
	}

	public GregorianCalendar getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(GregorianCalendar checkIn) {
		this.checkIn = checkIn;
	}

	public int getDaysStaying() {
		return daysStaying;
	}

	public void setDaysStaying(int daysStaying) {
		this.daysStaying = daysStaying;
	}

	public GregorianCalendar getCheckOutOn() {
		return checkOutOn;
	}

	public void setCheckOutOn(GregorianCalendar checkOutOn) {
		this.checkOutOn = checkOutOn;
	}

	public int getSiteNumber() {
		return siteNumber;
	}

	public void setSiteNumber(int siteNumber) {
		this.siteNumber = siteNumber;
	}
}
