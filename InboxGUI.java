
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
import java.io.IOException;

public class InboxGUI extends JFrame {
    private EmailDatabase emailDatabase;
    private JPanel contentPane;
    private JList<String> listEmails;
    private JLabel lblInbox;
    private JScrollPane scrollPane;
    private JButton btnDelete;
    private JButton btnCancel;
    private Email email;
    private List<Email> inbox;
    private DefaultListModel<String> listModel;
    private FileHandler fileHandler;
    private int index;

    public InboxGUI(String userEmail) throws IOException {
        //load emails in database
        emailDatabase = new EmailDatabase(userEmail);
        emailDatabase.loadUserEmails(userEmail);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        lblInbox = new JLabel("Inbox");
        lblInbox.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
        lblInbox.setBounds(172, 20, 92, 36);
        contentPane.add(lblInbox);

        inbox = emailDatabase.getInbox(userEmail);

        //display email
        listModel = new DefaultListModel<>();
        for (Email email : inbox)
            listModel.addElement("From: " + email.getSender() + " - Subject: " + email.getSubject());
        listEmails = new JList<>(listModel);

        scrollPane = new JScrollPane(listEmails);
        scrollPane.setBounds(6, 54, 434, 184);
        contentPane.add(scrollPane);

        //display email
        listEmails.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    index = listEmails.getSelectedIndex();
                    if (index >= 0) {
                        email = inbox.get(index);
                        new EmailViewerGUI(email, null, userEmail).setVisible(true);
                    }
                }
            }
        });

        btnDelete = new JButton("Delete");
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = listEmails.getSelectedIndex();
                if (index >= 0) {
                    email = inbox.get(index);
                    fileHandler = new FileHandler();
                    try {
                        //delete email from database
                        fileHandler.removeEmail(userEmail, email, emailDatabase);
                        inbox.remove(index);
                        listModel.remove(index);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        btnDelete.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
        btnDelete.setBounds(375, 240, 75, 26); // may need to adjust this
        contentPane.add(btnDelete);

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
