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
	private static final String MESSAGE_SEARCHED = "Search results for \"%1$s\" :";
	private static final String MESSAGE_EMPTYFILE = "\n%1$s is empty\n";
	private static final String MESSAGE_ERROR = "Unrecogonized command type";
	private static final String MESSAGE_INVALIDLINE = "Invalid line number.";
	private static final String PATTERN_COMMANDS_TYPE1 = "add|delete|search";
	private static final String PATTERN_COMMANDS_TYPE2 = "clear|sort|exit|display";
	private static final String MESSAGE_NEWLINE = "\n";

	/**
	 * These variables are declared for the whole class so any methods can access
	 * 
	 */
	private static String fileName = "mytextfile.txt";
	private static String content = null;
	private static Scanner sc = null;
	private static Scanner scanner = new Scanner(System.in);
	private static boolean isFileExist = false;
	private static Vector<String> dataFromFile = new Vector<String>();

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
			//	showToUser(MESSAGE_NEWLINE);
			showToUser("\ncommand: ");
			String command = scanner.nextLine();
			String userCommand = command;
			String feedback = executeCommand(userCommand);
			showToUser(feedback);
		}	
	}

	private static void showToUser(String text) {
		System.out.print(text);
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
			isFileExist = false;
			clearFile(isFileExist);
		}

		return String.format(MESSAGE_WELCOME,fileName);
	}

	public static String executeCommand(String userCommand) {
		userCommand = userCommand.trim();
		String userText = "";

		if(userCommand.matches(PATTERN_COMMANDS_TYPE1)) {
			showToUser(MESSAGE_NEWLINE);
			showToUser("Content cannot be empty!");
			return MESSAGE_NEWLINE;
		} else if(!(userCommand.matches(PATTERN_COMMANDS_TYPE2))) {
			userText = userCommand.substring(userCommand.indexOf(' ')+1);
			userCommand = userCommand.substring(0, userCommand.indexOf(' '));
		} else {
			userText = MESSAGE_NEWLINE;
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
		if(commandTypeString.equals(null)) {
			throw new Error(MESSAGE_ERROR);
		}
		else {
			commandTypeString = commandTypeString.toLowerCase();
		}

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

	private static Vector<String> readDataFromFile() {
		String content = null;
		Vector<String> dataFromFile = new Vector<String>();
		dataFromFile.clear();

		try {
			sc = new Scanner(new FileReader(fileName));
			while(sc.hasNextLine()) {
				content = sc.nextLine();
				content = trimText(content);
				dataFromFile.add(content);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			if(sc!= null) {
				sc.close();
			}
		}

		return dataFromFile;
	}

	/**
	 * This operation count the number of  lines in the text
	 * 
	 * @return count
	 */

	private static int getCount(){
		int count = 0;

		try {
			sc = new Scanner(new FileReader(fileName));
			while(sc.hasNextLine()) {
				count++;
				sc.nextLine();		
			}
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return count;
	}

	private static String clearFile(boolean isFile) {
		PrintWriter pw = null;

		try {
			pw = new PrintWriter(fileName);

		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			if(pw != null) {
				pw.close();
			}
		}

		if(isFile) {
			return String.format(MESSAGE_CLEARED, fileName);
		}

		return null;
	}

	private static void displayFile() {
		dataFromFile.clear();

		dataFromFile = readDataFromFile();

		for(int index = 0; index < dataFromFile.size(); index++) {
			showToUser(MESSAGE_NEWLINE);
			showToUser(index+1 +". "+dataFromFile.elementAt(index));
			showToUser(MESSAGE_NEWLINE);
		}

		if(dataFromFile.isEmpty()) {
			showToUser(String.format(MESSAGE_EMPTYFILE, fileName));
		}		

	} 

	private static String deleteText(String userText) {
		int line = Integer.parseInt(userText) - 1;
		dataFromFile.clear();

		if(line >= getCount()) {
			showToUser(MESSAGE_NEWLINE);
			showToUser(MESSAGE_INVALIDLINE);
			return "";
		}

		dataFromFile = readDataFromFile();

		clearFile(true);

		for(int index=0; index < dataFromFile.size(); index++) {
			if(index != line) {
				addText(dataFromFile.elementAt(index));
			}
			else {
				content = dataFromFile.elementAt(index);
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

		} catch(IOException e) {
			e.printStackTrace();
		} finally {
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
		dataFromFile.clear();
		dataFromFile = readDataFromFile();

		if(dataFromFile.isEmpty()) {
			return String.format(MESSAGE_EMPTYFILE, fileName);
		}

		else {
			clearFile(true);

			Collections.sort(dataFromFile, String.CASE_INSENSITIVE_ORDER);

			for(int index = 0; index < dataFromFile.size(); index++) {
				addText(dataFromFile.elementAt(index));
			}

			return String.format(MESSAGE_SORTED,fileName);
		}
	}

	private static void searchFile(String userText) {
		boolean isFound = false;
		dataFromFile.clear();
		dataFromFile = readDataFromFile();

		if(!dataFromFile.isEmpty()) {
			showToUser(MESSAGE_NEWLINE);
			showToUser(String.format(MESSAGE_SEARCHED, userText));
			showToUser(MESSAGE_NEWLINE);

			for(int index = 0; index < dataFromFile.size(); index++) {
				if(dataFromFile.elementAt(index).contains(userText)) {
					isFound = true;
					showToUser(MESSAGE_NEWLINE);
					showToUser(index+1+". "+dataFromFile.elementAt(index));
					showToUser(MESSAGE_NEWLINE);
				}
			}

		} else {
			showToUser(String.format(MESSAGE_EMPTYFILE, fileName));
		}

		if(!isFound && !dataFromFile.isEmpty()) {
			showToUser(MESSAGE_NEWLINE);
			showToUser("No results found.");
			showToUser(MESSAGE_NEWLINE);
		}
	}
}
