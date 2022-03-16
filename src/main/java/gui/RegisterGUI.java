package gui;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Label;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JPasswordField;


import businessLogic.*;
import domain.User;


import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import java.awt.Dimension;

public class RegisterGUI extends JFrame {
	private JTextField nameTextField;
	private JTextField lastNameTextField;
	private JTextField emailTextField;
	private JTextField userNameTextField;
	private JPasswordField passwordField;
	private JTextField dateTextField;
	private JButton signUpBtn;
	
	private BlFacade businessLogic;
	private JLabel statusLbl;
	
	
	public RegisterGUI(BlFacade bl) {
		getContentPane().setBackground(Color.LIGHT_GRAY);
		businessLogic = bl;
		JLabel lblNewLabel = new JLabel("Welcome to BET&RUIN");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		
		JLabel lblNewLabel_4 = new JLabel("Username:");
		
		userNameTextField = new JTextField();
		userNameTextField.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("Password:");
		
		passwordField = new JPasswordField();
		
		JLabel lblNewLabel_1 = new JLabel("Name:");
		
		nameTextField = new JTextField();
		nameTextField.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Last name:");
		
		lastNameTextField = new JTextField();
		lastNameTextField.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Email:");
		
		emailTextField = new JTextField();
		emailTextField.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("Date of birth:");
		
		dateTextField = new JTextField();
		dateTextField.setText("dd/mm/yyyy");
		dateTextField.setColumns(10);
		
		captureNewUser();
		
		statusLbl = new JLabel("");
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(155)
									.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(35)
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addComponent(lblNewLabel_1)
										.addComponent(lblNewLabel_2)
										.addComponent(lblNewLabel_3)
										.addComponent(lblNewLabel_5)
										.addComponent(lblNewLabel_4)
										.addComponent(lblNewLabel_6, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE))
									.addGap(32)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(emailTextField, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE)
										.addComponent(lastNameTextField, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE)
										.addComponent(nameTextField, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE)
										.addComponent(userNameTextField, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE)
										.addComponent(dateTextField, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE)
										.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE)
										.addComponent(statusLbl))))
							.addGap(196))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(signUpBtn)
							.addGap(131))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(5)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(7)
							.addComponent(lblNewLabel_1))
						.addComponent(nameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_2)
						.addComponent(lastNameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_3)
						.addComponent(emailTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(dateTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_6))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(userNameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_4))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_5))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(statusLbl)
						.addComponent(signUpBtn))
					.addGap(7))
		);
		getContentPane().setLayout(groupLayout);
		this.setMinimumSize(new Dimension(450,300));
		getContentPane().setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{lblNewLabel, lblNewLabel_1, lblNewLabel_2, lblNewLabel_3, lblNewLabel_5, lblNewLabel_4, lblNewLabel_6, emailTextField, lastNameTextField, nameTextField, userNameTextField, dateTextField, passwordField, signUpBtn}));
	}
	private void captureNewUser() {
		signUpBtn = new JButton("Sign-Up");
		signUpBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				User user;
				try {
					user = parseUser();
					boolean registered = businessLogic.checkIfRegistered(user);
					if(!registered) {
						businessLogic.register(user);
					}else {
						statusLbl.setText("User is already registered!");
						//Display message, user already registered
					}
				} catch (ParseException e1) {
					e1.printStackTrace();
					System.out.println("Date was introduced incorrectly...");
				}
				
			}
	
				
			
		});
	}
	
	private User parseUser() throws ParseException {
		String name = nameTextField.getText();
		String lastname = lastNameTextField.getText();
		String email =  emailTextField.getText();
		String userName = userNameTextField.getText();
		String password = new String(passwordField.getPassword());
		String date = dateTextField.getText();
		
		SimpleDateFormat formatter1=new SimpleDateFormat("dd/MM/yyyy"); 
		
		Date uDate;
		
			uDate = formatter1.parse(date);
			return new User(userName,password,name,lastname,email,uDate);
		
	}
	
	public BlFacade getBusinessLogic() {
		return businessLogic;
	}
	public void setBusinessLogic(BlFacade businessLogic) {
		this.businessLogic = businessLogic;
	}
	
	
	private void setThisMinimumSize(Dimension minimumSize) {
		setMinimumSize(minimumSize);
	}
}
