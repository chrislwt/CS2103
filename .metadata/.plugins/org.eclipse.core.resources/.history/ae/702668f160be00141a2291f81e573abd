import static org.junit.Assert.*;

import org.junit.Test;



public class TextBuddyTest {
	
	
	@Test
	public void testExcecuteCommand() {
		//to create a text file for testing
		TextBuddy.checkFileExist();

		//test sort
		assertEquals("\nadded to mytextfile.txt : \"software eng\"\n", TextBuddy.executeCommand("add software eng"));
		assertEquals("\nadded to mytextfile.txt : \"christina lee\"\n", TextBuddy.executeCommand("add christina lee"));

		TextBuddy.executeCommand("display");
		TextBuddy.executeCommand("sort");
		TextBuddy.executeCommand("display");
		
		//test search
		TextBuddy.executeCommand("search abc");

	}
	


}
