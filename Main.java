public class main {
	public static void main(String[] args) {

		// not sure if we want this -> deletes emails at startup **
		File emailDir = new File("emails");
		deleteDirectory(emailDir);

		// initialize and show login frame upon running program
		LoginGUI loginFrame1 = new LoginGUI();
		loginFrame1.setVisible(true);
	}

	// **
	private static void deleteDirectory(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			if (files != null) {
				for (File f : files) {
					deleteDirectory(f);
				}
			}
		}
		file.delete();
	}
}
