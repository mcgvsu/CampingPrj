/**********************************************************************
Dialog form used to gather information in order to keep track of
an Tent Site.

@author Conner Toney
@author Mike Kolarik
@version GVSU Winter 2015
 *********************************************************************/

package package1;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.*;

public class DialogCheckInTent extends JDialog 
implements ActionListener {

	/** default serial ID */
	private static final long serialVersionUID = 1L;

	/** label used to prompt for name */
	private JLabel nameLbl;

	/** text field used to gather name */
	private JTextField nameTxt;

	/** used to prompt for check-in date */
	private JLabel checkedInOnLbl;

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

	/** used to prompt for tenters amount */
	private JLabel numTentersLbl;

	/** used to gather amount of tenters */
	private JTextField numTentersTxt;

	/** used to attempt to set Site to input information */
	private JButton okButton;

	/** used to cancel out of dialog */
	private JButton cancelButton;

	/** generic Tent object used */
	private Tent tentSite;

	/** used to display form */
	private JPanel panel;

	/** notifies outside classes of close status */
	private int closeStatus;

	/******************************************************************
    Constructor creates the dialog form GUI
    @param lockedFrame GUI frame that is locked while dialog is open
    @param inputTent input Tent
	 *****************************************************************/
	public DialogCheckInTent(JFrame lockedFrame, Tent inputTent) {

		//locks given frame while dialog is open, sets title
		super(lockedFrame, "Tent Check in", true);
		panel = new JPanel();
		tentSite = inputTent;
		panel.setLayout(new GridLayout(6, 2));

		//sets up form content
		nameLbl = new JLabel("Last Name: ");
		nameTxt = new JTextField("Doe");
		checkedInOnLbl = new JLabel("Checked-in (DD/MM/YYYY): ");
		checkedInOnTxt = new JTextField("03/17/2015");
		siteNumLbl = new JLabel("Requested site number (1-5): ");
		siteNumTxt = new JTextField("1");
		numReservationDaysLbl = new JLabel("Days reserved: ");
		numReservationDaysTxt = new JTextField("2");
		numTentersLbl = new JLabel("Number of tenters: ");
		numTentersTxt = new JTextField("3");
		okButton = new JButton("OK");
		okButton.addActionListener(this);
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);

		//adds everything to panel
		panel.add(nameLbl);
		panel.add(nameTxt);
		panel.add(checkedInOnLbl);
		panel.add(checkedInOnTxt);
		panel.add(siteNumLbl);
		panel.add(siteNumTxt);
		panel.add(numReservationDaysLbl);
		panel.add(numReservationDaysTxt);
		panel.add(numTentersLbl);
		panel.add(numTentersTxt);
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
				GregorianCalendar checkInDate = new GregorianCalendar();
				checkInDate.setTime(date);

				//parses for name, throws exception if anything
				//but letters are in string
				String name = nameTxt.getText();
				if (!name.matches("[a-zA-Z]+"))
					throw new ParseException("Error - not letters", 1);

				//parses for site#, throws exception if less than 1
				// or greater than 5
				int siteNum = Integer.parseInt(siteNumTxt.getText());
				if (siteNum < 1 || siteNum > 5)
					throw new ParseException("Site error", 1);

				//parses for reservation day amount, throws exception 
				//if less than 1
				int reservDays = Integer.
						parseInt(numReservationDaysTxt.getText());
				if (reservDays < 1)
					throw new ParseException("Reservation error", 1);

				//parses for tenters, throws exception if less than 1
				int numTenters = Integer.
						parseInt(numTentersTxt.getText());
				if (numTenters < 1)
					throw new ParseException("Tenters error", 1);

				//sets parsed information to the input Tent
				tentSite.setCheckIn(checkInDate);
				tentSite.setNameReserving(name);
				tentSite.setSiteNumber(siteNum);
				tentSite.setDaysStaying(reservDays);
				tentSite.setNumOfTenters(numTenters);
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
