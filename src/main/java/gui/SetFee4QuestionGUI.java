package gui;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JCalendar;

import businessLogic.BlFacade;
import configuration.UtilDate;
import domain.Fee;
import domain.Question;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;

public class SetFee4QuestionGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private BlFacade businessLogic;

	private final JLabel eventDateLbl = new JLabel(ResourceBundle.getBundle("Etiquetas").
			getString("EventDate"));
	private final JLabel questionLbl = new JLabel(ResourceBundle.getBundle("Etiquetas").
			getString("Questions")); 
	private final JLabel eventLbl = new JLabel(ResourceBundle.getBundle("Etiquetas").
			getString("Events")); 

	private JButton closeBtn = new JButton(ResourceBundle.getBundle("Etiquetas").
			getString("Close"));

	// Code for JCalendar
	private JCalendar calendar = new JCalendar();
	private Calendar previousCalendar;
	private Calendar currentCalendar;
	private JScrollPane eventScrollPane = new JScrollPane();
	private JScrollPane questionScrollPane = new JScrollPane();

	private Vector<Date> datesWithEventsInCurrentMonth = new Vector<Date>();

	private JTable eventTable= new JTable();
	private JTable questionTable = new JTable();

	private DefaultTableModel eventTableModel;
	private DefaultTableModel questionTableModel;

	private String[] eventColumnNames = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("EventN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Event"), 

	};
	private String[] questionColumnNames = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("QuestionN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Question")
	};
	private JTextField feeTextField;
	private JPanel feePanel;
	private JTextField resultTextField;
	private JButton addFeeBtn;


	public void setBusinessLogic(BlFacade bl) {
		businessLogic = bl;
	}

	public SetFee4QuestionGUI(BlFacade bl) {
		businessLogic = bl;
		try {
			jbInit();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}


	private void jbInit() throws Exception {

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("BrowseQuestions"));

		eventDateLbl.setBounds(new Rectangle(40, 15, 140, 25));
		questionLbl.setBounds(138, 248, 69, 14);
		eventLbl.setBounds(295, 19, 259, 16);

		this.getContentPane().add(eventDateLbl, null);
		this.getContentPane().add(questionLbl);
		this.getContentPane().add(eventLbl);

		closeBtn.setBounds(new Rectangle(274, 419, 130, 30));

		closeBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				jButton2_actionPerformed(e);
			}
		});
		
		addBtnHandler();
		
		this.getContentPane().add(closeBtn, null);

		calendar.setBounds(new Rectangle(40, 50, 225, 150));

		datesWithEventsInCurrentMonth = businessLogic.getEventsMonth(calendar.getDate());
		CreateQuestionGUI.paintDaysWithEvents(calendar, datesWithEventsInCurrentMonth);

		// Code for JCalendar
		this.calendar.addPropertyChangeListener(new PropertyChangeListener() {

			
			public void propertyChange(PropertyChangeEvent propertyChangeEvent) {

				if (propertyChangeEvent.getPropertyName().equals("locale")) {
					calendar.setLocale((Locale) propertyChangeEvent.getNewValue());
				}
				else if (propertyChangeEvent.getPropertyName().equals("calendar")) {
					previousCalendar = (Calendar) propertyChangeEvent.getOldValue();
					currentCalendar = (Calendar) propertyChangeEvent.getNewValue();
					DateFormat dateformat1 = DateFormat.getDateInstance(1, calendar.getLocale());
					Date firstDay = UtilDate.trim(new Date(calendar.getCalendar().getTime().getTime()));

					int previousMonth = previousCalendar.get(Calendar.MONTH);
					int currentMonth = currentCalendar.get(Calendar.MONTH);

					if (currentMonth != previousMonth) {
						if (currentMonth == previousMonth + 2) {
							// Si en JCalendar está 30 de enero y se avanza al mes siguiente, 
							// devolvería 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este código se dejará como 1 de febrero en el JCalendar
							currentCalendar.set(Calendar.MONTH, previousMonth + 1);
							currentCalendar.set(Calendar.DAY_OF_MONTH, 1);
						}						

						calendar.setCalendar(currentCalendar);
						datesWithEventsInCurrentMonth = businessLogic.getEventsMonth(calendar.
								getDate());
					}

					CreateQuestionGUI.paintDaysWithEvents(calendar,datesWithEventsInCurrentMonth);

					try {
						eventTableModel.setDataVector(null, eventColumnNames);
						eventTableModel.setColumnCount(3); // another column added to allocate ev objects

						Vector<domain.Event> events = businessLogic.getEvents(firstDay);

						if (events.isEmpty() ) eventLbl.setText(ResourceBundle.getBundle("Etiquetas").
								getString("NoEvents") + ": " + dateformat1.format(currentCalendar.
										getTime()));
						else eventLbl.setText(ResourceBundle.getBundle("Etiquetas").
								getString("Events") + ": " + dateformat1.format(currentCalendar.
										getTime()));
						for (domain.Event ev : events){
							Vector<Object> row = new Vector<Object>();
							System.out.println("Events " + ev);
							row.add(ev.getEventNumber());
							row.add(ev.getDescription());
							row.add(ev); 	// ev object added in order to obtain it with 
							// tableModelEvents.getValueAt(i,2)
							eventTableModel.addRow(row);		
						}
						eventTable.getColumnModel().getColumn(0).setPreferredWidth(25);
						eventTable.getColumnModel().getColumn(1).setPreferredWidth(268);
						eventTable.getColumnModel().removeColumn(eventTable.getColumnModel().
								getColumn(2)); // not shown in JTable
					}
					catch (Exception e1) {
						questionLbl.setText(e1.getMessage());
					}
				}
			} 
		});

		this.getContentPane().add(calendar, null);

		eventScrollPane.setBounds(new Rectangle(292, 50, 346, 150));
		
		captureQuestionWhenClick();
		
		questionScrollPane.setBounds(new Rectangle(60, 274, 219, 116));

		eventTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = eventTable.getSelectedRow();
				domain.Event ev = (domain.Event)eventTableModel.getValueAt(i,2); // obtain ev object
				Vector<Question> queries = ev.getQuestions();

				questionTableModel.setDataVector(null, questionColumnNames);

				if (queries.isEmpty())
					questionLbl.setText(ResourceBundle.getBundle("Etiquetas").
							getString("NoQuestions") + ": " + ev.getDescription());
				else 
					questionLbl.setText(ResourceBundle.getBundle("Etiquetas").
							getString("SelectedEvent") + " " + ev.getDescription());

				for (domain.Question q : queries) {
					Vector<Object> row = new Vector<Object>();
					row.add(q.getQuestionNumber());
					row.add(q.getQuestion());
					questionTableModel.addRow(row);	
				}
				questionTable.getColumnModel().getColumn(0).setPreferredWidth(25);
				questionTable.getColumnModel().getColumn(1).setPreferredWidth(268);
			}
		});

		eventScrollPane.setViewportView(eventTable);
		eventTableModel = new DefaultTableModel(null, eventColumnNames);

		eventTable.setModel(eventTableModel);
		eventTable.getColumnModel().getColumn(0).setPreferredWidth(25);
		eventTable.getColumnModel().getColumn(1).setPreferredWidth(268);

		questionScrollPane.setViewportView(questionTable);
		questionTableModel = new DefaultTableModel(null, questionColumnNames);

		questionTable.setModel(questionTableModel);
		questionTable.getColumnModel().getColumn(0).setPreferredWidth(25);
		questionTable.getColumnModel().getColumn(1).setPreferredWidth(268);

		this.getContentPane().add(eventScrollPane, null);
		this.getContentPane().add(questionScrollPane, null);
		
		JLabel lblFee = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("SetFee4QuestionGUI.lblFee.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblFee.setBounds(458, 248, 69, 14);
		getContentPane().add(lblFee);
		
		feePanel = new JPanel();
		feePanel.setBackground(Color.LIGHT_GRAY);
		feePanel.setBounds(360, 274, 233, 116);
		getContentPane().add(feePanel);
		
		JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("SetFee4QuestionGUI.lblNewLabel.text")); //$NON-NLS-1$ //$NON-NLS-2$
		
		JLabel lblNewLabel_1 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("SetFee4QuestionGUI.lblNewLabel_1.text")); //$NON-NLS-1$ //$NON-NLS-2$
		
		resultTextField = new JTextField();
		
		resultTextField.setColumns(10);
		
		feeTextField = new JTextField();
		
		
		feeTextField.setColumns(10);
		
		
		GroupLayout gl_feePanel = new GroupLayout(feePanel);
		gl_feePanel.setHorizontalGroup(
			gl_feePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_feePanel.createSequentialGroup()
					.addGroup(gl_feePanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_feePanel.createSequentialGroup()
							.addGap(16)
							.addComponent(lblNewLabel))
						.addGroup(gl_feePanel.createSequentialGroup()
							.addGap(26)
							.addComponent(lblNewLabel_1)))
					.addGap(18)
					.addGroup(gl_feePanel.createParallelGroup(Alignment.LEADING)
						.addComponent(addFeeBtn)
						.addComponent(feeTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(resultTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(26, Short.MAX_VALUE))
		);
		gl_feePanel.setVerticalGroup(
			gl_feePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_feePanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_feePanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(resultTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_feePanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1)
						.addComponent(feeTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
					.addComponent(addFeeBtn)
					.addContainerGap())
		);
		feePanel.setVisible(false);
		feePanel.setLayout(gl_feePanel);
	}

	private void addBtnHandler() {
		addFeeBtn = new JButton(ResourceBundle.getBundle("Etiquetas").getString("SetFee4QuestionGUI.btnNewButton.text")); //$NON-NLS-1$ //$NON-NLS-2$
		addFeeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				int i = questionTable.getSelectedRow();
				int qn = (Integer) questionTableModel.getValueAt(i,0); // obtain question string
				
				Question q = businessLogic.getQuestion(qn);
				
				String r = resultTextField.getText();
				String f = feeTextField.getText();
				
				Fee fee = new Fee(r,Double.parseDouble(f));
				
				q.addFee(fee);
				
				businessLogic.updateQuestionFees(q);
				
				
				for(Fee y : q.getFees()) {
					System.out.println(y);
				}
								
			}
		});
	}

	private void captureQuestionWhenClick() {
		questionScrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			
				feePanel.setVisible(true);
				
				
				
			}
		});
	}

	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}