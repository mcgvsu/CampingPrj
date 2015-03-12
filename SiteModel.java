package package1;

import java.text.DateFormat;
import java.util.ArrayList;
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
}
