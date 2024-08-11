//libraries
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmailDatabase {
    //fields
    private List<Email> emails;
    private FileHandler fileHandler;
    private String loggedInUser;

    public EmailDatabase(String sender) throws IOException {
        fileHandler = new FileHandler();
        emails = new ArrayList<>();
        loggedInUser = sender;
    }

    public void loadUserEmails(String userEmail) throws IOException {
        loggedInUser = userEmail;
        try {
            emails = fileHandler.readEmail(userEmail);
        } catch (IOException e) {
            emails = new ArrayList<>();
            e.printStackTrace();
        }
    }

    public void saveUserEmails() throws IOException {
        if (loggedInUser == null)
            throw new IllegalStateException("No user is logged in.");
        try {
            fileHandler.saveEmail(loggedInUser, emails);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendEmail(Email email) throws IOException {
        EmailDatabase recipientEmailDB = new EmailDatabase(email.getRecipient());
        recipientEmailDB.loadUserEmails(email.getRecipient());
        recipientEmailDB.emails.add(email);
        recipientEmailDB.saveUserEmails();
    }

    public List<Email> getInbox(String userEmail) throws IOException {
        List<Email> inbox = new ArrayList<>();
        for (Email e : emails) {
            if (e.getRecipient().equals(userEmail))
                inbox.add(e);
        }
        return inbox;
    }

    public void deleteEmail(String userEmail, Email emailDel, EmailDatabase emailDataBase) throws IOException {
        emails.remove(emailDel);
        saveUserEmails();
    }
}
