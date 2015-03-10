package package1;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DialogCheckInTent extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JLabel nameLabel;
	private JTextField nameText;
	private JLabel occupiedOnLabel;
	private JTextField occupiedOnTxt;
	private JLabel siteNumberLabel;
	private JTextField siteNumberTxt;
	private JLabel numOfDaysReservingLabel;
	private JTextField numOfDaysReservingTxt;
	private JLabel numOfTentersLabel;
	private JTextField numOfTentersTxt;
	private JButton okButton;
	private JButton cancelButton;
	private Tent unit;
	private JPanel panel;
	private int closeStatus;

	public DialogCheckInTent(JFrame pOccupy, Tent pUnit) {
		super(pOccupy, "Tent Check in", true);
		panel = new JPanel();
		unit = pUnit;
		panel.setLayout(new GridLayout(6, 2));
		nameLabel = new JLabel("Name: ");
		nameText = new JTextField();
		occupiedOnLabel = new JLabel("Occupied on (DD/MM/YYYY): ");
		occupiedOnTxt = new JTextField();
		siteNumberLabel = new JLabel("Requested site number: ");
		siteNumberTxt = new JTextField();
		numOfDaysReservingLabel = new JLabel("Days reserved: ");
		numOfDaysReservingTxt = new JTextField();
		numOfTentersLabel = new JLabel("Number of occupants: ");
		numOfTentersTxt = new JTextField();
		okButton = new JButton("OK");
		okButton.addActionListener(this);
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		panel.add(nameLabel);
		panel.add(nameText);
		panel.add(occupiedOnLabel);
		panel.add(occupiedOnTxt);
		panel.add(siteNumberLabel);
		panel.add(siteNumberTxt);
		panel.add(numOfDaysReservingLabel);
		panel.add(numOfDaysReservingTxt);
		panel.add(numOfTentersLabel);
		panel.add(numOfTentersTxt);
		panel.add(okButton);
		panel.add(cancelButton);
		getContentPane().add(panel);
		pack();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == okButton) {
			closeStatus = 1;
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			Date date;
			try {
				date = dateFormat.parse(occupiedOnTxt.getText());
				GregorianCalendar occupiedDate = new GregorianCalendar();
				occupiedDate.setTime(date);
				String name = nameText.getText();
				int siteNum = Integer.parseInt(siteNumberTxt.getText());
				int reservDays = Integer.parseInt(numOfDaysReservingTxt.getText());
				int numTenters = Integer.parseInt(numOfTentersTxt.getText());
				unit.setCheckIn(occupiedDate);
				unit.setNameReserving(name);
				unit.setSiteNumber(siteNum);
				unit.setDaysStaying(reservDays);
				unit.setNumOfTenters(numTenters);
				dispose();
			}
			catch(ParseException ex) {
				ex.printStackTrace();
			}
		}
		if (e.getSource() == cancelButton) {
			closeStatus = 0;
			dispose();
		}
	}
	
	public int getCloseStatus() {
		return closeStatus;
	}
}
