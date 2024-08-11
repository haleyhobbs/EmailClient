
//libraries
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import java.util.List;
import java.io.IOException;

public class SearchGUI extends JFrame {
    // fields
    private String userEmail;
    private FileHandler fileHandler;
    private JPanel contentPane;
    private JLabel lblSubjectSearch;
    private JTextField textFieldSubjectSearch;
    private JButton btnCancel;
    private JButton btnSearch;
    private DefaultListModel<String> listModel;
    private JScrollPane scrollPane;
    private JList<String> listEmails;

    public SearchGUI(String userEmail, FileHandler fileHandler) {
        this.userEmail = userEmail;
        this.fileHandler = fileHandler;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblSubjectSearch = new JLabel("Subject:");
        lblSubjectSearch.setBounds(18, 25, 61, 16);
        contentPane.add(lblSubjectSearch);

        textFieldSubjectSearch = new JTextField();
        textFieldSubjectSearch.setBounds(77, 20, 328, 26);
        contentPane.add(textFieldSubjectSearch);
        textFieldSubjectSearch.setColumns(10);

        btnCancel = new JButton("X");
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnCancel.setBounds(406, 0, 44, 26);
        contentPane.add(btnCancel);

        btnSearch = new JButton("Search");
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });
        btnSearch.setBounds(174, 42, 100, 26);
        contentPane.add(btnSearch);

        listModel = new DefaultListModel<>();

        scrollPane = new JScrollPane();
        scrollPane.setBounds(6, 70, 438, 196);
        contentPane.add(scrollPane);

        listEmails = new JList<>(listModel);
        listEmails.setBounds(48, 72, 416, 176);
        contentPane.add(listEmails);

        // display email when user double-clicks
        listEmails.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = listEmails.getSelectedIndex();
                    if (index >= 0) {
                        try {
                            List<Email> emails = fileHandler.readEmail(userEmail);
                            Email selectedEmail = emails.get(index);
                            new EmailViewerGUI(selectedEmail, fileHandler, userEmail).setVisible(true);
                        } catch (IOException ex) {
                            System.out.println("Error reading emails.");
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    private void performSearch() {
        String subject = textFieldSubjectSearch.getText().trim().toLowerCase();

        if (!subject.isEmpty()) {
            try {
                FileHandler fileHandler = new FileHandler();
                List<Email> emails = fileHandler.readEmail(userEmail);

                listModel.clear();

                // if matching email found, display
                for (Email email : emails) {
                    String emailSubject = email.getSubject().toLowerCase();
                    if (emailSubject.contains(subject))
                        listModel.addElement("From: " + email.getSender() + " - Subject: " + email.getSubject());
                }
                if (listModel.isEmpty())
                    listModel.addElement("No emails found with subject containing: " + subject);
            } catch (IOException e) {
                System.out.println("Error reading emails.");
                e.printStackTrace();
            }
        }
    }
}
