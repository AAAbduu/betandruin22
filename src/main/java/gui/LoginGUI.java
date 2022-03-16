package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BlFacade;
import businessLogic.BlFacadeImplementation;

import java.awt.Color;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginGUI extends JFrame {

	private JPanel contentPane;
	private JTextField usernameTextField;
	private JPasswordField passwordField;
	private JButton loginBtn;
	BlFacade businessLogic;
	private JLabel statusLogin;



	public LoginGUI(BlFacade bl) {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("Welcome to BET&RUIN");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		
		JLabel lblNewLabel_1 = new JLabel("Username:");
		
		JLabel lblNewLabel_1_1 = new JLabel("Password:");
		
		usernameTextField = new JTextField();
		usernameTextField.setColumns(10);
		
		passwordField = new JPasswordField();
		
		initializeLoginBtn();
		businessLogic = bl;
		
		statusLogin = new JLabel("");
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(112)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_1)
						.addComponent(lblNewLabel_1_1, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(passwordField, 130, 130, 130)
						.addComponent(usernameTextField, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(96, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(136, Short.MAX_VALUE)
					.addComponent(lblNewLabel)
					.addGap(131))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(172)
					.addComponent(loginBtn)
					.addContainerGap(181, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(189)
					.addComponent(statusLogin)
					.addContainerGap(190, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel)
					.addGap(50)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1)
						.addComponent(usernameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(28)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1_1))
					.addGap(18)
					.addComponent(statusLogin)
					.addPreferredGap(ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
					.addComponent(loginBtn)
					.addGap(30))

		);
		statusLogin.setText("Introduce credentials");
		contentPane.setLayout(gl_contentPane);
	}

	private void initializeLoginBtn() {
		loginBtn = new JButton("Log-in");
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String usname = usernameTextField.getText();
				String psswd = new String (passwordField.getPassword());
				
				if(usname.contentEquals("admin") && psswd.contentEquals("admin")) {
					AdminViewGUI admin = new AdminViewGUI(businessLogic);
					admin.setVisible(true);
					setVisible(false);
					return;
				}
				
				boolean r = businessLogic.login(usname,psswd);
				
				if (r) {
					statusLogin.setText("Succesfully logged-in!");
					UserViewGUI usr = new UserViewGUI (businessLogic, usname);
					setVisible(false);
					usr.setVisible(true);
					return;
				}
				else statusLogin.setText("Credentials are not correct!");
				
				
				
			}
		});
	}

}
