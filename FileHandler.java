
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private static final String EMAIL_DIR = "emails";
    private List<Email> emails;
    private boolean emailDeleted;
    private Path path;
    private Path userDir;
    private Path emailFile;
    private StringBuilder emailContent;
    private String line;
    private String sender;
    private String recipient;
    private String subject;

    public FileHandler() {
        path = Paths.get(EMAIL_DIR);
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
        userDir = Paths.get(EMAIL_DIR, userEmail);
        
        if (!Files.exists(userDir)) 
            Files.createDirectory(userDir);

        for (Email email : emails) {
            emailFile = userDir.resolve(email.getSubject() + ".txt"); // Using subject as filename
            try (BufferedWriter writer = Files.newBufferedWriter(emailFile)) {
                writer.write("From: " + email.getSender() + "\n");
                writer.write("To: " + email.getRecipient() + "\n");
                writer.write("Subject: " + email.getSubject() + "\n");
                writer.write(email.getEmailContent() + "\n");
            }
        }

    }

    public List<Email> readEmail(String userEmail) throws IOException {
        emails = new ArrayList<>();
        userDir = Paths.get(EMAIL_DIR, userEmail);
        if (Files.exists(userDir)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(userDir)) {
                for (Path entry : stream) {
                    if (Files.isRegularFile(entry)) {
                        try (BufferedReader reader = Files.newBufferedReader(entry)) {
                            sender = reader.readLine().split(": ")[1];
                            recipient = reader.readLine().split(": ")[1];
                            subject = reader.readLine().split(": ")[1];
                            emailContent = new StringBuilder();
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
        emails = emailDatabase.getInbox(userEmail);
        userDir = Paths.get(EMAIL_DIR, userEmail);
        emailDeleted = false;

        for (Email e : emails) {
            if (e.equals(email)) {
                emailFile = userDir.resolve(email.getSubject() + ".txt");
                emailDatabase.deleteEmail(email);
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
