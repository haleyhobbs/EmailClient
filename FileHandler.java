import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    private static final String EMAIL_DIR = "emails";

    public FileHandler() {

        Path path = Paths.get(EMAIL_DIR);
        if (!Files.exists(path)) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }

    public void saveEmail(String userEmail, List<Email> emails) throws IOException {

        Path userDir = Paths.get(EMAIL_DIR, userEmail);
        if (!Files.exists(userDir)) {
            Files.createDirectory(userDir);
        }

        for (Email email : emails) {
            Path emailFile = userDir.resolve(email.getSubject() + ".txt"); // Using subject as filename

            try (BufferedWriter writer = Files.newBufferedWriter(emailFile)) {
                writer.write("From: " + email.getSender() + "\n");
                writer.write("To: " + email.getRecipient() + "\n");
                writer.write("Subject: " + email.getSubject() + "\n");
                writer.write(email.getEmailContent() + "\n");
            }
        }
    }

}
