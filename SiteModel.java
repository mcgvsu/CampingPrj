/**********************************************************************
Model used to represent a table of Sites.

@author Conner Toney
@author Mike Kolarik
@version GVSU Winter 2015
 *********************************************************************/

package package1;

import java.io.*;
import java.text.*;
import java.util.*;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

public class SiteModel extends AbstractTableModel {

	/** default serial ID */
	private static final long serialVersionUID = 1L;

	/** list of Sites */
	private ArrayList<Site> siteList;

	/** names used for column header */
	private String[] columnNames = {"Name", "Checked-In", 
			"Days Staying", "Site #", "Misc"};

	/******************************************************************
	Default constructor that calls super() and creates siteList
	 *****************************************************************/
	public SiteModel() {
		super();
		siteList = new ArrayList<Site>();
	}

	/******************************************************************
	Gets the number of columns
	@return the number of columns
	 *****************************************************************/
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	/******************************************************************
	Gets the number of rows
	@return the number of rows
	 *****************************************************************/
	@Override
	public int getRowCount() {
		return siteList.size();
	}

	/******************************************************************
	Gets the value in table at the position of (row, col)
	@param row row where value is contained
	@param col column where value is contained
	@return the value at (row, col)
	 *****************************************************************/
	@Override
	public Object getValueAt(int row, int col) {
		
		//the siteList retrieves info dependent on column number
		switch (col) {
		case 0:
			return siteList.get(row).getNameReserving();
		case 1:
			return (DateFormat.getDateInstance(DateFormat.SHORT)
					.format(siteList.get(row).getCheckIn().getTime()));
		case 2:
			return siteList.get(row).getDaysStaying();
		case 3:
			return siteList.get(row).getSiteNumber();
		case 4:
			if (siteList.get(row) instanceof RV) 
				return ("Power: " 
						+ ((RV) siteList.get(row)).getPower());
			else
				return ("Occupants: " 
						+ ((Tent) siteList.get(row)).getNumOfTenters());
		default:
			return null;
		}
	}

	/******************************************************************
	Gets the name of the column
	@param col column where the name is
	@return the column name
	 *****************************************************************/
	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	/******************************************************************
	Removes the Site at the index in siteList and updates table display
	@param col column where the name is
	 *****************************************************************/
	public void removeSite(int index) {
		siteList.remove(index);
		fireTableRowsDeleted(0, siteList.size());
	}

	/******************************************************************
	Adds the given Site to siteList and updates table display
	@param inputSite the Site to be added to siteList
	 *****************************************************************/
	public void addSite(Site inputSite) {
		siteList.add(inputSite);
		fireTableRowsInserted(0, siteList.size());
	}

	/******************************************************************
	Gets the Object at the given index
	@param index the location of the Object
	@return the Site at the given index
	 *****************************************************************/
	public Object getObject(int index) {
		return siteList.get(index);
	}

	/******************************************************************
	Checks if the given Site's site# is already taken
	@param inputSite the Site being checked
	@return true if site is free, false if site is not free
	 *****************************************************************/
	public boolean checkSite(Site inputSite) {
		int inputSiteNum = inputSite.getSiteNumber();
		for (int i = 0; i < siteList.size(); i++) {
			if (siteList.get(i).getSiteNumber() == inputSiteNum)
				return false;
		}
		return true;
	}

	/******************************************************************
	Checks for date availability by checking for overlaps
	@param inputDate the date being checked
	@return true if date is available, false if not
	 *****************************************************************/
	public boolean checkDates(Site inputDate) {
		GregorianCalendar inputCal = inputDate.getCheckIn();
		GregorianCalendar outputCal = inputDate.getCheckOutOn();

		//checks for date availability
		for (int i = 0; i < siteList.size(); i++) {
			if (inputDate.getSiteNumber() == siteList.
					get(i).getSiteNumber()) {
				GregorianCalendar inputCal2;
				inputCal2 = siteList.get(i).getCheckIn();
				GregorianCalendar outputCal2;
				outputCal2 = siteList.get(i).getCheckOutOn();
				if (inputCal.before(inputCal2) 
						&& outputCal.after(outputCal2))
					return false;
				if (inputCal.before(inputCal2) 
						&& (outputCal.after(inputCal2) 
								&& outputCal.before(outputCal2)))
					return false;
				if (inputCal.after(inputCal2) 
						&& outputCal.before(outputCal2))
					return false;
				if (inputCal.after(inputCal2) 
						&& inputCal.before(outputCal2))
					return false;
			}
		}
		return true;
	}

	/******************************************************************
	Saves a serialized file of the table information
	@param filename name of file to be saved
	 *****************************************************************/
	public void saveDB(String filename) {
		try {
			FileOutputStream fileOutput;
			fileOutput = new FileOutputStream(filename);
			ObjectOutputStream objectOutput;
			objectOutput = new ObjectOutputStream(fileOutput);
			objectOutput.writeObject(siteList);
			objectOutput.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/******************************************************************
	Loads a serialized file of the table information
	@param filename name of file to be loaded
	 *****************************************************************/
	@SuppressWarnings("unchecked")
	public void loadDB(String filename) {
		try {
			FileInputStream fileInput = new FileInputStream(filename);
			ObjectInputStream objectInput;
			objectInput = new ObjectInputStream(fileInput);
			siteList = (ArrayList<Site>) objectInput.readObject();
			fireTableRowsInserted(0, siteList.size() -1);
			objectInput.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/******************************************************************
	Saves a text file of the table information
	@param filename name of file to be saved
	 *****************************************************************/
	public void saveText(String filename) {
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new FileWriter(filename)));
			out.println(siteList.size());
			
			//iterates through siteList and prints out the Site
			//info line by line
			for (int i = 0; i < siteList.size(); i++) {
				Site tempSite = siteList.get(i);
				out.println(tempSite.getClass().getName());
				out.println(tempSite.getNameReserving());
				out.println(DateFormat.getDateInstance(DateFormat.SHORT)
						.format(tempSite.getCheckIn().getTime()));
				out.println(tempSite.getDaysStaying());
				out.println(tempSite.getSiteNumber());
				if (tempSite instanceof Tent)
					out.println(((Tent) tempSite).getNumOfTenters());
				else
					out.println(((RV) tempSite).getPower());
			}
			out.close();
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, "Saving error!");
		}
	}

	/******************************************************************
	Loads a text file of the table information
	@return filename name of file to be loaded
	 *****************************************************************/
	public void loadText(String filename) {
		
		//beings by clearing what is essentially the table
		siteList.clear();
		fireTableRowsDeleted(0, siteList.size());

		try {
			Scanner scan = new Scanner(new File(filename));
			int count = Integer.parseInt(scan.nextLine().trim());
			
			//reads through the file line by line
			for (int i = 0; i < count; i++) {
				String type = scan.nextLine().trim();
				String name = scan.nextLine().trim();

				//parses for date
				GregorianCalendar dateRes = new GregorianCalendar();
				try {
					DateFormat format;
					format = new SimpleDateFormat("MM/dd/yyyy");
					Date tempDate;
					tempDate = format.parse(scan.nextLine().trim());
					dateRes = new GregorianCalendar();
					dateRes.setTime(tempDate);
				} catch (ParseException ex) {
					ex.printStackTrace();
				}
				
				//parses for days
				int days = Integer.parseInt(scan.nextLine().trim());
				GregorianCalendar dateOut = new GregorianCalendar();
				
				//parses for site number
				int siteNum = Integer.parseInt(scan.nextLine().trim());

				//if tent, parses for number of tenters and sets
				//the parsed information into a Tent object
				//ELSE sets parsed information into RV object
				if (type.contains("Tent")) {
					int numTenters = 
							Integer.parseInt(scan.nextLine().trim());
					Tent tempTent = new Tent(name, dateRes, days, 
							dateOut, siteNum, numTenters);
					siteList.add(tempTent);
					fireTableRowsInserted(siteList.size() - 1,
							siteList.size() - 1);
				} else {
					int pow = Integer.parseInt(scan.nextLine().trim());
					RV rv = new RV (name, dateRes, days, dateOut,
							siteNum, pow);
					siteList.add(rv);
					fireTableRowsInserted(siteList.size() - 1,
							siteList.size() - 1);
				}
			}
			scan.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/******************************************************************
	Gets the list of Sites
	@return siteList
	 *****************************************************************/
	public ArrayList<Site> getSiteList() {
		return siteList;
	}

	/******************************************************************
	Sorts the list of Sites
	 *****************************************************************/
	public void sortList() {
		Collections.sort(siteList);
	}
}
