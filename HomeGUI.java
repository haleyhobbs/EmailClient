//libraries
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class HomeGUI extends JFrame {
    //fields
    private JPanel contentPane;
    private JButton btnLoginSecondAcct;

    public HomeGUI(String userEmail) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        //if user wants to login to second account, start with new login gui
        JButton btnLoginSecondAcct = new JButton("Login to another account");
        btnLoginSecondAcct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LoginGUI loginFrame2 = new LoginGUI();
                loginFrame2.setVisible(true);
            }
        });
        btnLoginSecondAcct.setBounds(124, 168, 196, 29);
        contentPane.add(btnLoginSecondAcct);

        JLabel lblEmail = new JLabel("Email");
        lblEmail.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
        lblEmail.setBounds(183, 26, 86, 41);
        contentPane.add(lblEmail);

        JButton btnCompose = new JButton("Compose email");
        btnCompose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ComposeGUI composeFrame = new ComposeGUI(userEmail);
                composeFrame.setVisible(true);
            }
        });
        btnCompose.setBounds(124, 79, 196, 29);
        contentPane.add(btnCompose);

        JButton btnInbox = new JButton("View inbox");
        btnInbox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                InboxGUI inboxFrame;
                try {
                    inboxFrame = new InboxGUI(userEmail);
                    inboxFrame.setVisible(true);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnInbox.setBounds(124, 109, 196, 29);
        contentPane.add(btnInbox);

        JButton btnSearch = new JButton("Search");
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FileHandler fileHandler = new FileHandler();
                SearchGUI searchFrame = new SearchGUI(userEmail, fileHandler);
                searchFrame.setVisible(true);
            }
        });
        btnSearch.setBounds(124, 138, 196, 29);
        contentPane.add(btnSearch);

        JButton btnLogout = new JButton("Logout");
        btnLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnLogout.setBounds(124, 196, 196, 29);
        contentPane.add(btnLogout);

        JLabel lblAcctName = new JLabel("");
        lblAcctName.setBounds(383, 6, 61, 16);
        contentPane.add(lblAcctName);
    }
}
