public class Email {
    private String sender;
    private String subject;
    private String recipient;
    private String emailContent;

    public Email(String sender, String recipient, String subject, String emailContent) {
        this.sender = sender;
        this.subject = subject;
        this.recipient = recipient;
        this.emailContent = emailContent;
    }

    public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getEmailContent() {
		return emailContent;
	}
	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}
}
