//libraries
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JList;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InboxGUI extends JFrame {
    //fields
    private EmailDatabase emailDatabase;
	private JPanel contentPane;
	private JLabel lblInbox;
    private List<Email> inbox;
    private DefaultListModel<String> listModel;
    private JList<String> listEmails;
    private JScrollPane scrollPane;
    private JButton btnCancel;
	private String userEmail;
	
    //constructor
	public InboxGUI(String userEmail) {
        //load emails in database
		this.userEmail = userEmail;
		emailDatabase = new EmailDatabase(userEmail);
        emailDatabase.loadUserEmails(userEmail);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblInbox = new JLabel("Inbox");
		lblInbox.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		lblInbox.setBounds(172, 20, 92, 36);
		contentPane.add(lblInbox);

		List<Email> inbox = emailDatabase.getInbox(userEmail);
		System.out.println("Displaying " + inbox.size() + " emails in inbox.");
		
        //display email
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Email email : inbox) {
        	listModel.addElement("From: " + email.getSender() + " - Subject: " + email.getSubject());
        }
        listEmails = new JList<>(listModel);

        JScrollPane scrollPane = new JScrollPane(listEmails);
        scrollPane.setBounds(10, 70, 420, 180);
        contentPane.add(scrollPane);
      
        //open email when user double-clicks
        listEmails.addMouseListener(new MouseAdapter() {
        	@Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = listEmails.getSelectedIndex();
                    if (index >= 0) {
                        Email email = inbox.get(index);
                        new EmailViewerGUI(email, null, userEmail).setVisible(true);
                    }
                }
            }
        });		
		
		JButton btnCancel = new JButton("X");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(406, 0, 44, 26);
		contentPane.add(btnCancel);
	}
}