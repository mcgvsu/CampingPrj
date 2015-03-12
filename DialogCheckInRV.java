package package1;

import java.awt.GridLayout;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.*;

public class DialogCheckInRV extends JDialog implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JLabel nameLabel;
	private JTextField nameText;
	private JLabel occupiedOnLabel;
	private JTextField occupiedOnTxt;
	private JLabel siteNumberLabel;
	private JTextField siteNumberTxt;
	private JLabel numOfDaysReservingLabel;
	private JTextField numOfDaysReservingTxt;
	private JLabel powerLabel;
	private JTextField powerTxt;
	private JButton okButton;
	private JButton cancelButton;
	private RV unit;
	private JPanel panel;
	private int closeStatus;

	public DialogCheckInRV(JFrame pOccupy, RV pUnit) {
		super(pOccupy, "RV Check in", true);
		unit = pUnit;
		panel = new JPanel();
		panel.setLayout(new GridLayout(6, 2));
		nameLabel = new JLabel("Name: ");
		nameText = new JTextField();
		occupiedOnLabel = new JLabel("Occupied on (DD/MM/YYYY): ");
		occupiedOnTxt = new JTextField();
		siteNumberLabel = new JLabel("Requested site number: ");
		siteNumberTxt = new JTextField();
		numOfDaysReservingLabel = new JLabel("Days reserved: ");
		numOfDaysReservingTxt = new JTextField();
		powerLabel = new JLabel("Power usage: ");
		powerTxt = new JTextField();
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
		panel.add(powerLabel);
		panel.add(powerTxt);
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

				//parses for power, throws exception if not 30 40 or 50
				int powerNum = Integer.parseInt(powerTxt.getText());
				if (!(powerNum == 30 || powerNum == 40 || powerNum == 50))
					throw new ParseException("Power error", 1);

				unit.setCheckIn(occupiedDate);
				unit.setNameReserving(name);
				unit.setSiteNumber(siteNum);
				unit.setDaysStaying(reservDays);
				unit.setPower(powerNum);
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
