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

            System.out.println("Loaded " + emails.size() + " emails for user: " + loggedInUser);

        } catch (IOException e) {
            emails = new ArrayList<>();
            e.printStackTrace();
        }
    }
}
