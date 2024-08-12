
import java.io.File;

public class Main {
	private static File emailDir;
	private static File[] files;
	public static LoginGUI loginFrame1;
	
	public static void main(String[] args) {
		//delete emails at startup
		emailDir = new File("emails");
		deleteDirectory(emailDir);

		//initialize and show login frame upon running program
		loginFrame1 = new LoginGUI();
		loginFrame1.setVisible(true);
	}

	//delete emails at startup
	private static void deleteDirectory(File file) {
		if (file.isDirectory()) {
			files = file.listFiles();
			if (files != null) {
				for (File f : files)
					deleteDirectory(f);
			}
		}
		file.delete();
	}
}
