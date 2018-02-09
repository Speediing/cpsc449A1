import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Shell{
	public static int min_pen = -1;
	public static ArrayList<String> min_list = new ArrayList<String>();  
  //global variables holding penalties probably?
  
  public static void main(String[] args){
	  
	if (args.length != 2) { 
		
		System.exit(0);
		return;
	}
	 
    Parser ps = new Parser();
    ps.inputReader(args[0]);
    
    Node root = new Node();
    
    System.out.println("FINAL PENALTY HERE: "+min_pen);
    printlist(min_list);
    
    //FINAL STATEMENT HERE ASSUMING EVERYTHING PREVIOUSLY WORKED CORRECTLY
    //LOOK AT ME LOOK AT ME LOOK AT ME LOOK AT ME
    if(min_pen==-1) {
    	constructFileOutPut(7);
    }
    else {
    	String returnedstring = "Solution";
    	for(String element: min_list) {
    		returnedstring = returnedstring+" "+element;
    	}
    	returnedstring = returnedstring+"; Quality: "+min_pen;
    	System.out.println("this is being put into file: "+returnedstring);
    	constructFileOutPut(returnedstring);
    }
    
    //output min_pen and min_list to file here or whatever
  } 
    
  //Method just to help with printing out the node contents
  private static void printlist(ArrayList<String> input) {
	  System.out.print("FINAL LIST HERE: ");
      for (String element : input) {
          System.out.print(element + ",");
      }
      System.out.println("");
  }
  
	public static void constructFileOutPut(int messageNumber) {
		String messageToOutPut = messageAssigner(messageNumber);
		makeOutPutFile(args[1], messageToOutPut);
	}
	
	public static void constructFileOutPut(String validSolution) {
		makeOutPutFile(args[1], validSolution);
	}
	
	public static String messageAssigner(int messageNumber) {
		String messageToOutPut = null;
		
		switch (messageNumber) {
		
		case 1: messageToOutPut = "Error while parsing input file";
				break;
		case 2:	messageToOutPut = "partial assignment error";
				break;
		case 3: messageToOutPut = "invalid machine/task";
				break;
		case 4: messageToOutPut = "machine penalty error";
				break;
		case 5: messageToOutPut = "invalid task";
				break;
		case 6: messageToOutPut = "invalid penalty";
				break;
		case 7: messageToOutPut = "No valid solution possible!";
				break;
		}
		
		return messageToOutPut;
	}
	
	public static void makeOutPutFile(String outPutFileName, String messageToOutPut) {
		
		try {
		BufferedWriter out = new BufferedWriter(new FileWriter(outPutFileName));
		out.write(messageToOutPut);
		out.close();
		} catch (IOException e) {
			System.err.println("Error writing to File");
		} catch (NullPointerException e) {
			System.err.println("Error writing to File");
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("file was created");
		System.exit(0);
		return;
		
	}
	
}
