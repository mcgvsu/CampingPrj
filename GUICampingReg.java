package package1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GUICampingReg extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	private JMenuBar menubar;
	
	//menus
	private JMenu fileMenu;
	private JMenu checkInMenu;
	private JMenu checkOutMenu;
	
	//file menu
	private JMenuItem openSerialItem;
	private JMenuItem saveSerialItem;
	private JMenuItem openTextItem;
	private JMenuItem saveTextItem;
	private JMenuItem exitItem;
	
	//check in menu
	private JMenuItem checkInTentItem;
	private JMenuItem checkInRVItem;
	
	//check out menu
	private JMenuItem checkOutItem;
	
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
		menubar.add(fileMenu);
		fileMenu.add(openSerialItem);
		fileMenu.add(saveSerialItem);
		fileMenu.addSeparator();
		fileMenu.add(openTextItem);
		fileMenu.add(saveTextItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);
		checkInMenu.add(checkInTentItem);
		checkInMenu.add(checkInRVItem);
		checkOutMenu.add(checkOutItem);
		menubar.add(checkInMenu);
		menubar.add(checkOutMenu);
		tableModel = new SiteModel();
		table = new JTable(tableModel);
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
			//creates new Tent object
			Tent unit = new Tent();
			//creates new Dialog for Tent, sends Tent object to Dialog
			DialogCheckInTent tentDialog = 
					new DialogCheckInTent(this, unit);
			tentDialog.setVisible(true);
			//if ok has been clicked, add the Tent object to the table
			if (tentDialog.getCloseStatus() == 1) {
				if (tableModel.checkSite(unit.getSiteNumber())) {
					tableModel.addSite(unit);
					JOptionPane.showMessageDialog(null,"Estimated cost:"
							+ "$" + unit.getCost(), "Estimated Cost",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "Sorry, "
							+ "this site is occupied.");
				}
			}
		}
		if (e.getSource() == checkInRVItem) {
			//creates new RV object
			RV unit = new RV();
			//creates new Dialog for RV, sends RV object to Dialog
			DialogCheckInRV RVDialog = new DialogCheckInRV(this, unit);
			RVDialog.setVisible(true);
			//if ok has been clicked, add the RV object to the table
			if (RVDialog.getCloseStatus() == 1) {
				if (tableModel.checkSite(unit.getSiteNumber())) {
					tableModel.addSite(unit);
					JOptionPane.showMessageDialog(null,"Estimated cost:"
							+ "$" + unit.getCost(), "Estimated Cost",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "Sorry, "
							+ "this site is occupied.");
				}
			}
		}
		
		//TODO Calculate actual cost of stay when checkout
		//by using a dialog box to input actual checkout day
		if (e.getSource() == checkOutItem) {
			int index = table.getSelectedRow();
			Site tempSite = (Site) tableModel.getObject(index);
			JOptionPane.showMessageDialog(null,
					("Payment due: $" + tempSite.getCost()),
					"Payment due!",
					JOptionPane.INFORMATION_MESSAGE);
			tableModel.removeSite(index);
		}
	}

	public static void main(String[] args) {
		GUICampingReg g = new GUICampingReg();
		g.createGUI();
	}
}
