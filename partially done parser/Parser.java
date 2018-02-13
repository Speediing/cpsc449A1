
//java imports
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
		FileReader fr = null;
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
		try {
			// open up the file
			fr = new FileReader(fileName);
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
					constraintCounter++;
					// TODO this should just check that there is a name for the file and that no
					// funny business is going on
					// line = readName(br, line);
				}

				if (line.matches("forced partial assignment:[\\s]*")) {
					constraintCounter++;
					// passes the buffered reader(by reference thats why you don't need to return it
					// current line is also passed into the method so that it can continue where it
					// left off.
					// the methods return the current line so that it can continue checking for the
					// next constraint header

				}

				if (line.matches("forbidden machine:[\\s]*")) {
					constraintCounter++;
					// passing in corresponding object for the constraint

				}

				if (line.matches("too-near tasks:[\\s]*")) {
					constraintCounter++;

				}

				if (line.matches("machine penalties:[\\s]*")) {
					constraintCounter++;

				}

				if (line.matches("too-near penalities[\\s]*")) {
					constraintCounter++;

				}

				//System.out.println(line);
			}
			// checks constraint counter to see if = 6 if not err.
			if (constraintCounter != 6) {
				Shell.constructFileOutPut(1);
			}
			// System.out.println(constraintCounter);
			br.close();
		} catch (FileNotFoundException ex) {
			// SHOULD THIS BE ERROR WHILE PARSING
			System.out.println("File not found");
			System.exit(0);
			return;

		} catch (IOException e) {
			System.out.println("bitchin'");
			Shell.constructFileOutPut(1);
		}

		// try catch
		try {
			boolean nameRead = false, fpaRead = false, fmRead = false, tntRead = false, mpRead = false, tnpRead = false;
			constraintCounter = 0;
			// open up the file
			fr = new FileReader(fileName);
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
				// System.out.println(line);
				// looks for name header along with an infinite amount of white space that
				// follows
				// there can be no white space before though
				if (line.matches("Name:[\\s]*")) {
					if (!nameRead) {
						System.out.println(line);
						line = readName(br, line);
						constraintCounter++;
						nameRead = true;

					} else {
						break;
					}
					// TODO this should just check that there is a name for the file and that no
					// funny business is going on
					// line = readName(br, line);
				}

				if (line.matches("forced partial assignment:[\\s]*")) {
					if (nameRead) {
						constraintCounter++;
						// passes the buffered reader(by reference thats why you don't need to return it
						// current line is also passed into the method so that it can continue where it
						// left off.
						// the methods return the current line so that it can continue checking for the
						// next constraint header
						System.out.println(line);
						fpaRead = true;
						line = readForcedPartialAssignmet(br, line);
						System.out.println(line);
					} else {
						break;
					}
				}

				if (line.matches("forbidden machine:[\\s]*")) {
					if (fpaRead) {

						constraintCounter++;
						// passing in corresponding object for the constraint
						line = readForbiddenMachine(br, fmElement, line);
						System.out.println(line);
						fmRead = true;
					} else {
						break;
					}

				}

				if (line.matches("too-near tasks:[\\s]*")) {
					if (fmRead) {
						constraintCounter++;
						line = readToNearTask(br, tntElement, line);
						System.out.println(line);
						tntRead = true;
					} else {
						break;
					}
				}

				if (line.matches("machine penalties:[\\s]*")) {
					if (tntRead) {
						constraintCounter++;
						line = readMachinePenalties(br, line);
						mpRead = true;
						System.out.println(line);
					} else {
						break;
					}
				}

				if (line.matches("too-near penalities[\\s]*")) {
					if (mpRead)
						constraintCounter++;
					line = readToNearPenalities(br, tnpElement, line);
					tnpRead = true;
				} else {
					break;
				}

			}
			// checks constraint counter to see if = 6 if not err.
			if (constraintCounter != 6) {

				Shell.constructFileOutPut(1);
			}
			// System.out.println(constraintCounter);
			br.close();
		} catch (FileNotFoundException ex) {
			// SHOULD THIS BE ERROR WHILE PARSING
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
				// System.out.println(line);
				Matcher emptyCheck = Pattern.compile("[\\s]*").matcher(line);
				if (emptyCheck.matches()) {
					lastLine = line;
					continue;
				}
				
				Matcher name = Pattern.compile(("[\\S]+[\\s]*")).matcher(line);
				Matcher whiteSpace = Pattern.compile(("([\\S]+[\\s][\\S]+)*")).matcher(line);
				if (whiteSpace.matches()) {
					Shell.constructFileOutPut(1);
				}
				else if(name.matches()) {
					namesCounted++;
				}
				// System.out.println(line);
				
				
				lastLine = line;
			}
			if (!(lastLine.matches("([\\s]*)"))) {
				Shell.constructFileOutPut(1);
			}
			if (namesCounted != 1) {
				// System.out.println(namesCounted);
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
		/*String task1;
		String task2;
		String penalty;*/
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
				Matcher matcher = Pattern.compile(("\\(([A-H]),([A-H]),([0]*|[0]*[1-9][0-9]*)\\)[\\s]*")).matcher(line);
				Matcher format = Pattern.compile(
						("\\(([^\\s]([\\S]*[\\s]*[\\S]*)*),([^\\s]([\\S]*[\\s]*[\\S]*)*),([^\\s]([\\S]*[\\s]*[\\S]*)*)\\)[\\s]*"))
						.matcher(line);

				if (format.matches()) {

					if (!(format.group(1)).matches("[A-H]")) {
						// System.out.println("task1");
						Shell.constructFileOutPut(5);
					}

					else if (!(format.group(3)).matches("[A-H]")) {
						// System.out.println("task2");
						Shell.constructFileOutPut(5);
					}

					else if (!(format.group(5)).matches("[0]*|[0]*[1-9][0-9]*")) {
						// System.out.println("penalty");
						Shell.constructFileOutPut(6);
					}
					
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

					}
					// if format is wrong exit
				} else {
					// System.out.println("parse");
					Shell.constructFileOutPut(1);

				}
			}
		} catch (IOException e) {
			Shell.constructFileOutPut(1);
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

				Matcher matcher = Pattern.compile(("\\(([A-H]),([A-H])\\)[\\s]*")).matcher(line);
				Matcher format = Pattern
						.compile(("\\(([^\\s]([\\S]*[\\s]*[\\S]*)*),([^\\s]([\\S]*[\\s]*[\\S]*)*)\\)[\\s]*"))
						.matcher(line);

				// System.out.pr(intln(line);

				if (format.matches()) {

					if (!(format.group(1)).matches("[A-H]")) {
						Shell.constructFileOutPut(3);
					}

					else if (!(format.group(3)).matches("[A-H]")) {
						// System.out.pr(intln("task2");
						Shell.constructFileOutPut(3);
					}

				}
				
				if (matcher.matches()) {

					tntElement = new TNTelement(matcher.group(1), matcher.group(2));
					tntArray.add(tntElement);
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

				if (line.matches("machine penalties:[\\s]*")) {
					continue;
				}

				// THIS IS THE ERROR

				lineCopy = line;
				// lineCopy = lineCopy.replace(" ", "");
				String[] splitter = lineCopy.split("\\s+");
				if (splitter.length < 8) {

					Shell.constructFileOutPut(4);
				}
				if (i > 7) {

					Shell.constructFileOutPut(4);
				}

				for (int j = 0; j < splitter.length; j++) {

					if (j > 7) {

						Shell.constructFileOutPut(4);

					}
					if (Integer.parseInt(splitter[j]) < 0) {

						// System.out.println("penis");
						Shell.constructFileOutPut(6);
					}
					MParray[i][j] = Integer.parseInt(splitter[j]);
					// System.out.println(MParray[i][j]);
				}

				// System.out.println(i);
				i++;

				lastLine = line;

			}
			// System.out.pr(intln(Arrays.deepToString(MParray) + "\n");
			if (i < 8) {
				// System.out.println("penis");
				Shell.constructFileOutPut(4);
			}
			// System.out.println(line);
			if (!(lastLine.matches("([\\s]*)"))) {
				// System.out.println("penis");
				Shell.constructFileOutPut(1);
			}
		} catch (IOException e) {
			// System.out.println("penis");
			Shell.constructFileOutPut(1);
		}
			 catch (NumberFormatException e) {
			// System.out.println("penis0");
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
				Matcher format = Pattern
						.compile(("\\(([^\\s]([\\S]*[\\s]*[\\S]*)*),([^\\s]([\\S]*[\\s]*[\\S]*)*)\\)[\\s]*"))
						.matcher(line);

				// System.out.pr(intln(line);

				if (format.matches()) {

					if (!(format.group(1)).matches("[1-8]")) {
						// System.out.println("task1");
						Shell.constructFileOutPut(3);
					}

					else if (!(format.group(3)).matches("[A-H]")) {
						// System.out.println("task2");
						Shell.constructFileOutPut(3);
					}

			
				if (matcher.matches()) {

					fmElement = new FMelement(Integer.parseInt(matcher.group(1)), matcher.group(2));
					fmArray.add(fmElement);

					i++;
				}

				else {
					// TODO partial assignment
					Shell.constructFileOutPut(1);
				}
				lastLine = line;
			}
			}
			if (!(lastLine.matches("([\\s]*)"))) {
				Shell.constructFileOutPut(1);
			}
		} catch (IOException e) {
			Shell.constructFileOutPut(1);
		}

		return line;
	}

	private String readForcedPartialAssignmet(BufferedReader br, String line) {

		try {
			int key;
			String value;
			String lastLine = "";
			String exitString = "forbidden machine:[\\s]*";
			while (!(line = br.readLine()).matches(exitString)) {

				Matcher emptyCheck = Pattern.compile("[\\s]*").matcher(line);
				if (emptyCheck.matches()) {
					lastLine = line;
					continue;
				}

				Matcher matcher = Pattern.compile(("\\(([1-8]),([A-H])\\)[\\s]*")).matcher(line);
				Matcher format = Pattern
						.compile(("\\(([^\\s]([\\S]*[\\s]*[\\S]*)*),([^\\s]([\\S]*[\\s]*[\\S]*)*)\\)[\\s]*"))
						.matcher(line);

				// System.out.pr(intln(line);

				if (format.matches()) {

					if (!(format.group(1)).matches("[1-8]")) {
						// System.out.println("task1");
						Shell.constructFileOutPut(3);
					}

					else if (!(format.group(3)).matches("[A-H]")) {
						// System.out.println("task2");
						Shell.constructFileOutPut(3);
					}
				}
				if (matcher.matches()) {
					key = Integer.parseInt(matcher.group(1));
					value = matcher.group(2);
					if (value.equals(fpa.get(key))) {
					
					} else if (!fpa.containsKey(Integer.parseInt(matcher.group(1)))
							&& !fpa.containsValue(matcher.group(2))) {
						fpa.put(Integer.parseInt(matcher.group(1)), matcher.group(2));
					
					}

					else {

						Shell.constructFileOutPut(2);
					}

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

		return line;

	}

}

