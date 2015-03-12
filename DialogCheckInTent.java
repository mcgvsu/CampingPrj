package package1;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.*;

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
				//parses date, throws exception if parse fails
				date = dateFormat.parse(occupiedOnTxt.getText());
				GregorianCalendar occupiedDate = new GregorianCalendar();
				occupiedDate.setTime(date);

				//parses for name, throws exception if anything
				//but letters are in string
				String name = nameText.getText();
				if (!name.matches("[a-zA-Z]+"))
					throw new ParseException("Name error - not letters", 1);

				//parses for site#, throws exception if less than 1
				int siteNum = Integer.parseInt(siteNumberTxt.getText());
				if (siteNum < 1)
					throw new ParseException("Site error", 1);

				//parses for reservation day amount, throws exception if less than 1
				int reservDays = Integer.parseInt(numOfDaysReservingTxt.getText());
				if (reservDays < 1)
					throw new ParseException("Reservation error", 1);

				//parses for tenters, throws exception if not 30 40 or 50
				int numTenters = Integer.parseInt(numOfTentersTxt.getText());
				if (numTenters < 1)
					throw new ParseException("Tenters error", 1);

				unit.setCheckIn(occupiedDate);
				unit.setNameReserving(name);
				unit.setSiteNumber(siteNum);
				unit.setDaysStaying(reservDays);
				unit.setNumOfTenters(numTenters);
				dispose();
			}
			catch(ParseException ex1) {
				JOptionPane.showMessageDialog(null, "Input Error",
						"Input Error", JOptionPane.ERROR_MESSAGE);
			}
			catch(NumberFormatException ex2) {
				JOptionPane.showMessageDialog(null, "Input Error",
						"Input Error", JOptionPane.ERROR_MESSAGE);
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
