import java.util.*;
import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class Parser{
	int[] MParray;
	public static HashMap<Integer, String> fpa = new HashMap<Integer, String>();
	public static ArrayList<FMelement> fpArray = new ArrayList<FMelement>();
	public static ArrayList<TNTelement> tntArray = new ArrayList<TNTelement>();
	//public static ArrayList<FPArray> fpArray = new ArrayList<FPArray>();
	public  ArrayList<TNPelement> tnpArray = new ArrayList<TNPelement>();
  //public  ArrayList<String> fileLines = new ArrayList<String>();




	public void inputReader(String fileName) {

		BufferedReader br = null;
		String line = "";
		int constraintCounter = 0;
		FMelement fmElement = null;
		TNTelement tntElement = null;
		TNPelement tnpElement = null;


		try {
			FileReader fr = new FileReader(fileName);

			br = new BufferedReader(fr);

			while ((line = br.readLine()) != null){

				Matcher emptyCheck = Pattern.compile("[\\s]*").matcher(line);
				if (emptyCheck.matches()) continue;




				if (line.matches("Name:[\\s]*")){
					constraintCounter++;
				}//else{System.out.println("this doesnt exist");}
				//TODO forced partial assignment, forbidden assingment done using hashmaps


				if (line.matches("forced partial assignment:[\\s]*")){
					constraintCounter++;
					line = readForcedPartialAssignmet(br, line);

				}

				if (line.matches("forbidden machine:[\\s]*")){
					constraintCounter++;
					line = readForbiddenMachine(br, fmElement, line);


				}
				//TODO too near tasks will be done using class arary list
				if (line.matches("too-near tasks:[\\s]*")){
					constraintCounter++;
					line = readToNearTask(br, tntElement, line);
				}

				//TODO machine penalties will be stored as a regular old 2d array
				if (line.matches("machine penalties:[\\s]*")){
					constraintCounter++;
				}
				//TODO too-near penalties will be done with a custom array list using classses
				if (line.matches("too-near penalities[\\s]*")){
					constraintCounter++;
					line = readToNearPenalities(br, tnpElement, line);
				}

			}
			/*if (constraintCounter != 6)
      		{
      		//TODO display errors
    		}else{
    		//methods that do things
  			}*/

			System.out.println(constraintCounter);
		}
		catch(FileNotFoundException ex)
		{
			//TODO check if this is the right output that i want in the case that they  put in the wrong file name

			System.out.println("File not found");
			System.exit(0);
			return;
		}
		catch(IOException e)
		{
			System.out.println("Error while parsing the input file");
			System.exit(0);
			return;
		}
	}

	

	private String readToNearPenalities(BufferedReader br,  TNPelement tnpElement, String line){
		int i = 0;
		try{
			
			while ((line = br.readLine()) !=null){

				Matcher emptyCheck = Pattern.compile("[\\s]*").matcher(line);
				if (emptyCheck.matches()) continue;


				Matcher matcher = Pattern.compile(("\\(([A-H]),([A-H]),([0-9]*)\\)")).matcher(line);

				if (matcher.matches()){
					tnpElement = new TNPelement(matcher.group(1), matcher.group(2), Long.parseLong(matcher.group(3)));
					tnpArray.add(tnpElement);
					//  if (!fpa.containsKey(matcher.group(1)) && !fpa.containsValue(matcher.group(2)))
					//  {
					//  fpa.put(Integer.parseInt(matcher.group(1)), matcher.group(2));
					System.out.println(tnpArray.get(i).getTNPtaskOne() + " " + tnpArray.get(i).getTNPtaskTwo() + " " + tnpArray.get(i).getTNPpenalty()); 

					i++;
				}else{
					//TODO partial assignment
					System.out.println(line);
					System.out.println("parsing error");

					System.exit(0);

					return null;

				}

			}


		}catch(IOException e){

		}
		return line;
	}


	private String readToNearTask(BufferedReader br,  TNTelement tntElement, String line){
		int i = 0;
		try{
			String exitString = "machine penalties:";
			while (!(line = br.readLine()).equals(exitString)){

				Matcher emptyCheck = Pattern.compile("[\\s]*").matcher(line);
				if (emptyCheck.matches()) continue;


				Matcher matcher = Pattern.compile(("\\(([A-H]),([A-H])\\)")).matcher(line);

				if (matcher.matches()){

					tntElement = new TNTelement(matcher.group(1), matcher.group(2));
					tntArray.add(tntElement);
					//  if (!fpa.containsKey(matcher.group(1)) && !fpa.containsValue(matcher.group(2)))
					//  {
					//  fpa.put(Integer.parseInt(matcher.group(1)), matcher.group(2));
					System.out.println(tntArray.get(i).getTNTtaskOne() + " " + tntArray.get(i).getTNTtaskTwo()); 

					i++;
				}else{
					//TODO partial assignment
					System.out.println(line);
					System.out.println("parsing error");

					System.exit(0);

					return null;

				}

			}


		}catch(IOException e){

		}
		return line;
	}


	
	
	
	
	private String readMachinePenalties(BufferedReader br, String line) {
		try {
			String exitString = "too-near penalities";
			while (!(line = br.readLine()).equals(exitString)) {
				
				Matcher emptyCheck = Pattern.compile("[\\s]*").matcher(line);
				if (emptyCheck.matches()) continue; 
				
				Matcher matcher = Pattern.compile(("\\(([0-9]*)[\\s]([0-9]*)[\\s]([0-9]*)[\\s]([0-9]*)[\\s]([0-9]*)[\\s]([0-9]*)[\\s]([0-9]*)[\\s]([0-9]*)[\\s]*")).matcher(line);
				
			}
			 } catch(IOException e) {}
		return line;
	}
	
	
	
	
	private String readForbiddenMachine(BufferedReader br, FMelement fmElement, String line){
		int i = 0;
		try{
			String exitString = "too-near tasks:";
			while (!(line = br.readLine()).equals(exitString)){

				Matcher emptyCheck = Pattern.compile("[\\s]*").matcher(line);
				if (emptyCheck.matches()) continue;

				Matcher matcher = Pattern.compile(("\\(([1-8]),([A-H])\\)[\\s]*")).matcher(line);

				if (matcher.matches()){

					fmElement = new FMelement(Integer.parseInt(matcher.group(1)), matcher.group(2));
					fpArray.add(fmElement);
					System.out.println(fpArray.get(i).getFMachine() + " " + fpArray.get(i).getFMtask());
					i++;
				}else{
					//TODO partial assignment
					System.out.println(line);
					System.out.println("partial error");
					System.exit(0);
					return null;
				}
				while(line.isEmpty()){line = br.readLine();}
			}
		}catch(IOException e){
		}
		return line;
	}

	private String readForcedPartialAssignmet(BufferedReader br, String line){

		try{
			String exitString = "forbidden machine:";
			while ( !(line = br.readLine()).equals(exitString) ){
				
				//next = brNext.readLine();
				//System.out.println(next);
				//br.mark();
				//if (next.equals(exitString)) {return;}
				//line = br.readLine();

				//if(line.equals(exitString)){
				//br.reset();
				//  return line;}
				
				Matcher emptyCheck = Pattern.compile("[\\s]*").matcher(line);
				if (emptyCheck.matches()) continue;

				Matcher matcher = Pattern.compile(("\\(([1-8]),([A-H])\\)")).matcher(line);
				if (matcher.matches()){
					if (!fpa.containsKey(matcher.group(1)) && !fpa.containsValue(matcher.group(2)))
					{
						fpa.put(Integer.parseInt(matcher.group(1)), matcher.group(2));
						System.out.println(Arrays.asList(fpa));
					} else {
						System.out.println("error assignment");
						System.exit(0);
						return null;
					}
				}else{
					//TODO partial assignment
					System.out.println(line);
					System.out.println("partial error");
					System.exit(0);
					return null;
				}
			}
		}  catch(IOException e)
		{
			System.out.println("Error while parsing the input file");
			System.exit(0);
			return null;
		}
		return line;
	}

	//TODO make buffered reader method
	//
	//read through the file
	//parse through each line and create each object

}

//TODO create getters for each array list DONE-----
//will be able to return objects from the list
//TODO need to create methods to store the constraints in the lists
//TODO need to check if everything is correct from the inpkut file
//if anything is wrong need to display error
//TODO make sure there is only up to 8 pairs of fpa
//TODO check if there are doubles
