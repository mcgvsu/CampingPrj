package package1;

import java.io.*;
import java.text.*;
import java.util.*;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

public class SiteModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private ArrayList<Site> siteList;
	private String[] columnNames = {"Name", "Checked-In", "Days Staying", "Site #", "Misc"};

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return siteList.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
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
				return ("Power: " + ((RV) siteList.get(row)).getPower());

			else
				return ("Occupants: " + ((Tent) siteList.get(row)).getNumOfTenters());
		default:
			return null;
		}
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	public SiteModel() {
		super();
		siteList = new ArrayList<Site>();
	}

	public void removeSite(int i) {
		siteList.remove(i);
		fireTableRowsDeleted(0, siteList.size());
	}

	public void addSite(Site inputSite) {
		siteList.add(inputSite);
		fireTableRowsInserted(0, siteList.size());
	}

	public Object getObject(int i) {
		return siteList.get(i);
	}
		
	public boolean checkSite(Site n) {
		int inputSiteNum = n.getSiteNumber();
		for (int i = 0; i < siteList.size(); i++) {
			if (siteList.get(i).getSiteNumber() == inputSiteNum)
				return false;
		}
		return true;
	}

	public boolean checkDates(Site n) {
		GregorianCalendar inN = n.getCheckIn();
		GregorianCalendar outN = n.getCheckOutOn();
		
		for (int i = 0; i < siteList.size(); i++) {
			if (n.getSiteNumber() == siteList.get(i).getSiteNumber()) {
				GregorianCalendar inI = siteList.get(i).getCheckIn();
				GregorianCalendar outI = siteList.get(i).getCheckOutOn();
				if (inN.before(inI) && outN.after(outI))
					return false;
				if (inN.before(inI) && (outN.after(inI) && outN.before(outI)))
					return false;
				if (inN.after(inI) && outN.before(outI))
					return false;
				if (inN.after(inI) && inN.before(outI))
					return false;
			}
		}
		return true;
	}
	
	public void saveDB(String filename) {
		try {
			FileOutputStream fo = new FileOutputStream(filename);
			ObjectOutputStream oo = new ObjectOutputStream(fo);
			oo.writeObject(siteList);
			oo.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void loadDB(String filename) {
		try {
			FileInputStream fi = new FileInputStream(filename);
			ObjectInputStream oi = new ObjectInputStream(fi);
			siteList = (ArrayList<Site>) oi.readObject();
			fireTableRowsInserted(0, siteList.size() -1);
			oi.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void saveText(String filename) {
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new FileWriter(filename)));
			out.println(siteList.size());
			for (int i = 0; i < siteList.size(); i++) {
				Site s = siteList.get(i);
				out.println(s.getClass().getName());
				out.println(s.getNameReserving());
				out.println(DateFormat.getDateInstance(DateFormat.SHORT)
						.format(s.getCheckIn().getTime()));
				out.println(s.getDaysStaying());
				out.println(s.getSiteNumber());
				if (s instanceof Tent)
					out.println(((Tent) s).getNumOfTenters());
				else
					out.println(((RV) s).getPower());
			}
			out.close();
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, "Loading error!");
		}
	}

	public void loadText(String filename) {
		siteList.clear();
		fireTableRowsDeleted(0, siteList.size());

		try {
			Scanner sc = new Scanner(new File(filename));
			int count = Integer.parseInt(sc.nextLine().trim());
			for (int i = 0; i < count; i++) {
				String type = sc.nextLine().trim();
				String name = sc.nextLine().trim();

				GregorianCalendar dateRes = new GregorianCalendar();
				try {
					DateFormat f = new SimpleDateFormat("MM/dd/yyyy");
					Date d = f.parse(sc.nextLine().trim());
					dateRes = new GregorianCalendar();
					dateRes.setTime(d);
				} catch (ParseException ex) {
					ex.printStackTrace();
				}
				int days = Integer.parseInt(sc.nextLine().trim());
				GregorianCalendar dateOut = new GregorianCalendar();
				int siteNum = Integer.parseInt(sc.nextLine().trim());

				if (type.contains("Tent")) {
					int numTenters = 
							Integer.parseInt(sc.nextLine().trim());
					Tent t = new Tent(name, dateRes, days, dateOut,
							siteNum, numTenters);
					siteList.add(t);
					fireTableRowsInserted(siteList.size() - 1,
							siteList.size() - 1);
				} else {
					int pow = Integer.parseInt(sc.nextLine().trim());
					RV rv = new RV (name, dateRes, days, dateOut,
							siteNum, pow);
					siteList.add(rv);
					fireTableRowsInserted(siteList.size() - 1,
							siteList.size() - 1);
				}
			}
			sc.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public ArrayList<Site> getSiteList() {
		return siteList;
	}
	
	public void sortList() {
		Collections.sort(siteList);
	}
}
