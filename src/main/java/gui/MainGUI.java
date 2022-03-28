package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

import businessLogic.BlFacade;


public class MainGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel mainPane;
	protected JLabel selectOptionLbl;
	private JPanel localePane;
	private JRadioButton euskaraRbtn;
	private JRadioButton castellanoRbtn;
	private JRadioButton englishRbtn;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	private BlFacade businessLogic;
	private JButton btnLogin;
	private JButton btnRegister;

	public BlFacade getBusinessLogic(){
		return businessLogic;
	}

	public void setBussinessLogic (BlFacade afi){
		businessLogic = afi;
	}


	public MainGUI() {
		super();

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					//if (ConfigXML.getInstance().isBusinessLogicLocal()) facade.close();
				}
				catch (Exception e1) {
					System.out.println("Error: " + e1.toString() + " , likely problems "
							+ "with Business Logic or Data Accesse");
				}
				System.exit(1);
			}
		});

		this.setBounds(100, 100, 566, 428);

		this.initializeMainPane();
		this.setContentPane(mainPane);
		
			
		btnLogin.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.btnLogin.text")); //$NON-NLS-1$ //$NON-NLS-2$
			

		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainTitle"));
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	
	
	private void initializeMainPane() {
		mainPane = new JPanel();
		mainPane.setLayout(new GridLayout(4, 1, 0, 0));

		selectOptionLbl = new JLabel(ResourceBundle.getBundle("Etiquetas").
				getString("SelectUseCase"));
		selectOptionLbl.setHorizontalAlignment(SwingConstants.CENTER);
		mainPane.add(selectOptionLbl);


		intializeLoginBtn();
		mainPane.add(btnLogin);
		
		initializeRegisterBtn();
		mainPane.add(btnRegister);
		

		initializeLocalePane();
		mainPane.add(localePane);
		
		
		
	}

	private void initializeRegisterBtn() {
		btnRegister = new JButton();
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterGUI register = new RegisterGUI(businessLogic);
				register.setVisible(true);
			}
		});
		btnRegister.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.btnLogin_1.text")); //$NON-NLS-1$ //$NON-NLS-2$
	}

	

	

	private void intializeLoginBtn() {
		btnLogin = new JButton();
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI login = new LoginGUI(businessLogic);
				login.setVisible(true);
			}
		});
		
		
	}

	

	

	private void initializeLocalePane() {
		localePane = new JPanel();

		initializeEuskaraRbtn();
		localePane.add(euskaraRbtn);

		initializeCastellanoRbtn();
		localePane.add(castellanoRbtn);

		initializeEnglishRbtn();
		localePane.add(englishRbtn);
	}

	private void initializeEuskaraRbtn() {
		euskaraRbtn = new JRadioButton("Euskara");
		euskaraRbtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				Locale.setDefault(new Locale("eus"));
				System.out.println("Locale: " + Locale.getDefault());
				redraw();
			}
		});
		buttonGroup.add(euskaraRbtn);
	}

	private void initializeCastellanoRbtn() {
		castellanoRbtn = new JRadioButton("Castellano");
		castellanoRbtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				Locale.setDefault(new Locale("es"));
				System.out.println("Locale: " + Locale.getDefault());
				redraw();
			}
		});
		buttonGroup.add(castellanoRbtn);
	}

	private void initializeEnglishRbtn() {
		englishRbtn = new JRadioButton("English");
		englishRbtn.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent e) {
				Locale.setDefault(new Locale("en"));
				System.out.println("Locale: " + Locale.getDefault());
				redraw();				}
		});
		buttonGroup.add(englishRbtn);
	}

	private void redraw() {
		selectOptionLbl.setText(ResourceBundle.getBundle("Etiquetas").
				getString("SelectUseCase"));
		
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainTitle"));
	}
}