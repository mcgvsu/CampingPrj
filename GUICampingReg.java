/**********************************************************************
 * GUI class for for camping registration program. Creates and
 * instantiates all components of the interface.
 * @author Conner Toney & Michael Kolarik
 * @version March 2015
 *********************************************************************/

package package1;

import java.awt.event.*;
import java.text.*;
import java.util.*;

import javax.swing.*;

public class GUICampingReg extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	/** Menu bar for top of window */
	private JMenuBar menubar;

	/** File menu for saving, loading, sorting, and closing */
	private JMenu fileMenu;

	/** Check in menu for registering new tent/rv sites */
	private JMenu checkInMenu;

	/** Check out menu for releasing sites */
	private JMenu checkOutMenu;

	/** Status menu for obtaining status of all sites */
	private JMenu statusMenu;

	/** Menu item to open serialized database */
	private JMenuItem openSerialItem;

	/** Menu item for saving serialized database */
	private JMenuItem saveSerialItem;

	/** Menu item for opening text database */
	private JMenuItem openTextItem;

	/** Menu item for saving text database */
	private JMenuItem saveTextItem;

	/** Menu item to exit the program */
	private JMenuItem exitItem;

	/** Menu item for sorting sites */
	private JMenuItem sortItem;

	/** Menu item to check-in new tent site */
	private JMenuItem checkInTentItem;

	/** Menu item to check-in new RV site */
	private JMenuItem checkInRVItem;

	/** Menu item to check out a site */
	private JMenuItem checkOutItem;

	/** Menu item to obtain camp site status */
	private JMenuItem statusItem;

	/** A table for managing/organizing sites */
	private JTable table;

	/** Model which will control all table functions */
	private SiteModel tableModel;

	/** Scroll pane to navigate list */
	private JScrollPane scrollPane;

	/******************************************************************
	 * Method for instantiating all GUI elements and setting various
	 * window properties
	 *****************************************************************/
	public void createGUI() {
		//Causes fram to close when 'x' is clicked
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Setting title of window
		setTitle("Reservations");

		//Instantiating all menu items and respective listeners
		menubar = new JMenuBar();
		fileMenu = new JMenu("File");
		checkInMenu = new JMenu("Check in");
		checkOutMenu = new JMenu("Check out");
		statusMenu = new JMenu("Status");
		openSerialItem = new JMenuItem("Open serial...");
		openSerialItem.addActionListener(this);
		saveSerialItem = new JMenuItem("Save serial...");
		saveSerialItem.addActionListener(this);
		openTextItem = new JMenuItem("Open text...");
		openTextItem.addActionListener(this);
		saveTextItem = new JMenuItem("Save text...");
		saveTextItem.addActionListener(this);
		exitItem =  new JMenuItem("Exit");
		exitItem.addActionListener(this);
		checkInTentItem = new JMenuItem("Check in Tent...");
		checkInTentItem.addActionListener(this);
		checkInRVItem = new JMenuItem("Check in RV...");
		checkInRVItem.addActionListener(this);
		checkOutItem = new JMenuItem("Check out...");
		checkOutItem.addActionListener(this);
		statusItem = new JMenuItem("Check status...");
		statusItem.addActionListener(this);
		sortItem = new JMenuItem("Sort by site...");
		sortItem.addActionListener(this);

		//Adding all elements to menus
		menubar.add(fileMenu);
		fileMenu.add(openSerialItem);
		fileMenu.add(saveSerialItem);
		fileMenu.addSeparator();
		fileMenu.add(openTextItem);
		fileMenu.add(saveTextItem);
		fileMenu.addSeparator();
		fileMenu.add(sortItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);
		checkInMenu.add(checkInTentItem);
		checkInMenu.add(checkInRVItem);
		checkOutMenu.add(checkOutItem);
		menubar.add(checkInMenu);
		menubar.add(checkOutMenu);
		statusMenu.add(statusItem);
		menubar.add(statusMenu);
		tableModel = new SiteModel();
		table = new JTable(tableModel);
		scrollPane = new JScrollPane(table);
		add(scrollPane);
		setJMenuBar(menubar);

		//Setting default size and visibility
		setSize(500, 200);
		setVisible(true);
	}

	/******************************************************************
	 * Method used to detect which item was clicked and what methods
	 * to perform in each case
	 * @param e the event for each clicked item
	 *****************************************************************/
	@Override
	public void actionPerformed(ActionEvent e) {
		//Opens serialized db
		if (e.getSource() == openSerialItem) {
			tableModel.saveDB("res_ser");
		}
		//Saves db to serialized file
		if (e.getSource() == saveSerialItem) {
			tableModel.loadDB("res_ser");
		}
		//Opens text db
		if (e.getSource() == openTextItem) {
			tableModel.loadText("res_text.txt");
		}
		//Saves db to text file
		if (e.getSource() == saveTextItem) {
			tableModel.saveText("res_text.txt");
		}
		//Exits window
		if (e.getSource() == exitItem) 
			System.exit(0);
		//Checks in a new tent site
		if (e.getSource() == checkInTentItem) {
			//creates new RV object
			Tent unit = new Tent();
			//creates new Dialog for RV, sends RV object to Dialog
			DialogCheckInTent tentDialog = 
					new DialogCheckInTent(this, unit);
			tentDialog.setVisible(true);
			if (tentDialog.getCloseStatus() == 1) {
				//If site is available and desired dates don't conflict
				//only then will a new site be allowed
				if (tableModel.checkSite(unit) == false) {
					if (tableModel.checkDates(unit) == true) {
						tableModel.addSite(unit);
						//Present window with estimated cost
						JOptionPane.showMessageDialog
						(null,"Estimated cost:"+"$"+ unit.getCost(),
								"Estimated Cost",
								JOptionPane.INFORMATION_MESSAGE);
					}
					//If site/date conflict, block site, inform user
					else
						JOptionPane.showMessageDialog(null,
								"Sorry, this site is occupied.");						
				} else {
					tableModel.addSite(unit);
					JOptionPane.showMessageDialog
					(null,"Estimated cost:"
							+ "$" + unit.getCost(),"Estimated Cost",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
		//Checks in new RV site
		if (e.getSource() == checkInRVItem) {
			//creates new RV object
			RV unit = new RV();
			//creates new Dialog for RV, sends RV object to Dialog
			DialogCheckInRV RVDialog = new DialogCheckInRV(this, unit);
			RVDialog.setVisible(true);
			if (RVDialog.getCloseStatus() == 1) {
				//If site is available and desired dates don't conflict
				//only then will a new site be allowed
				if (tableModel.checkSite(unit) == false) {
					if (tableModel.checkDates(unit) == true) {
						tableModel.addSite(unit);
						//Present window with estimated cost
						JOptionPane.showMessageDialog
						(null,"Estimated cost:"+"$"+ unit.getCost(),
								"Estimated Cost",
								JOptionPane.INFORMATION_MESSAGE);
					}
					//If site/date conflict, block site, inform user
					else
						JOptionPane.showMessageDialog(null,
								"Sorry, this site is occupied.");						
				}
				else {
					tableModel.addSite(unit);
					JOptionPane.showMessageDialog(null,"Estimated cost:"
							+ "$" + unit.getCost(), "Estimated Cost",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
		//Checking out camp sites
		if (e.getSource() == checkOutItem) {
			//gets selected Site and creates an equal temp site
			int index = table.getSelectedRow();
			Site tempSite = (Site) tableModel.getObject(index);

			//Fields for JOptionPane
			JTextField dateTxtField = new JTextField(10);
			JPanel inputDatePanel = new JPanel();
			inputDatePanel.add(dateTxtField);
			GregorianCalendar outDate = new GregorianCalendar();
			//Pop up window to input checkout ate
			int result = JOptionPane.showConfirmDialog
					(null, inputDatePanel, 
					"Enter date: ", JOptionPane.OK_CANCEL_OPTION);

			//only removes Site if input is correct + OK is selected
			if (result == JOptionPane.OK_OPTION) {
				SimpleDateFormat dateFormat = 
						new SimpleDateFormat("MM/dd/yyyy");
				Date date;
				try {

					//parses date, throws exception if parse fails
					date = dateFormat.parse(dateTxtField.getText());
					outDate.setTime(date);

					//time difference of date checked out vs checked in
					long diff = outDate.getTimeInMillis() - 
							tempSite.getCheckIn().getTimeInMillis();
					int days = (int) (diff / (24 * 60 * 60 * 1000));
					if (days < 0)
						tempSite.setDaysStaying(0);
					else
						tempSite.setDaysStaying(days);

					//outputs actual payment due, then removes Site
					JOptionPane.showMessageDialog(null,
							("Payment due: $" + tempSite.getCost()),
							"Payment due!",
							JOptionPane.INFORMATION_MESSAGE);
					tableModel.removeSite(index);
				}
				catch (ParseException ex) {
					JOptionPane.showMessageDialog
					(null, "Incorrect date input!");
				}
			}
		}
		//Shows status of all sites based on an inputted date
		if (e.getSource() == statusItem) {
			DialogGetDateStatus DateDialog =new DialogGetDateStatus
					(this, tableModel.getSiteList());
			DateDialog.setVisible(true);
		}
		//Uses table model to call sorting (polymorphic)
		if (e.getSource() == sortItem) {
			tableModel.sortList();
		}
	}

	/******************************************************************
	 * Main method used to create new GUI instance
	 * @param args
	 *****************************************************************/	
	public static void main(String[] args) {
		GUICampingReg g = new GUICampingReg();
		g.createGUI();
	}
}
