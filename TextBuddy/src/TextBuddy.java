public class TextBuddy {
	
	

	public static void main(String[] args) {
		
		if(args.length == 0){	
			System.out.println("Please enter a filename.");
			return;
		}
		else{
			String fileName = args[1];
			System.out.println("Welcome to TextBuddy. "+fileName+" is ready for use");
		}	
		
	}
	
	

}
