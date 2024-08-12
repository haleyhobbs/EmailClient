
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class ComposeGUI extends JFrame {
	private JPanel contentPane;
	private JLabel lblRecipient;
	private JLabel lblSubject;
	private JButton btnSend;
	private JButton btnCancel;
	private JTextField textFieldSubject;
	private JTextField textFieldRecipient;
	private JTextField textFieldMessage;
	private String recipient;
	private String subject;
	private String message;
	private Email email;
	private EmailDatabase emailDatabase;

	//constructor when user doesn't enter recipient
	public ComposeGUI(String sender) {
		this(sender, "");
	}

	//constructor when recipient is entered (automatically filled when replying)
	public ComposeGUI(String sender, String recipient) {
		this.recipient = recipient;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblRecipient = new JLabel("To:");
		lblRecipient.setBounds(6, 20, 61, 16);
		contentPane.add(lblRecipient);

		textFieldRecipient = new JTextField(recipient);
		textFieldRecipient.setBounds(79, 15, 331, 26);
		contentPane.add(textFieldRecipient);
		textFieldRecipient.setColumns(10);

		lblSubject = new JLabel("Subject:");
		lblSubject.setBounds(6, 53, 61, 16);
		contentPane.add(lblSubject);

		textFieldSubject = new JTextField();
		textFieldSubject.setBounds(78, 48, 331, 26);
		contentPane.add(textFieldSubject);
		textFieldSubject.setColumns(10);

		textFieldMessage = new JTextField();
		textFieldMessage.setBounds(6, 76, 438, 165);
		contentPane.add(textFieldMessage);
		textFieldMessage.setColumns(10);

		btnSend = new JButton("Send");
		btnSend.setBounds(325, 245, 82, 29);
		btnSend.addActionListener(e -> {
			this.recipient = textFieldRecipient.getText();
			this.subject = textFieldSubject.getText();
			this.message = textFieldMessage.getText();

			//create email object with user-entered information
			email = new Email(sender, this.recipient, this.subject, this.message);
			
			//add email to database
			try {
				emailDatabase = new EmailDatabase(sender);
				emailDatabase.sendEmail(email);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			dispose();
		});
		btnSend.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		btnSend.setBounds(375, 240, 75, 26);
		contentPane.add(btnSend);

		btnCancel = new JButton("X");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(406, 0, 44, 26);
		contentPane.add(btnCancel);
	}
}
