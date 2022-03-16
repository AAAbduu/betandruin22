package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.toedter.calendar.JCalendar;

import businessLogic.BlFacade;
import configuration.UtilDate;
import domain.Event;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;

public class CreateNewEventGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private BlFacade businessLogic;
	DefaultComboBoxModel<Event> eventModel = new DefaultComboBoxModel<Event>();
	private JLabel eventDateLbl = new JLabel(ResourceBundle.getBundle("Etiquetas").
			getString("EventDate"));
	private JCalendar calendar = new JCalendar();
	private Calendar previousCalendar = null;
	private Calendar currentCalendar = null;

	private JScrollPane eventScrollPane = new JScrollPane();
	private JButton closeBtn = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	private JLabel msgLbl = new JLabel();

	private Vector<Date> datesWithEventsInCurrentMonth = new Vector<Date>();
	private final JPanel panel = new JPanel();
	private final JTextField localTeamField = new JTextField();
	private final JTextField visiterTeamField = new JTextField();
	private final JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateNewEventGUI.lblNewLabel.text")); //$NON-NLS-1$ //$NON-NLS-2$
	private final JButton addEventBtn = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateNewEventGUI.btnNewButton.text")); //$NON-NLS-1$ //$NON-NLS-2$

	public void setBusinessLogic(BlFacade bl) {
		businessLogic = bl;		
	}

	public CreateNewEventGUI(BlFacade bl, Vector<domain.Event> v) {
		
		addEventBtnHandler();
		
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 37));
		visiterTeamField.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateNewEventGUI.textField_1.text")); //$NON-NLS-1$ //$NON-NLS-2$
		visiterTeamField.setColumns(10);
		localTeamField.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateNewEventGUI.textField.text")); //$NON-NLS-1$ //$NON-NLS-2$
		localTeamField.setColumns(10);
		businessLogic = bl;
		try {
			jbInit(v);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addEventBtnHandler() {
		addEventBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Date d = calendar.getDate();
				String local = localTeamField.getText();
				String visit = visiterTeamField.getText();
				
				String event = local + "-" +visit ;
				
				Event toAdd = new Event (event, d);
				
				//CALL BUSINESSLOGIC TO ADD IN DB
				
			}	
		});
	}

	private void jbInit(Vector<domain.Event> v) throws Exception {

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(604, 370));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("CreateQuestion"));

		calendar.setBounds(new Rectangle(40, 50, 225, 150));
		eventScrollPane.setBounds(new Rectangle(25, 44, 346, 116));
		closeBtn.setBounds(new Rectangle(226, 312, 130, 30));
		closeBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});

		msgLbl.setBounds(new Rectangle(275, 182, 305, 20));
		msgLbl.setForeground(Color.red);

		this.getContentPane().add(msgLbl, null);

		this.getContentPane().add(closeBtn, null);

		this.getContentPane().add(calendar, null);

		datesWithEventsInCurrentMonth = businessLogic.getEventsMonth(calendar.getDate());
		paintDaysWithEvents(calendar,datesWithEventsInCurrentMonth);

		eventDateLbl.setBounds(new Rectangle(40, 15, 140, 25));
		eventDateLbl.setBounds(40, 16, 140, 25);
		getContentPane().add(eventDateLbl);
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBounds(70, 209, 455, 102);
		
		getContentPane().add(panel);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(24)
					.addComponent(localTeamField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(53)
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
					.addComponent(visiterTeamField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(41))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(163)
					.addComponent(addEventBtn)
					.addContainerGap(175, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(27)
							.addComponent(localTeamField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(25)
							.addComponent(visiterTeamField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(17)
							.addComponent(lblNewLabel)))
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(addEventBtn)
					.addContainerGap())
		);
		panel.setLayout(gl_panel);


		// Code for JCalendar
		this.calendar.addPropertyChangeListener(new PropertyChangeListener() {
		
			public void propertyChange(PropertyChangeEvent propertychangeevent) {
				if (propertychangeevent.getPropertyName().equals("locale")) {
					calendar.setLocale((Locale) propertychangeevent.getNewValue());
				} else if (propertychangeevent.getPropertyName().equals("calendar")) {
					currentCalendar = (Calendar) propertychangeevent.getOldValue();
					previousCalendar = (Calendar) propertychangeevent.getNewValue();
					System.out.println("calendarAnt: "+currentCalendar.getTime());
					System.out.println("calendarAct: "+previousCalendar.getTime());
					DateFormat dateformat1 = DateFormat.getDateInstance(1, calendar.getLocale());

					int monthAnt = currentCalendar.get(Calendar.MONTH);
					int monthAct = previousCalendar.get(Calendar.MONTH);
					if (monthAct!=monthAnt) {
						if (monthAct==monthAnt+2) { 
							// Si en JCalendar estÃ¡ 30 de enero y se avanza al mes siguiente, 
							// devolverá 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este código se dejará como 1 de febrero en el JCalendar
							previousCalendar.set(Calendar.MONTH, monthAnt + 1);
							previousCalendar.set(Calendar.DAY_OF_MONTH, 1);
						}

						calendar.setCalendar(previousCalendar);

						datesWithEventsInCurrentMonth = businessLogic.getEventsMonth(calendar.getDate());
					}

					paintDaysWithEvents(calendar,datesWithEventsInCurrentMonth);

					Date firstDay = UtilDate.trim(previousCalendar.getTime());

					
				}
			}
		});
	}

	public static void paintDaysWithEvents(JCalendar jCalendar, 
			Vector<Date> datesWithEventsCurrentMonth) {
		// For each day with events in current month, the background color for that day is changed.

		Calendar calendar = jCalendar.getCalendar();

		int month = calendar.get(Calendar.MONTH);
		int today = calendar.get(Calendar.DAY_OF_MONTH);
		int year = calendar.get(Calendar.YEAR);

		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int offset = calendar.get(Calendar.DAY_OF_WEEK);

		if (Locale.getDefault().equals(new Locale("es")))
			offset += 4;
		else
			offset += 5;

		for (Date d:datesWithEventsCurrentMonth){
			calendar.setTime(d);
			System.out.println(d);

			// Obtain the component of the day in the panel of the DayChooser of the
			// JCalendar.
			// The component is located after the decorator buttons of "Sun", "Mon",... or
			// "Lun", "Mar"...,
			// the empty days before day 1 of month, and all the days previous to each day.
			// That number of components is calculated with "offset" and is different in
			// English and Spanish

			Component o = jCalendar.getDayChooser().getDayPanel()
					.getComponent(calendar.get(Calendar.DAY_OF_MONTH) + offset);
			o.setBackground(Color.CYAN);
		}

		calendar.set(Calendar.DAY_OF_MONTH, today);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.YEAR, year);
	}

	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}