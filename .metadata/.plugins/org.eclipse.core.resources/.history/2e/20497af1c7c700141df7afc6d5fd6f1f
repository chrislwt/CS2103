import static org.junit.Assert.*;

import org.junit.Test;

public class TextBuddySortTest {

	@Test
	public void testExecuteCommand() {
		//to create a text file for testing
		TextBuddy.checkFileExist();

		//test sort
		assertEquals("\nadded to mytextfile.txt : \"software eng\"\n", TextBuddy.executeCommand("add software eng"));
		assertEquals("\nadded to mytextfile.txt : \"christina lee\"\n", TextBuddy.executeCommand("add christina lee"));

		TextBuddy.executeCommand("display");
		TextBuddy.executeCommand("sort");
		TextBuddy.executeCommand("display");

		assertEquals("\nadded to mytextfile.txt : \"github is interesting\"\n", TextBuddy.executeCommand("add github is interesting"));
		assertEquals("\nadded to mytextfile.txt : \"Try harder\"\n", TextBuddy.executeCommand("add Try harder"));

		TextBuddy.executeCommand("display");
		TextBuddy.executeCommand("sort");
		TextBuddy.executeCommand("display");

	}

}

