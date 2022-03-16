package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BlFacade;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.LayoutStyle.ComponentPlacement;

public class UserViewGUI extends JFrame {

	private JPanel contentPane;
	private JButton browseQuestionsBtn;
	private BlFacade businessLogic;
	private String currentUser;
	private JButton closeBtn;

	/**
	 * Create the frame.
	 */
	public UserViewGUI(BlFacade bl, String currentUs) {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		businessLogic = bl;
		this.currentUser = currentUs;
		
		JLabel lblNewLabel = new JLabel("Welcome "+ this.currentUser+"!");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 27));
		
		initializeBrowseQuestionsBtn();
		
		intializeCloseBtn();
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(41)
					.addComponent(browseQuestionsBtn)
					.addContainerGap(243, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(107, Short.MAX_VALUE)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 266, GroupLayout.PREFERRED_SIZE)
					.addGap(67))
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addGap(178)
					.addComponent(closeBtn)
					.addContainerGap(185, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel)
					.addGap(51)
					.addComponent(browseQuestionsBtn)
					.addPreferredGap(ComponentPlacement.RELATED, 108, Short.MAX_VALUE)
					.addComponent(closeBtn)
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}

	private void intializeCloseBtn() {
		closeBtn = new JButton("Close");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
	}

	private void initializeBrowseQuestionsBtn() {
		browseQuestionsBtn = new JButton("Browse Questions");
		browseQuestionsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BrowseQuestionsGUI findQuestionsWindow = new BrowseQuestionsGUI(businessLogic);
				findQuestionsWindow.setVisible(true);
			}
		});
	}
}
