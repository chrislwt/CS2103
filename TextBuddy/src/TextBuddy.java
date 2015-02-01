import java.io.IOException;
import java.util.Scanner;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.File;
import java.util.*;

/**
 * This class is used to store and retrieve text from a text file entered by the user.
 * User can add, delete and clear contents through commands. User can choose to work on an
 * existing file or create a new one. The file is saved after every command user entered.


 * @author Christina Lee Wei Ting
 */

public class TextBuddy {
	
	private static final String WELCOME_MESSAGE = "\nWelcome to TextBuddy. %1$s is ready for use\n";
	private static final String MESSAGE_ADDED = "\nadded to %1$s : \"%2$s\"\n";
	private static final String MESSAGE_DELETED = "\ndeleted from %1$s : \"%2$s\"\n";
	private static final String MESSAGE_CLEARED = "\nall content deleted from %1s\n";
	
	// These are the possible command types
	enum COMMAND_TYPE {
		ADD_TEXT, DELETE_TEXT, DISPLAY_TEXT, CLEAR_TEXT, INVALID, EXIT
	};
	
	/*
	 * This variable is declared for the whole class (instead of declaring it
	 * inside the readUserCommand() method to facilitate automated testing using
	 * the I/O redirection technique. If not, only the first line of the input
	 * text file will be processed.
	 */
	private static Scanner scanner = new Scanner(System.in);
	/*
	 * This variable is declared for the whole class so any methods can access
	 * to the file status any time needed.This variable is used to determine
	 * if the file is currently meant for CLEAR or DELETE command
	 */
	private static boolean fileStatus = false;
	
	public static void main(String[] args) {
	
			String fileName = args[0];
			showToUser(checkFileExist(fileName));

			while (true) {
				System.out.print("command: ");
				String command = scanner.nextLine();
				String userCommand = command;
				String feedback = executeCommand(userCommand, fileName);
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
	 * @param fileName
	 *            is the full string user has entered as the argument
	 * @return the welcome message
	 */
	private static String checkFileExist(String fileName) {
		File textFile = new File(fileName);
		
		if(!(textFile.isFile())){
			fileStatus = false;
			clearFile(fileName, fileStatus);
		}
		
		return String.format(WELCOME_MESSAGE,fileName);
	}
	
	private static String executeCommand(String userCommand, String fileName) {

		userCommand = userCommand.trim();
		String userText = "";

		if(!(userCommand.toLowerCase().equals("clear") || userCommand.toLowerCase().equals("display") || userCommand.toLowerCase().equals("exit"))) {
			userText = userCommand.substring(userCommand.indexOf(' ')+1);
			userCommand = userCommand.substring(0, userCommand.indexOf(' '));
		}
		else
			userText = "";
		
		COMMAND_TYPE commandType = determineCommandType(userCommand);
		
		switch (commandType) {
		case ADD_TEXT:	
				return addText(fileName,userText);	
		case DELETE_TEXT:
				return deleteText(fileName,userText);
		case DISPLAY_TEXT: 
				displayFile(fileName);
				return "";
		case CLEAR_TEXT: 
			return clearFile(fileName, true);	
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
		case "exit":
			return COMMAND_TYPE.EXIT;
		default:
			return COMMAND_TYPE.INVALID;
		}
			
	}
	
	private static String clearFile(String fileName, boolean fileStatus) {
		PrintWriter pw = null;
		
		try {
			pw = new PrintWriter(fileName);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		finally {
			if(pw != null)
				pw.close();
		}
		
		if(fileStatus)
			return String.format(MESSAGE_CLEARED, fileName);
		
		return null;
	}
	
	private static void displayFile(String fileName) {
		Scanner sc = null;
		boolean empty = true;
		
		try {
			sc = new Scanner(new FileReader(fileName));
			while(sc.hasNextLine()){
				System.out.println("");
				System.out.println(sc.nextLine());
				empty = false;
			}
			
			if(empty) {
				System.out.println("");
				System.out.println(fileName+" is empty");
			}		
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		finally {
			if(sc!= null)
				sc.close();
		}
	} 
	
	private static String deleteText(String fileName, String userText) {
		int line = Integer.parseInt(userText) - 1;
		Scanner sc = null;
		String content = "";
		String trimmedContent = "";
		Vector<String> data = new Vector<String>();
		
		try {
			sc = new Scanner(new FileReader(fileName));
			
			while(sc.hasNextLine()){
				content = sc.nextLine();
				trimmedContent = content.substring(content.indexOf(' ')+1);
				data.addElement(trimmedContent);
			}
			
			clearFile(fileName,true);
			
			for(int index=0; index < data.size(); index++){
				if(index != line)
					addText(fileName,data.elementAt(index));
				else
					content = data.elementAt(index);
			}
	
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		finally {
			if(sc!= null)
				sc.close();
		}
		
		return String.format(MESSAGE_DELETED, fileName, content);
	}
	
	private static String addText(String fileName, String userText) {
		PrintWriter editedFile = null;
		Scanner sc = null;
		int line = 0;
		
		try {
			sc = new Scanner(new FileReader(fileName));

			while(sc.hasNextLine()){
				line++;
				sc.nextLine();
			}
				
			editedFile = new PrintWriter(new FileWriter(fileName, true));
			editedFile.println((line+1)+". "+userText);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		finally {
			if(sc!= null)
				sc.close();
			if(editedFile != null)
				editedFile.close();
		}
		
		return String.format(MESSAGE_ADDED, fileName, userText);		
	}
	
	
}
