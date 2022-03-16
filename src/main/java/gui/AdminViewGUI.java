package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BlFacade;
import domain.Event;

import java.awt.Color;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.awt.event.ActionEvent;

public class AdminViewGUI extends JFrame {

	private JPanel contentPane;
	private JButton setFeeBtn;
	private JButton createQuestionBtn;
	private JButton createEventBtn;
	private JButton browseQuestionsBtn;
	private JButton closeBtn;
	private BlFacade businessLogic;


	/**
	 * Create the frame.
	 */
	public AdminViewGUI(BlFacade bl) {
		setBounds(100, 100, 450, 300);
		setMinimumSize(new Dimension (600,450));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		businessLogic = bl;
		
		JLabel lblNewLabel = new JLabel("WELCOME ADMIN!");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 27));
		
		setFeeBtnInitialize();
		
		createEventBtnInitialize();
		
		createQuestionBtnInitialize();
		
		initializeBrowseQuestionsBtn();
		
		closeBtnInitialize();
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(62)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 262, GroupLayout.PREFERRED_SIZE)
							.addGap(150))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
									.addComponent(setFeeBtn)
									.addPreferredGap(ComponentPlacement.RELATED, 155, Short.MAX_VALUE)
									.addComponent(createEventBtn))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(createQuestionBtn)
									.addPreferredGap(ComponentPlacement.RELATED, 147, Short.MAX_VALUE)
									.addComponent(browseQuestionsBtn)))
							.addGap(55))))
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap(276, Short.MAX_VALUE)
					.addComponent(closeBtn)
					.addGap(235))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
					.addGap(69)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(setFeeBtn)
						.addComponent(createEventBtn))
					.addGap(95)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(createQuestionBtn)
						.addComponent(browseQuestionsBtn))
					.addGap(83)
					.addComponent(closeBtn)
					.addContainerGap(16, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}


	private void closeBtnInitialize() {
		closeBtn = new JButton("Close");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
	}


	private void initializeBrowseQuestionsBtn() {
		browseQuestionsBtn = new JButton();
		browseQuestionsBtn.setText("Browse Questions");
		browseQuestionsBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				BrowseQuestionsGUI findQuestionsWindow = new BrowseQuestionsGUI(businessLogic);
				findQuestionsWindow.setVisible(true);
			}
		});
	}


	private void createQuestionBtnInitialize() {
		createQuestionBtn = new JButton("Create new question");
		createQuestionBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				CreateQuestionGUI createQuestionWindow = new CreateQuestionGUI(businessLogic,
						new Vector<Event>());
				createQuestionWindow.setBusinessLogic(businessLogic);
				createQuestionWindow.setVisible(true);
				
			}
		});
	}


	private void createEventBtnInitialize() {
		createEventBtn = new JButton("Create new event");
		createEventBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				CreateNewEventGUI cr = new CreateNewEventGUI(businessLogic, null);
				cr.setVisible(true);
				
				
			}
		});
	}


	private void setFeeBtnInitialize() {
		setFeeBtn = new JButton("Set fee for question");
		setFeeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				SetFee4QuestionGUI setfee = new SetFee4QuestionGUI(businessLogic);
				setfee.setVisible(true);
				
			}
		});
	}
}
