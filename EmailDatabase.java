import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;

public class EmailDatabase {

    private List<Email> emails;
    private FileHandler fileHandler;
    private String loggedInUser;

    public EmailDatabase(String sender) {
        fileHandler = new FileHandler();
        emails = new ArrayList<>();
        loggedInUser = sender;
    }

    public void loadUserEmails(String userEmail) {
        loggedInUser = userEmail;
        try {
            emails = fileHandler.readEmail(userEmail);

        } catch (IOException e) {
            emails = new ArrayList<>();
            e.printStackTrace();
        }
    }

    public void saveUserEmails() {
        if (loggedInUser == null) {
            throw new IllegalStateException("No user is logged in.");
        }
        try {
            fileHandler.saveEmail(loggedInUser, emails);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendEmail(Email email) {

        EmailDatabase recipientEmailDB = new EmailDatabase(email.getRecipient());
        recipientEmailDB.loadUserEmails(email.getRecipient());
        recipientEmailDB.emails.add(email);

        recipientEmailDB.saveUserEmails();

    }

    public List<Email> getInbox(String userEmail) {
        List<Email> inbox = new ArrayList<>();
        for (Email e : emails) {
            if (e.getRecipient().equals(userEmail)) {
                inbox.add(e);
            }
        }

        System.out.println("Inbox contains " + inbox.size() + " emails for user: " + loggedInUser);

        return inbox;
    }

    public void deleteEmail(String userEmail, Email emailDel, EmailDatabase emailDataBase) throws IOException {

        emails.remove(emailDel);

        fileHandler.removeEmail(userEmail, emailDel, emailDataBase);

        System.out.println("Deleting email: " + emailDel.getSubject());

        saveUserEmails();
    }
}
