
//java imports
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser class: shell class calls inputReader(String fileName), passing in the
 * file name provided by the user via command line the parser goes through the
 * file line by line, when it passes over a constraint header the constraint
 * counter will tick if this counter does not = 6 at the end of the parsing the
 * parser will output an error message and terminate after it has incremented
 * the counter it will call the method that corresponds to the header and sorts
 * the information in the txt file into their respective ArrayLists, hashMap, or
 * 2d list. these are all public so anyone can access them however all but one
 * of the attributes are immutable. each method handles all the errors
 * associated with there constraints once it is done the parser will close
 */

public class Parser {
	// Machine penalty array
	public static int[][] MParray = new int[8][8];
	// Forrbidden partial assignment array
	public static HashMap<Integer, String> fpa = new HashMap<Integer, String>();
	// forbidden machine array THERE IS A TYPO HERE THAT NEEDS TO BE FIXED
	public static ArrayList<FMelement> fmArray = new ArrayList<FMelement>();
	// too near task array
	public static ArrayList<TNTelement> tntArray = new ArrayList<TNTelement>();
	// too near penalty array
	public static ArrayList<TNPelement> tnpArray = new ArrayList<TNPelement>();

	/**
	 * reads the file line by line continuously sorts information to there
	 * respective storage places
	 * 
	 * @param fileName
	 *            string containing the name of the file to be parsed
	 */
	public void inputReader(String fileName) {
		// make buffered reader
		BufferedReader br = null;
		// current line the parser is reading
		String line = "";
		// constraint counter which is used to count the headers
		int constraintCounter = 0;
		// make Forbidden machine element object
		FMelement fmElement = null;
		// make too near task element object
		TNTelement tntElement = null;
		// make to near penalty element object
		TNPelement tnpElement = null;
		
		
		// try catch
		try {
			boolean nameRead = false, fpaRead = false, fmRead = false, tntRead = false, mpRead = false, tnpRead = false;

			// open up the file
			FileReader fr = new FileReader(fileName);
			// make a new buffered reader in the file
			br = new BufferedReader(fr);
			// read till the file till nothing left
			while ((line = br.readLine()) != null) {

				// uses regular expressions to catch empty lines [\\s]* checks for an infinite
				// about of white space
				// creates a matcher object with a specific pattern that it looks for
				// this is how most of the error checking is done
				Matcher emptyCheck = Pattern.compile("[\\s]*").matcher(line);
				// if line empty skip
				if (emptyCheck.matches())
					continue;

				// looks for name header along with an infinite amount of white space that
				// follows
				// there can be no white space before though
				if (line.matches("Name:[\\s]*")) {
					if(!nameRead) {
					line = readName(br, line);
					constraintCounter++;
					nameRead = true;
					}else{break;}
					// TODO this should just check that there is a name for the file and that no
					// funny business is going on
					// line = readName(br, line);
				}

				if (line.matches("forced partial assignment:[\\s]*")) {
					if(nameRead){
						constraintCounter++;
					// passes the buffered reader(by reference thats why you don't need to return it
					// current line is also passed into the method so that it can continue where it
					// left off.
					// the methods return the current line so that it can continue checking for the
					// next constraint header
					fpaRead = true;
					line = readForcedPartialAssignmet(br, line);
					}else{break;}
				}

				if (line.matches("forbidden machine:[\\s]*")) {
					if (fpaRead) {
						constraintCounter++;
						// passing in corresponding object for the constraint
						line = readForbiddenMachine(br, fmElement, line);
						fmRead = true;
					}else {break;}

				}

				if (line.matches("too-near tasks:[\\s]*")) {
					if (fmRead) {
						constraintCounter++;
						line = readToNearTask(br, tntElement, line);
						tntRead = true;
					}else {break;}
				}

				if (line.matches("machine penalties:[\\s]*")) {
					if (tntRead) {
						constraintCounter++;
						line = readMachinePenalties(br, line);
						mpRead = true;
					} else {break;}
				}

				if (line.matches("too-near penalities[\\s]*")) {
					if (mpRead)
					constraintCounter++;
					line = readToNearPenalities(br, tnpElement, line);
					tnpRead = true;
				}else {break;}

			}
			// checks constraint counter to see if = 6 if not err.
			if (constraintCounter != 6) {

				Shell.constructFileOutPut(1);
			}
			System.out.println(constraintCounter);
			br.close();
		} catch (FileNotFoundException ex) {
			//SHOULD THIS BE ERROR WHILE PARSING
			System.out.println("File not found");
			System.exit(0);
			return;

		} catch (IOException e) {

			Shell.constructFileOutPut(1);
		}
	}

	private String readName(BufferedReader br, String line) {
		try {
			String lastLine = "";
			String exitString = "forced partial assignment:[\\s]*";
			int namesCounted = 0;
			while (!(line = br.readLine()).matches(exitString)) {
				//System.out.println(line);
				Matcher emptyCheck = Pattern.compile("[\\s]*").matcher(line);
				if (emptyCheck.matches()) {
					lastLine = line;
					continue;
				}
				
				Matcher name = Pattern.compile(("[\\S]+")).matcher(line);
				if (name.matches()) {
					namesCounted++;
				}
				
				lastLine = line;
			}
			if (!(lastLine.matches("([\\s]*)"))) {
				Shell.constructFileOutPut(1);
			}
			if (namesCounted != 1) {
				//System.out.println(namesCounted);
				Shell.constructFileOutPut(1);
			}
		} catch (IOException e) {
			Shell.constructFileOutPut(1);
		} catch (NullPointerException e) {
			Shell.constructFileOutPut(1);
		}

		return line;
	}
	
	
	/**
	 * reads the too near penalties, checking to see if any task is outside the
	 * specified range as well as any invalid input using reg. expression
	 * 
	 * @param br
	 *            reference to the buffered reader
	 * @param tnpElement
	 *            too near penalties
	 * @param line
	 *            current line
	 * @return
	 */
	private String readToNearPenalities(BufferedReader br, TNPelement tnpElement, String line) {

		try {

			while ((line = br.readLine()) != null) {
				// checks empty line
				Matcher emptyCheck = Pattern.compile("[\\s]*").matcher(line);

				if (emptyCheck.matches())
					continue;

				// creates matchers allowed and not allowed inputs
				// anything that disagrees with these gets tossed
				// (.*) reads any possible character its there cause we don't really care about
				// what's there for those error checks
				Matcher matcher = Pattern.compile(("\\(([A-H]),([A-H]),([0-9]+)\\)")).matcher(line);
				Matcher invalidTaskOne = Pattern.compile(("\\(([^A-H]),([A-H]),(.*)\\)")).matcher(line);
				Matcher invalidTaskTwo = Pattern.compile(("\\(([A-H]),([^A-H]),(.*)\\)")).matcher(line);
				Matcher invalidTaskAll = Pattern.compile(("\\(([^A-H]),([^A-H]),(.*)\\)")).matcher(line);
				Matcher invalidPenalty = Pattern.compile(("\\((.*),(.*),([0-9]+)\\)")).matcher(line);

				// taskone != A-H or tasktwo != A-H or task one and two != A-H
				if (invalidTaskOne.matches() || invalidTaskTwo.matches() || invalidTaskAll.matches()) {

					Shell.constructFileOutPut(5);
				}
				if (!(invalidPenalty.matches())) {
					Shell.constructFileOutPut(6);
				}
				// if the tnt follows the correct formatting then put it in the array
				if (matcher.matches()) {

					// TESTING
					// System.out.println(tnpArray.size());

					Boolean penaltyChanged = false;
					// the regex uses ([]) to assign its groups '\\' is an escape character
					// checks if the array is empty if it is it puts the first value it sees in
					if (tnpArray.size() == 0) {
						tnpElement = new TNPelement(matcher.group(1), matcher.group(2),
								Integer.parseInt(matcher.group(3)));
						tnpArray.add(tnpElement);

						/*
						 * TESTING // System.out.println(tnpArray.get(i).getTNPtaskOne() + " " + //
						 * tnpArray.get(i).getTNPtaskTwo() + " " // + tnpArray.get(i).getTNPpenalty());
						 * i++;
						 */

					} else {
						// this for loops checks if the arrangement of taskone, tasktwo already exists
						// in that order and if it does it just updates the
						// penalty via protected setter
						for (int j = 0; j < tnpArray.size() - 1; j++)

						{

							String taskOne = tnpArray.get(j).getTNPtaskOne();
							String taskTwo = tnpArray.get(j).getTNPtaskTwo();

							if (taskOne.equals(matcher.group(1)) && taskTwo.equals(matcher.group(2))) {

								tnpArray.get(j).setTNPpenalty(Integer.parseInt(matcher.group(3)));
								// if the value is updated then this gets turned and no new thing is added to
								// the array
								penaltyChanged = true;
								break;

								// System.out.println(tnpArray.get(j) + " element was changed");
							}

						}
						// checks if the penalty was updated
						if (penaltyChanged)
							continue;

						tnpElement = new TNPelement(matcher.group(1), matcher.group(2),
								Integer.parseInt(matcher.group(3)));
						tnpArray.add(tnpElement);

						/*
						 * TESTING // System.out.println(tnpArray.get(i).getTNPtaskOne() + " " + //
						 * tnpArray.get(i).getTNPtaskTwo() + " " // + tnpArray.get(i).getTNPpenalty());
						 * i++;
						 */
					}
					// if format is wrong exit
				} else {
					Shell.constructFileOutPut(1);
				}

			}

		} catch (IOException e) {
			Shell.constructFileOutPut(1);
		}
		// TESTING COMMENT THIS OuT WHEN DONE
		for (int x = 0; x < tnpArray.size(); x++) {
			System.out.println(tnpArray.get(x).getTNPtaskOne() + " " + tnpArray.get(x).getTNPtaskTwo() + " "
					+ tnpArray.get(x).getTNPpenalty());
		}
		return line;
	}

	private String readToNearTask(BufferedReader br, TNTelement tntElement, String line) {

		try {
			int i = 0;
			String lastLine = "";
			String exitString = "machine penalties:[\\s]*";

			while (!(line = br.readLine()).matches(exitString)) {

				Matcher emptyCheck = Pattern.compile("[\\s]*").matcher(line);

				if (emptyCheck.matches()) {
					lastLine = line;
					continue;
				}

				Matcher matcher = Pattern.compile(("\\(([A-H]),([A-H])\\)")).matcher(line);
				Matcher invalidTaskOne = Pattern.compile(("\\(([^A-H]),([A-H])\\)")).matcher(line);
				Matcher invalidTaskTwo = Pattern.compile(("\\(([A-H]),([^A-H])\\)")).matcher(line);
				Matcher invalidTaskAll = Pattern.compile(("\\(([^A-H]),([^A-H])\\)")).matcher(line);
				if (invalidTaskOne.matches() || invalidTaskTwo.matches() || (invalidTaskAll.matches())) {
					Shell.constructFileOutPut(3);
				}

				if (matcher.matches()) {

					tntElement = new TNTelement(matcher.group(1), matcher.group(2));
					tntArray.add(tntElement);
					// if (!fpa.containsKey(matcher.group(1)) &&
					// !fpa.containsValue(matcher.group(2)))
					// {
					// fpa.put(Integer.parseInt(matcher.group(1)), matcher.group(2));
					System.out.println(tntArray.get(i).getTNTtaskOne() + " " + tntArray.get(i).getTNTtaskTwo());

					i++;
				} else {
					// TODO partial assignment
					Shell.constructFileOutPut(1);

				}

				lastLine = line;
			}
			if (!(lastLine.matches("([\\s]*)"))) {
				Shell.constructFileOutPut(1);
			}
		} catch (IOException e) {
			Shell.constructFileOutPut(1);
		}
		/*
		 * catch(IllegalStateException e) { System.out.println("invalid machine/task");
		 * System.exit(0); return null;
		 */
		// }
		return line;
	}

	private String readMachinePenalties(BufferedReader br, String line) {

		try {
			int i = 0;
			String lastLine = "";
			String exitString = "too-near penalities[\\s]*";
			while (!(line = br.readLine()).matches(exitString)) {
				Matcher emptyCheck = Pattern.compile("[\\s]*").matcher(line);
				if (emptyCheck.matches()) {
					lastLine = line;
					continue;
				}

				String lineCopy = line;
				/*
				 * lineCopy = lineCopy.replaceAll("\\s", ""); if (lineCopy.length() != 8) {
				 * System.out.println("machine penalty error"); System.exit(0); return null;
				 *
				 * }
				 */
				// System.out.println(lineCopy);
				// System.out.println(lineCopy.length());

				/*
				 * Matcher matcher = Pattern.compile(
				 * ("([0-9]*) ([0-9]*) ([0-9]*) ([0-9]*) ([0-9]*) ([0-9]*) ([0-9]*) ([0-9]*)[\\s]*"
				 * )).matcher(line);
				 * 
				 * if (matcher.matches()) { // System.out.println(lineCopy);
				 */

				lineCopy = line;
				lineCopy = lineCopy.replace(" ", ".");
				String[] splitter = lineCopy.split("\\.");
				if (splitter.length < 8) {
					Shell.constructFileOutPut(4);
				}
				if (i > 7) {
					Shell.constructFileOutPut(4);
				}

				for (int j = 0; j < splitter.length; j++) {
					// System.out.println(splitter.length);
					if (j > 7) {
						Shell.constructFileOutPut(4);

					}
					if (Integer.parseInt(splitter[j]) < 0) {
						Shell.constructFileOutPut(6);
					}
					MParray[i][j] = Integer.parseInt(splitter[j]);
					// System.out.println(MParray[i][j]);
				}
				// System.out.println(i);
				i++;
				lastLine = line;
				/*
				 * } else { System.out.println("Error while parsing the input file");
				 * System.exit(0); return null;
				 */
				// }
			}
			if (!(lastLine.matches("([\\s]*)"))) {
				Shell.constructFileOutPut(1);
			}
			System.out.println(Arrays.deepToString(MParray) + "\n");
			if (i < 8) {
				Shell.constructFileOutPut(4);
			}

		} catch (IOException e) {
			Shell.constructFileOutPut(1);
		} /*
			 * catch (ArrayIndexOutOfBoundsException exception) {
			 * System.out.print("machine penalty error"); System.exit(0); return null; }
			 */catch (NumberFormatException e) {
				 Shell.constructFileOutPut(6);
		}

		return line;
	}

	private String readForbiddenMachine(BufferedReader br, FMelement fmElement, String line) {
		int i = 0;
		try {
			String lastLine = "";
			String exitString = "too-near tasks:[\\s]*";
			while (!(line = br.readLine()).matches(exitString)) {

				Matcher emptyCheck = Pattern.compile("[\\s]*").matcher(line);
				if (emptyCheck.matches()) {
					lastLine = line;
					continue;
				}

				Matcher matcher = Pattern.compile(("\\(([1-8]),([A-H])\\)[\\s]*")).matcher(line);
				Matcher invalidTask = Pattern.compile(("\\(([1-8]),([^A-H])\\)[\\s]*")).matcher(line);
				Matcher invalidMach = Pattern.compile(("\\(([^1-8]),([A-H])\\)[\\s]*")).matcher(line);
				Matcher invalidAll = Pattern.compile(("\\(([^1-8]),([^A-H])\\)[\\s]*")).matcher(line);

				if (invalidTask.matches() || invalidMach.matches() || (invalidAll.matches())) {
					Shell.constructFileOutPut(3);

				}
				if (matcher.matches()) {

					fmElement = new FMelement(Integer.parseInt(matcher.group(1)), matcher.group(2));
					fmArray.add(fmElement);
					System.out.println(fmArray.get(i).getFMachine() + " " + fmArray.get(i).getFMtask());
					i++;
				}

				else {
					// TODO partial assignment
					Shell.constructFileOutPut(1);
				}
				lastLine = line;
			}
			if (!(lastLine.matches("([\\s]*)"))) {
				Shell.constructFileOutPut(1);
			}
		} catch (IOException e) {
			Shell.constructFileOutPut(1);
		}
		/*
		 * catch(IllegalStateException e) { System.out.println("invalid machine/task");
		 * System.exit(0); return null; }
		 */
		return line;
	}

	private String readForcedPartialAssignmet(BufferedReader br, String line) {

		try {
			int key;
			String value;
			String lastLine = "";
			String exitString = "forbidden machine:[\\s]*";
			while (!(line = br.readLine()).matches(exitString)) {

				// next = brNext.readLine();
				// System.out.println(next);
				// br.mark();
				// if (next.equals(exitString)) {return;}
				// line = br.readLine();

				// if(line.equals(exitString)){
				// br.reset();
				// return line;}

				Matcher emptyCheck = Pattern.compile("[\\s]*").matcher(line);
				if (emptyCheck.matches()) {
					lastLine = line;
					continue;
				}
				/*
				 * Matcher invalidMach =
				 * Pattern.compile(("\\(([0-9]+&&[^1|2|3|45678]),([A-H])\\)[\\s]*")).matcher(
				 * line); Matcher invalidTask =
				 * Pattern.compile(("\\(([1-8]),(\\&&[^A-H])\\)[\\s]*")).matcher(line); Matcher
				 * invalidMachTask =
				 * Pattern.compile(("\\(([0-9]*&&[^1-8]),([A-H]*&&[^A-H])\\)[\\s]*")).matcher(
				 * line);
				 */
				Matcher matcher = Pattern.compile(("\\(([1-8]),([A-H])\\)[\\s]*")).matcher(line);

				if (matcher.matches()) {
					key = Integer.parseInt(matcher.group(1));
					value = matcher.group(2);
					// System.out.println(fpa.get(key) + value);
					// System.out.println(value.equals(fpa.get(key)));

					if (value.equals(fpa.get(key))) {
						// System.out.println(fpa.containsKey(Integer.parseInt(matcher.group(1))) +
						// " " + fpa.containsValue(matcher.group(2)) + " " +"penis");
						// System.out.println("this one is ok it can stay");
					} else if (!fpa.containsKey(Integer.parseInt(matcher.group(1)))
							&& !fpa.containsValue(matcher.group(2))) {
						fpa.put(Integer.parseInt(matcher.group(1)), matcher.group(2));
						System.out.println(Arrays.asList(fpa));

						// System.out.println(fpa.containsKey(Integer.parseInt(matcher.group(1))) +
						// " " + fpa.containsValue(matcher.group(2)) + " " +"penis");
					}

					else {

						Shell.constructFileOutPut(2);
					}

				}

				/*
				 * else if (invalidTask.matches() || invalidMach.matches() ||
				 * invalidMachTask.matches()) { System.out.println("invalid machine/task");
				 * System.exit(0); return null; }
				 */
				else {
					// TODO partial assignment
					Shell.constructFileOutPut(1);
				}
				lastLine = line;
			}
			if (!(lastLine.matches("([\\s]*)"))) {
				Shell.constructFileOutPut(1);
			}
		} catch (IOException e) {
			Shell.constructFileOutPut(1);
		}

		return line;

	}

	// TODO make buffered reader method
	//
	// read through the file
	// parse through each line and create each object
	public static void main(String[] args) {
		Parser parser = new Parser();
		parser.inputReader("inputFile.txt");
	}
}

// TODO create getters for each array list DONE-----
// will be able to return objects from the list
// TODO need to create methods to store the constraints in the lists
// TODO need to check if everything is correct from the inpkut file
// if anything is wrong need to display error
// TODO make sure there is only up to 8 pairs of fpa
// TODO check if there are doubles
