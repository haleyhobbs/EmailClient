import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmailDatabase {

    private List<Email> emails;
    private FileHandler fileHandler;
    private String loggedInUser;

    public EmailDatabase(String sender) {
        fileHandler = new FileHandler();
        emails = new ArrayList<>();
        loggedInUser = sender;
    }
}
