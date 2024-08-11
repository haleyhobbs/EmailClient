import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginGUI extends JFrame {
	private JPanel contentPane;
	private JTextField textFieldAddress;
	private JTextField textFieldPassword;
	private JLabel lblAddress;
	private JLabel lblPassword;
	private JButton btnLogin;
	private JButton btnQuit;
	private JLabel lblEmail;
	private String address;
	private String password;
	
	public LoginGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textFieldAddress = new JTextField();
		textFieldAddress.setBounds(147, 86, 255, 26);
		contentPane.add(textFieldAddress);
		textFieldAddress.setColumns(10);
		
		textFieldPassword = new JTextField();
		textFieldPassword.setBounds(147, 149, 255, 26);
		contentPane.add(textFieldPassword);
		textFieldPassword.setColumns(10);
		
		JLabel lblAddress = new JLabel("Email address:");
		lblAddress.setBounds(32, 91, 103, 16);
		contentPane.add(lblAddress);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(32, 154, 90, 16);
		contentPane.add(lblPassword);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				address = textFieldAddress.getText();
				password = textFieldPassword.getText();
				String loggedIn = checkCredentials(address, password);
				
				if (loggedIn != null) {
					HomeGUI homeFrame = new HomeGUI(loggedIn);
					homeFrame.setVisible(true);
					dispose();
				} else
					JOptionPane.showMessageDialog(null, "Invalid Login");
			}
		});
		btnLogin.setBounds(79, 215, 117, 29);
		contentPane.add(btnLogin);
		
		JButton btnQuit = new JButton("Exit Program");
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnQuit.setBounds(256, 215, 117, 29);
		contentPane.add(btnQuit);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		lblEmail.setBounds(181, 17, 90, 44);
		contentPane.add(lblEmail);
		
		JButton btnCancel = new JButton("X");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(406, 0, 44, 26);
		contentPane.add(btnCancel);
	}
	
	String checkCredentials(String address, String password) {
		if ((address.equals("guest1@gmail.com") && password.equals("OOP1")) || (address.equals("guest2@nyu.edu") && password.equals("OOP2")))
			return address;
		else return null;
	}
	
}
