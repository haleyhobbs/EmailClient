
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class EmailViewerGUI extends JFrame {
    private JPanel contentPane;
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JButton btnReply;
    private JButton btnClose;
    private ComposeGUI composeGUI;

    public EmailViewerGUI(Email email, FileHandler fileHandler, String userEmail) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setText("From: " + email.getSender() + "\n"
                + "To: " + email.getRecipient() + "\n"
                + "Subject: " + email.getSubject() + "\n"
                + "Message: " + email.getEmailContent());

        scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(5, 5, 440, 233);
        contentPane.add(scrollPane);

        btnReply = new JButton("Reply");
        btnReply.setBounds(10, 240, 100, 30);
        btnReply.addActionListener(e -> {
            //automatically open the new ComposeGUI with the sender's email as the recipient
            composeGUI = new ComposeGUI(userEmail, email.getSender());
            composeGUI.setVisible(true);
            dispose();
        });
        contentPane.add(btnReply);

        btnClose = new JButton("Close");
        btnClose.setBounds(290, 237, 100, 30);
        btnClose.addActionListener(e -> dispose());
        contentPane.add(btnClose);

        setTitle("View Email");
    }
}
