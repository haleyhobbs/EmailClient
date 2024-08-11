//libraries
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    //fields
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

    //save information in email to database
    public void saveEmail(String userEmail, List<Email> emails) throws IOException {
        Path userDir = Paths.get(EMAIL_DIR, userEmail);
        
        if (!Files.exists(userDir)) 
            Files.createDirectory(userDir);

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

    public List<Email> readEmail(String userEmail) throws IOException {
        List<Email> emails = new ArrayList<>();
        Path userDir = Paths.get(EMAIL_DIR, userEmail);
        if (Files.exists(userDir)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(userDir)) {
                for (Path entry : stream) {
                    if (Files.isRegularFile(entry)) {
                        try (BufferedReader reader = Files.newBufferedReader(entry)) {
                            String sender = reader.readLine().split(": ")[1];
                            String recipient = reader.readLine().split(": ")[1];
                            String subject = reader.readLine().split(": ")[1];
                            StringBuilder emailContent = new StringBuilder();
                            String line;
                            while ((line = reader.readLine()) != null)
                                emailContent.append(line).append("\n");
                            emails.add(new Email(sender, recipient, subject, emailContent.toString()));
                        }
                    }
                }
            }
        }
        return emails;
    }

    //remove email from database
    public void removeEmail(String userEmail, Email email, EmailDatabase emailDatabase) throws IOException {
        List<Email> emails = emailDatabase.getInbox(userEmail);
        Path userDir = Paths.get(EMAIL_DIR, userEmail);
        boolean emailDeleted = false;

        for (Email e : emails) {
            if (e.equals(email)) {
                Path emailFile = userDir.resolve(email.getSubject() + ".txt");
                emailDatabase.deleteEmail(userEmail, email, emailDatabase);
                Files.delete(emailFile);
                emails.remove(e);
                emailDeleted = true;
                break;
            }
        }
        if (emailDeleted)
            emailDatabase.saveUserEmails();
    }
}
