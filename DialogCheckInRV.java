/**********************************************************************
Dialog form used to gather information in order to keep track of
an RV Site.

@author Conner Toney
@author Mike Kolarik
@version GVSU Winter 2015
 *********************************************************************/

package package1;

import java.awt.GridLayout;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.*;

public class DialogCheckInRV extends JDialog implements ActionListener{

	/** default serial ID */
	private static final long serialVersionUID = 1L;

	/** label used to prompt for name */
	private JLabel nameLbl;

	/** text field used to gather name */
	private JTextField nameTxt;

	/** used to prompt for check-in date */
	private JLabel checkInOnLbl;

	/** used to gather check-in date */
	private JTextField checkedInOnTxt;

	/** used to prompt for site number */
	private JLabel siteNumLbl;

	/** used to gather site number */
	private JTextField siteNumTxt;

	/** used to prompt for amount of reservation days */
	private JLabel numReservationDaysLbl;

	/** used to gather amount of reservation days */
	private JTextField numReservationDaysTxt;

	/** used to prompt for power amount */
	private JLabel powerLbl;

	/** used to gather power amount */
	private JTextField powerTxt;

	/** used to attempt to set Site to input information */
	private JButton okButton;

	/** used to cancel out of dialog */
	private JButton cancelButton;

	/** generic RV object used */
	private RV rvSite;

	/** used to display form */
	private JPanel panel;

	/** notifies outside classes of close status */
	private int closeStatus;

	/******************************************************************
    Constructor creates the dialog form GUI
    @param lockedFrame GUI frame that is locked while dialog is open
    @param inputRV input RV
	 *****************************************************************/
	public DialogCheckInRV(JFrame lockedFrame, RV inputRV) {

		//locks given frame while dialog is open, sets title
		super(lockedFrame, "RV Check in", true);
		rvSite = inputRV;
		panel = new JPanel();
		panel.setLayout(new GridLayout(6, 2));

		//sets up form content
		nameLbl = new JLabel("Last Name: ");
		nameTxt = new JTextField("Doe");
		checkInOnLbl = new JLabel("Check-in (DD/MM/YYYY): ");
		checkedInOnTxt = new JTextField("03/17/2015");
		siteNumLbl = new JLabel("Requested site number (1-5): ");
		siteNumTxt = new JTextField("1");
		numReservationDaysLbl = new JLabel("Days reserved: ");
		numReservationDaysTxt = new JTextField("2");
		powerLbl = new JLabel("Power usage (30/40/50): ");
		powerTxt = new JTextField("30");
		okButton = new JButton("OK");
		okButton.addActionListener(this);
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);

		//adds everything to the panel
		panel.add(nameLbl);
		panel.add(nameTxt);
		panel.add(checkInOnLbl);
		panel.add(checkedInOnTxt);
		panel.add(siteNumLbl);
		panel.add(siteNumTxt);
		panel.add(numReservationDaysLbl);
		panel.add(numReservationDaysTxt);
		panel.add(powerLbl);
		panel.add(powerTxt);
		panel.add(okButton);
		panel.add(cancelButton);
		getContentPane().add(panel);
		pack();
	}

	/******************************************************************
    Determines which action to follow based on what buttons were
    selected
    @param e the action event
	 *****************************************************************/
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == okButton) {

			//used to let outside classes that OK has been selected
			closeStatus = 1;
			SimpleDateFormat dateFormat;
			dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			Date date;
			try {

				//parses date, throws exception if parse fails
				date = dateFormat.parse(checkedInOnTxt.getText());
				GregorianCalendar checkInDate;
				checkInDate = new GregorianCalendar();
				checkInDate.setTime(date);

				//parses for name, throws exception if anything
				//but letters are in string
				String name = nameTxt.getText();
				if (!name.matches("[a-zA-Z]+"))
					throw new ParseException("Error - not letters", 1);

				//parses for site#, throws exception if siteNum
				//is less than 1 or greater than 5
				int siteNum = Integer.parseInt(siteNumTxt.getText());
				if (siteNum < 1 || siteNum > 5)
					throw new ParseException("Site# error", 1);

				//parses for reservation day amount, throws exception 
				//if less than 1
				int reservDays = Integer.
						parseInt(numReservationDaysTxt.getText());
				if (reservDays < 1)
					throw new ParseException("Reservation error", 1);

				//parses for power, throws exception if not 30 40 or 50
				int powerNum = Integer.parseInt(powerTxt.getText());
				if (!(powerNum == 30 || powerNum == 40 
						|| powerNum == 50))
					throw new ParseException("Power error", 1);

				//sets parsed information to the input RV
				rvSite.setCheckIn(checkInDate);
				rvSite.setNameReserving(name);
				rvSite.setSiteNumber(siteNum);
				rvSite.setDaysStaying(reservDays);
				rvSite.setPower(powerNum);
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

			//used to let outside classes know cancel has been selected
			closeStatus = 0;
			dispose();
		}
	}

	/******************************************************************
    Gets the current closing status of the dialog window
    @return closeStatus
	 *****************************************************************/
	public int getCloseStatus() {
		return closeStatus;
	}
}
