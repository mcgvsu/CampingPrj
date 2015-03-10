package package1;

import java.util.GregorianCalendar;

public class RV extends Site{

	private static final long serialVersionUID = 1L;
	/** the power supplied to the Site */
	private int power; //30, 40, 50

	public RV() {
		super();
	}

	public RV(String nameReserving, GregorianCalendar checkIn,
			int daysStaying, GregorianCalendar checkOutOn,
			int siteNumber, int power) {
		super(nameReserving, checkIn, daysStaying, checkOutOn, siteNumber);
		this.power = power;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}
}
