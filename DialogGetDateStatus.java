package package1;

import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.*;

public class DialogGetDateStatus extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JTextArea statusText;
	private JTextField inputDateTxt;
	private JButton closeButton;
	private JButton okButton;
	private Date inputDate;
	private ArrayList<Site> list;

	public DialogGetDateStatus(JFrame pOccupy, ArrayList<Site> listOfSites) {
		super(pOccupy, "Date Status", true);
		list = listOfSites;
		panel = new JPanel();
		statusText = new JTextArea(5, 75);
		inputDateTxt = new JTextField(10);
		okButton = new JButton("OK");
		okButton.addActionListener(this);
		closeButton = new JButton("Close");
		closeButton.addActionListener(this);
		panel.add(statusText);
		panel.add(inputDateTxt);
		panel.add(okButton);
		panel.add(closeButton);
		getContentPane().add(panel);
		pack();

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == okButton) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			try {
				inputDate = dateFormat.parse(inputDateTxt.getText());
				GregorianCalendar occupiedDate = new GregorianCalendar();
				occupiedDate.setTime(inputDate);
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

	public Date getDate() {
		return inputDate;
	}

	public void getContent() {
		String content = "";
		for (int i = 0; i < list.size(); i++) {

			//adds name
			content += ("Name: ");
			content += (list.get(i).getNameReserving() + "\t");

			//adds check in date
			GregorianCalendar c = list.get(i).getCheckIn();
			Date date = c.getTime();
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			content += ("Checked in: ");
			content += (dateFormat.format(date));
			content += ("\t");

			//adds site
			content += ("Site: ");
			content += (list.get(i).getSiteNumber() + "\t");

			//estimated stay
			content += ("Estimated days: ");
			content += (list.get(i).getDaysStaying() + "\t");

			//days remaining
			Calendar advCal = Calendar.getInstance();
			advCal.setTime(date);
			advCal.add(Calendar.DATE, list.get(i).getDaysStaying());
			Date advDate = advCal.getTime();
			//calculates time difference between dates
			long dateDiff = advDate.getTime() - inputDate.getTime();
			int dayDiff = (int) (dateDiff / (24 * 60 * 60 *1000));
			if (date.getTime() > inputDate.getTime()) {
				content += ("Days Remaining: N/A");
			}
			else
				content += ("Days Remaining:" + dayDiff);

			content += ("\n");
		}
		statusText.setText(content);
	}
}
