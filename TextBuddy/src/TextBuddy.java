import java.util.Scanner;
import java.util.Vector;
import java.util.Collections;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.File;



/**
 * This class is used to store and retrieve text from a text file entered by the user.
 * User can add, delete, clear, sort and search contents through commands. User can choose to work on an
 * existing file or create a new one. The file is saved after every command user entered.


 * @author Christina Lee Wei Ting A0098824N
 */

public class TextBuddy {

	private static final String MESSAGE_WELCOME = "\nWelcome to TextBuddy. %1$s is ready for use\n";
	private static final String MESSAGE_ADDED = "\nadded to %1$s : \"%2$s\"\n";
	private static final String MESSAGE_DELETED = "\ndeleted from %1$s : \"%2$s\"\n";
	private static final String MESSAGE_CLEARED = "\nall content deleted from %1s\n";
	private static final String MESSAGE_SORTED = "\n%1$s is sorted\n";

	/**
	 * This variable is declared for the whole class so any methods can access
	 * to the file status any time needed.This variable is used to determine
	 */
	private static String fileName = "mytextfile.txt";
	private static Scanner sc = null;
	private static String content = null;
	private static String trimmedContent = null;
	private static boolean fileStatus = false;
	private static Scanner scanner = new Scanner(System.in);
	
	// These are the possible command types
	enum COMMAND_TYPE {
		ADD_TEXT, DELETE_TEXT, DISPLAY_TEXT, CLEAR_TEXT, SORT_TEXT, SEARCH_TEXT, INVALID, EXIT
	};


	public static void main(String[] args) {

		//if user did not input any filename, the default one will be assumed.
		if(args.length != 0) {
			fileName = args[0];
		}

		showToUser(checkFileExist());	

		while (true) {
			System.out.print("command: ");
			String command = scanner.nextLine();
			String userCommand = command;
			String feedback = executeCommand(userCommand);
			showToUser(feedback);
		}	
	}

	private static void showToUser(String text) {
		System.out.println(text);
	}

	/**
	 * This operation is used to check if text file exist
	 * if yes, it will append else it will create the file
	 * 
	 * @param fileName is the full string user has entered as the argument
	 * @return the welcome message
	 */
	public static String checkFileExist() {
		File textFile = new File(fileName);

		if(!(textFile.isFile())) {
			fileStatus = false;
			clearFile(fileStatus);
		}

		return String.format(MESSAGE_WELCOME,fileName);
	}

	public static String executeCommand(String userCommand) {

		userCommand = userCommand.trim();
		String userText = "";

		if(!(userCommand.toLowerCase().equals("sort") || userCommand.toLowerCase().equals("clear") || 
				userCommand.toLowerCase().equals("display") || userCommand.toLowerCase().equals("exit"))) {

			userText = userCommand.substring(userCommand.indexOf(' ')+1);
			userCommand = userCommand.substring(0, userCommand.indexOf(' '));
		}
		else {
			userText = "";
		}

		COMMAND_TYPE commandType = determineCommandType(userCommand);

		switch (commandType) {
		case ADD_TEXT:	
			return addText(userText);	
		case DELETE_TEXT:
			return deleteText(userText);
		case DISPLAY_TEXT: 
			displayFile();
			return "";
		case CLEAR_TEXT: 
			return clearFile(true);	
		case SORT_TEXT:
			return sortFile();
		case SEARCH_TEXT:
			searchFile(userText);
			return "";
		case EXIT:
			System.exit(0);	
		default:
			//throw an error if the command is not recognized
			throw new Error("Unrecognized command type");
		}

	}

	/**
	 * This operation determines which of the supported command types the user
	 * wants to perform
	 * 
	 * @param commandTypeString
	 *            is the first word of the user command
	 */
	private static COMMAND_TYPE determineCommandType(String commandTypeString) {
		if(commandTypeString.equals(null))
			throw new Error("Unregonized command type");
		else
			commandTypeString = commandTypeString.toLowerCase();

		switch(commandTypeString) {
		case "add":
			return COMMAND_TYPE.ADD_TEXT;
		case "delete":
			return COMMAND_TYPE.DELETE_TEXT;
		case "display":
			return COMMAND_TYPE.DISPLAY_TEXT;
		case "clear":
			return COMMAND_TYPE.CLEAR_TEXT;
		case "sort":
			return COMMAND_TYPE.SORT_TEXT;
		case "search":
			return COMMAND_TYPE.SEARCH_TEXT;
		case "exit":
			return COMMAND_TYPE.EXIT;
		default:
			return COMMAND_TYPE.INVALID;
		}

	}

	/**
	 * This operation remove the line numbers in the text
	 * 
	 * @param originalText
	 *            original text found in the textfile
	 */
	private static String trimText(String originalText) {

		return originalText.substring(originalText.indexOf(' ')+1);
	}

	private static int getCount(){
		
		try {
			sc = new Scanner(new FileReader(fileName));
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		int count = 0;
		while(sc.hasNextLine()) {
			count++;
			sc.nextLine();		
		}
		
		return count;
	}
	
	private static String clearFile(boolean fileStatus) {
		PrintWriter pw = null;

		try {
			pw = new PrintWriter(fileName);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		finally {
			if(pw != null) {
				pw.close();
			}
		}

		if(fileStatus) {
			return String.format(MESSAGE_CLEARED, fileName);
		}

		return null;
	}

	private static void displayFile() {
		boolean empty = true;

		try {
			sc = new Scanner(new FileReader(fileName));
			while(sc.hasNextLine()) {
				showToUser("");
				showToUser(sc.nextLine());
				empty = false;
			}

			if(empty) {
				showToUser("");
				showToUser(fileName+" is empty");
			}		
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		finally {
			if(sc!= null) {
				sc.close();
			}
		}
	} 

	private static String deleteText(String userText) {
		int line = Integer.parseInt(userText) - 1;
		Vector<String> data = new Vector<String>();
		
		if(line >= getCount()) {
			showToUser("");
			showToUser("Invalid line number.");
			return "";
		}
		
		try {
			sc = new Scanner(new FileReader(fileName));
			while(sc.hasNextLine()) {
				content = sc.nextLine();
				trimmedContent = trimText(content);
				data.addElement(trimmedContent);
			}

			clearFile(true);

			for(int index=0; index < data.size(); index++) {
				if(index != line) {
					addText(data.elementAt(index));
				}
				else {
					content = data.elementAt(index);
				}
			}
		}

		catch(IOException e) {
			e.printStackTrace();
		}

		finally {
			if(sc!= null) {
				sc.close();
			}
		}

		return String.format(MESSAGE_DELETED, fileName, content);
	}

	private static String addText(String userText) {
		PrintWriter editedFile = null;
		int line = 0;

		try {
			sc = new Scanner(new FileReader(fileName));

			while(sc.hasNextLine()) {
				line++;
				sc.nextLine();
			}

			editedFile = new PrintWriter(new FileWriter(fileName, true));
			editedFile.println((line+1)+". "+ userText);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		finally {
			if(sc!= null) {
				sc.close();
			}

			if(editedFile != null) {
				editedFile.close();
			}
		}

		return String.format(MESSAGE_ADDED, fileName, userText);		
	}

	private static String sortFile() {
		boolean empty = true;

		Vector<String> sortedData = new Vector<String>();

		try {
			sc = new Scanner(new FileReader(fileName));
			while(sc.hasNextLine()) {
				empty = false;
				content = sc.nextLine();
				trimmedContent = trimText(content);
				sortedData.addElement(trimmedContent);
			}

			if(empty) {
				showToUser("");
				showToUser(fileName+" is empty");
			}

			clearFile(true);
			Collections.sort(sortedData);

			for(int index = 0; index < sortedData.size(); index++) {
				addText(sortedData.elementAt(index));
			}
		}

		catch(IOException e) {
			e.printStackTrace();
		}

		finally {
			if(sc!= null) {
				sc.close();
			}
		}

		return String.format(MESSAGE_SORTED,fileName);
	}

	private static void searchFile(String userText) {
		boolean empty = true;
		boolean isFound = false;

		try {
			showToUser("Search results for '"+userText+"' :");
			sc = new Scanner(new FileReader(fileName));
			while(sc.hasNextLine()) {
				empty = false;
				content = sc.nextLine();
				if(content.contains(userText)) {
					showToUser("");
					showToUser(content);
					isFound = true;
				}
			}

			if(empty) {
				showToUser("");
				showToUser(fileName+" is empty");
			}

			if(!isFound && !empty) {
				showToUser("");
				showToUser("No results found.");
			}
		}

		catch(IOException e) {
			e.printStackTrace();
		}

		finally {
			if(sc!= null) {
				sc.close();
			}
		}

	}
}
