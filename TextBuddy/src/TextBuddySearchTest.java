import org.junit.Test;


public class TextBuddySearchTest {

	@Test
	public void testExecuteCommand() {
		//to create a text file for testing
		TextBuddy.checkFileExist();

		TextBuddy.executeCommand("search software");
		TextBuddy.executeCommand("search gi");
		TextBuddy.executeCommand("search abc");
	}

}
