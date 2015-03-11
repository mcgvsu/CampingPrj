package package1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GUICampingReg extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	
	private JMenuBar menubar;
	
	//menus
	private JMenu fileMenu;
	private JMenu checkInMenu;
	private JMenu checkOutMenu;
	
	//file menu
	private JMenuItem openSerialMenu;
	private JMenuItem saveSerialMenu;
	private JMenuItem openTextMenu;
	private JMenuItem saveTextMenu;
	private JMenuItem exitMenu;
	
	//check in menu
	private JMenuItem checkInTentMenu;
	private JMenuItem checkInRVMenu;
	
	//check out menu
	private JMenuItem checkOutTentMenu;
	private JMenuItem checkOutRVMenu;
	
	//table stuff
	private JTable table;
	private SiteModel tableModel;
	private JScrollPane scrollPane;

	public void createGUI() {
		frame = new JFrame("Reservations");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menubar = new JMenuBar();
		fileMenu = new JMenu("File");
		checkInMenu = new JMenu("Check in");
		checkOutMenu = new JMenu("Check out");
		openSerialMenu = new JMenuItem("Open serial...");
		openSerialMenu.addActionListener(this);
		saveSerialMenu = new JMenuItem("Save serial...");
		saveSerialMenu.addActionListener(this);
		openTextMenu = new JMenuItem("Open text...");
		openTextMenu.addActionListener(this);
		saveTextMenu = new JMenuItem("Save text...");
		saveTextMenu.addActionListener(this);
		exitMenu =  new JMenuItem("Exit");
		exitMenu.addActionListener(this);
		checkInTentMenu = new JMenuItem("Check in Tent...");
		checkInTentMenu.addActionListener(this);
		checkInRVMenu = new JMenuItem("Check in RV...");
		checkInRVMenu.addActionListener(this);
		checkOutTentMenu = new JMenuItem("Check out Tent...");
		checkOutTentMenu.addActionListener(this);
		checkOutRVMenu = new JMenuItem("Check out RV...");
		checkOutRVMenu.addActionListener(this);
		menubar.add(fileMenu);
		fileMenu.add(openSerialMenu);
		fileMenu.add(saveSerialMenu);
		fileMenu.addSeparator();
		fileMenu.add(openTextMenu);
		fileMenu.add(saveTextMenu);
		fileMenu.addSeparator();
		fileMenu.add(exitMenu);
		checkInMenu.add(checkInTentMenu);
		checkInMenu.add(checkInRVMenu);
		checkOutMenu.add(checkOutTentMenu);
		checkOutMenu.add(checkOutRVMenu);
		menubar.add(checkInMenu);
		menubar.add(checkOutMenu);
		tableModel = new SiteModel();
		table = new JTable(tableModel);
		scrollPane = new JScrollPane(table);
		frame.add(scrollPane);
		frame.setJMenuBar(menubar);
		frame.setSize(500, 200);
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == openSerialMenu) {
			//TODO open serial file
		}
		if (e.getSource() == saveSerialMenu) {
			//TODO save serial file
		}
		if (e.getSource() == openTextMenu) {
			//TODO open text file
		}
		if (e.getSource() == saveTextMenu) {
			//TODO save text file
		}
		if (e.getSource() == exitMenu) 
			System.exit(0);
		if (e.getSource() == checkInTentMenu) {
			//creates new Tent object
			Tent unit = new Tent();
			//creates new Dialog for Tent, sends Tent object to Dialog
			DialogCheckInTent tentDialog = new DialogCheckInTent(this, unit);
			tentDialog.setVisible(true);
			//if "okButton" has been clicked, add the Tent object to the table
			if (tentDialog.getCloseStatus() == 1) {
				tableModel.addSite(unit);
			}
		}
		if (e.getSource() == checkInRVMenu) {
			//creates new RV object
			RV unit = new RV();
			//creates new Dialog for RV, sends RV object to Dialog
			DialogCheckInRV RVDialog = new DialogCheckInRV(this, unit);
			RVDialog.setVisible(true);
			//if "okButton" has been clicked, add the RV object to the table
			if (RVDialog.getCloseStatus() == 1) {
				tableModel.addSite(unit);
			}
		}
		
		//TODO make check out menu dependent on Site type
		if (e.getSource() == checkOutTentMenu) {
			//TODO remove Site + pop up with payment info
		}
		if (e.getSource() == checkOutRVMenu) {
			//TODO remove Site + pop up with payment info
		}
	}

	public static void main(String[] args) {
		GUICampingReg g = new GUICampingReg();
		g.createGUI();
	}
}
