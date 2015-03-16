package package1;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;

public class GUICampingReg extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JMenuBar menubar;

	//menus
	private JMenu fileMenu;
	private JMenu checkInMenu;
	private JMenu checkOutMenu;
	private JMenu statusMenu;

	//file menu
	private JMenuItem openSerialItem;
	private JMenuItem saveSerialItem;
	private JMenuItem openTextItem;
	private JMenuItem saveTextItem;
	private JMenuItem exitItem;
	private JMenuItem sortItem;


	//check in menu
	private JMenuItem checkInTentItem;
	private JMenuItem checkInRVItem;

	//check out menu
	private JMenuItem checkOutItem;

	//status menu
	private JMenuItem statusItem;

	//table stuff
	private JTable table;
	private SiteModel tableModel;
	private JScrollPane scrollPane;
		
	public void createGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Reservations");
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
		
		table = new JTable(tableModel) {
			private static final long serialVersionUID = 1L;

			public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
				Component c = super.prepareRenderer(renderer, row, col);

				for (int i = 0; i < table.getRowCount(); i++) {
					Site tempSite = (Site) tableModel.getObject(i);
					if (row == i) {
						if (tempSite instanceof RV) {
							c.setBackground(Color.LIGHT_GRAY);
						}
						else
							c.setBackground(Color.WHITE);
					}
				}

				return c;
			}
		};
		scrollPane = new JScrollPane(table);
		add(scrollPane);		
		setJMenuBar(menubar);
		setSize(500, 200);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == openSerialItem) {
			tableModel.saveDB("reservations");
		}
		if (e.getSource() == saveSerialItem) {
			tableModel.loadDB("reservations");
		}
		if (e.getSource() == openTextItem) {
			//TODO open text file
		}
		if (e.getSource() == saveTextItem) {
			//TODO save text file
		}
		if (e.getSource() == exitItem) 
			System.exit(0);

		if (e.getSource() == checkInTentItem) {
			if (tableModel.getRowCount() == 5)
				JOptionPane.showMessageDialog(null, "All sites are occupied");
			else {
				//creates new Tent object
				Tent unit = new Tent();
				//creates new Dialog for Tent, sends Tent object to Dialog
				DialogCheckInTent tentDialog = new DialogCheckInTent(this, unit);
				tentDialog.setVisible(true);
				if (tentDialog.getCloseStatus() == 1) {
					if (tableModel.checkSite(unit) == false) {
						if (tableModel.checkDates(unit) == true) {
							tableModel.addSite(unit);
							JOptionPane.showMessageDialog(null,"Estimated cost:"
									+ "$" + unit.getCost(), "Estimated Cost",
									JOptionPane.INFORMATION_MESSAGE);
						}
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

		}
		if (e.getSource() == checkInRVItem) {
			if (tableModel.getRowCount() == 5)
				JOptionPane.showMessageDialog(null, "All sites are occupied");
			else {
				//creates new RV object
				RV unit = new RV();
				//creates new Dialog for RV, sends RV object to Dialog
				DialogCheckInRV RVDialog = new DialogCheckInRV(this, unit);
				RVDialog.setVisible(true);
				if (RVDialog.getCloseStatus() == 1) {
					if (tableModel.checkSite(unit) == false) {
						if (tableModel.checkDates(unit) == true) {
							tableModel.addSite(unit);
							JOptionPane.showMessageDialog(null,"Estimated cost:"
									+ "$" + unit.getCost(), "Estimated Cost",
									JOptionPane.INFORMATION_MESSAGE);
						}
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
		}


		if (e.getSource() == checkOutItem) {

			//gets selected Site and creates a temp site equal to that Site
			int index = table.getSelectedRow();
			Site tempSite = (Site) tableModel.getObject(index);

			//stuff for JOptionPane
			JTextField dateTxtField = new JTextField(10);
			JPanel inputDatePanel = new JPanel();
			inputDatePanel.add(dateTxtField);
			GregorianCalendar outDate = new GregorianCalendar();

			int result = JOptionPane.showConfirmDialog(null, inputDatePanel, 
					"Enter date: ", JOptionPane.OK_CANCEL_OPTION);

			//only removes Site if input is correct + OK is selected
			if (result == JOptionPane.OK_OPTION) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
				Date date;
				try {

					//parses date, throws exception if parse fails
					date = dateFormat.parse(dateTxtField.getText());
					outDate.setTime(date);

					//gets time difference of date checked out vs checked in
					long diff = outDate.getTimeInMillis() - tempSite.getCheckIn().getTimeInMillis();
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
					JOptionPane.showMessageDialog(null, "Incorrect date input!");
				}
			}
		}

		if (e.getSource() == statusItem) {
			DialogGetDateStatus DateDialog = new DialogGetDateStatus(this, tableModel.getSiteList());
			DateDialog.setVisible(true);
		}
		
		if (e.getSource() == sortItem) {
			tableModel.sortList();
		}
	}

	public static void main(String[] args) {
		GUICampingReg g = new GUICampingReg();
		g.createGUI();
	}
}
