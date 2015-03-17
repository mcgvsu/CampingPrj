/**********************************************************************
Dialog used to return information the status of a camp site based on
the given date.

@author Conner Toney
@author Mike Kolarik
@version GVSU Winter 2015
 *********************************************************************/

package package1;

import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;

public class DialogGetDateStatus extends JDialog 
implements ActionListener {

	/** default serial ID */
	private static final long serialVersionUID = 1L;

	/** used to display content */
	private JPanel panel;

	/** contains text concerning site statuses */
	private JTextArea statusText;

	/** used to gather input date */
	private JTextField inputDateTxt;

	/** used to close out of window */
	private JButton closeButton;

	/** used to attempt to return status information */
	private JButton okButton;

	/** input date */
	private Date inputDate;

	/** list of input Sites */
	private ArrayList<Site> list;

	/******************************************************************
    Constructor creates the dialog GUI
    @param lockedFrame GUI frame that is locked while dialog is open
    @param inputTent input Tent
	 *****************************************************************/
	public DialogGetDateStatus(JFrame lockedFrame, 
			ArrayList<Site> listOfSites) {
		
		//locks given frame while dialog is open, sets title
		super(lockedFrame, "Camp Site Date Status", true);
		list = listOfSites;
		panel = new JPanel();
		
		//sets up dialog content
		statusText = new JTextArea(5, 65);
		inputDateTxt = new JTextField("MM/DD/YYYY", 10);
		okButton = new JButton("OK");
		okButton.addActionListener(this);
		closeButton = new JButton("Close");
		closeButton.addActionListener(this);
		
		//adds everything to panel
		panel.add(statusText);
		panel.add(inputDateTxt);
		panel.add(okButton);
		panel.add(closeButton);
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
			SimpleDateFormat dateFormat;
			dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			try {
				inputDate = dateFormat.parse(inputDateTxt.getText());
				GregorianCalendar inputCal = new GregorianCalendar();
				inputCal.setTime(inputDate);
				getContent();
			}
			catch (ParseException ex) {
				JOptionPane.showMessageDialog(null, "Input Error",
						"Input Error", JOptionPane.ERROR_MESSAGE);
			}
			pack();
		}
		if (e.getSource() == closeButton) {
			dispose();
		}
	}

	/******************************************************************
    Gets the current closing status of the dialog window
    @return inputDate
	 *****************************************************************/
	public Date getDate() {
		return inputDate;
	}


	/******************************************************************
    Gets the content used to determine the status of dates for
    all input camp sites
	 *****************************************************************/
	public void getContent() {
		String content = "";
		
		//iterates through list of sites, retrieving information from
		//each site and then outputting name, check-in date, site #,
		//estimated stay, and days remaining
		for (int i = 0; i < list.size(); i++) {

			//adds name
			content += ("Name: ");
			content += (list.get(i).getNameReserving() + "\t");

			//adds check in date
			GregorianCalendar c = list.get(i).getCheckIn();
			Date date = c.getTime();
			SimpleDateFormat dateFormat;
			dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			content += ("Checked in: ");
			content += (dateFormat.format(date));
			content += ("\t");

			//adds site
			content += ("Site: ");
			content += (list.get(i).getSiteNumber() + "\t");

			//estimated stay
			content += ("Estimated stay: ");
			content += (list.get(i).getDaysStaying() + " days \t");

			//days remaining
			Calendar advCal = Calendar.getInstance();
			advCal.setTime(date);
			advCal.add(Calendar.DATE, list.get(i).getDaysStaying());
			Date advDate = advCal.getTime();
			
			//calculates time difference between dates
			long dateDiff = advDate.getTime() - inputDate.getTime();
			int dayDiff = (int) (dateDiff / (24 * 60 * 60 *1000));
			if (date.getTime() > inputDate.getTime()) 
				content += ("Days Remaining: N/A");
			else
				content += ("Days Remaining:" + dayDiff);

			content += ("\n");
		}
		statusText.setText(content);
	}
}
