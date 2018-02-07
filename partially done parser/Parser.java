import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
	public int[][] MParray = new int[8][8];
	public static HashMap<Integer, String> fpa = new HashMap<Integer, String>();
	public static ArrayList<FMelement> fpArray = new ArrayList<FMelement>();
	public static ArrayList<TNTelement> tntArray = new ArrayList<TNTelement>();
	// public static ArrayList<FPArray> fpArray = new ArrayList<FPArray>();
	public ArrayList<TNPelement> tnpArray = new ArrayList<TNPelement>();
	// public ArrayList<String> fileLines = new ArrayList<String>();

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

			while ((line = br.readLine()) != null) {

				Matcher emptyCheck = Pattern.compile("[\\s]*").matcher(line);
				if (emptyCheck.matches())
					continue;

				if (line.matches("Name:[\\s]*")) {
					constraintCounter++;
					line = readName(br, line);
				} // else{System.out.println("this doesnt exist");}
				// TODO forced partial assignment, forbidden assingment done using hashmaps

				if (line.matches("forced partial assignment:[\\s]*")) {
					constraintCounter++;
					line = readForcedPartialAssignmet(br, line);

				}

				if (line.matches("forbidden machine:[\\s]*")) {
					constraintCounter++;
					line = readForbiddenMachine(br, fmElement, line);

				}
				// TODO too near tasks will be done using class arary list
				if (line.matches("too-near tasks:[\\s]*")) {
					constraintCounter++;
					line = readToNearTask(br, tntElement, line);
				}

				// TODO machine penalties will be stored as a regular old 2d array
				if (line.matches("machine penalties:[\\s]*")) {
					constraintCounter++;
					line = readMachinePenalties(br, line);
				}
				// TODO too-near penalties will be done with a custom array list using classses
				if (line.matches("too-near penalities[\\s]*")) {
					constraintCounter++;
					line = readToNearPenalities(br, tnpElement, line);
				}

			}

			if (constraintCounter != 6) {

				System.out.println("Error while parsing input file");
				System.exit(0);
				return;
			}
			System.out.println(constraintCounter);
		} catch (FileNotFoundException ex) {
			
			System.out.println("File not found");
			System.exit(0);
			return;
			
		} catch (IOException e) {
			
			System.out.println("Error while parsing the input file");
			System.exit(0);
			return;
		}
	}
/*	private String readName(BufferedReader br, String line) {
		try {
			String lastLine = "";
			String exitString = "machine penalties:";
			
			while (!(line = br.readLine()).equals(exitString)) {
				
				Matcher emptyCheck = Pattern.compile("[\\s]*").matcher(line);
				if (emptyCheck.matches()) {
					lastLine = line;
					continue;
				}
				
				Matcher matcher = Pattern.compile(("")).matcher(line);
			
			lastLine = line;
			}
			if (!(lastLine.matches("([\\s]*)"))) {
				System.out.println("Error while parsing the input file penis");
				System.exit(0);
				return null;
			}
		} catch (IOException e) {
			System.out.println("Error while parsing the input file");
			System.exit(0);
			return null;
		}
		
		
		return line;
	}*/
	private String readToNearPenalities(BufferedReader br, TNPelement tnpElement, String line) {
	

		try {
			
			while ((line = br.readLine()) != null) {

				Matcher emptyCheck = Pattern.compile("[\\s]*").matcher(line);
				
				if (emptyCheck.matches())
					continue;

				Matcher matcher = Pattern.compile(("\\(([A-H]),([A-H]),([0-9]*)\\)")).matcher(line);
				Matcher invalidTaskOne = Pattern.compile(("\\(([i-zI-Z]),([A-H]),(.*)\\)")).matcher(line);
				Matcher invalidTaskTwo = Pattern.compile(("\\(([A-H]),([i-zI-Z]),(.*)\\)")).matcher(line);
				Matcher invalidTaskAll = Pattern.compile(("\\(([i-zI-Z]),([i-zI-Z]),(.*)\\)")).matcher(line);

				if (invalidTaskOne.matches() || invalidTaskTwo.matches() || invalidTaskAll.matches()) {
					
					System.out.println("invalid task");
					System.exit(0);
					return null;
				}

				if (matcher.matches()) {
					// System.out.println(tnpArray.size());
					Boolean penaltyChanged = false;

					if (tnpArray.size() == 0) {
						tnpElement = new TNPelement(matcher.group(1), matcher.group(2),
								Long.parseLong(matcher.group(3)));
						tnpArray.add(tnpElement);
						/*// if (!fpa.containsKey(matcher.group(1)) &&
						// !fpa.containsValue(matcher.group(2)))
						// {
						// fpa.put(Integer.parseInt(matcher.group(1)), matcher.group(2));
						// System.out.println(tnpArray.get(i).getTNPtaskOne() + " " +
						// tnpArray.get(i).getTNPtaskTwo() + " "
						// + tnpArray.get(i).getTNPpenalty());
						i++;*/
					} else {

						for (int j = 0; j < tnpArray.size() - 1; j++)

						{

							String taskOne = tnpArray.get(j).getTNPtaskOne();
							String taskTwo = tnpArray.get(j).getTNPtaskTwo();
							
							if (taskOne.equals(matcher.group(1)) && taskTwo.equals(matcher.group(2))) {
								
								tnpArray.get(j).setTNPpenalty(Long.parseLong(matcher.group(3)));
								
								penaltyChanged = true;
								
								//System.out.println(tnpArray.get(j) + " element was changed");
							}

						}

						if (penaltyChanged)
							continue;

						tnpElement = new TNPelement(matcher.group(1), matcher.group(2),
								Long.parseLong(matcher.group(3)));
						tnpArray.add(tnpElement);
						
						/*// if (!fpa.containsKey(matcher.group(1)) &&
						// !fpa.containsValue(matcher.group(2)))
						// {
						// fpa.put(Integer.parseInt(matcher.group(1)), matcher.group(2));
						// System.out.println(tnpArray.get(i).getTNPtaskOne() + " " +
						// tnpArray.get(i).getTNPtaskTwo() + " "
						// + tnpArray.get(i).getTNPpenalty());
						i++;
*/
					}

				} else {
					// TODO partial assignment
					System.out.println("Error while parsing the input file");

					System.exit(0);

					return null;

				}

			}

		} catch (IOException e) {
			System.out.println("Error while parsing the input file");
			System.exit(0);
			return null;
		}
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
			String exitString = "machine penalties:";
			
			while (!(line = br.readLine()).equals(exitString)) {

				Matcher emptyCheck = Pattern.compile("[\\s]*").matcher(line);
				
				if (emptyCheck.matches()) {
					lastLine = line;
					continue;
				}

				Matcher matcher = Pattern.compile(("\\(([A-H]),([A-H])\\)")).matcher(line);

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
					System.out.println("Error while parsing the input file");
					System.exit(0);

					return null;

				}

				lastLine = line;
			}
			if (!(lastLine.matches("([\\s]*)"))) {
				System.out.println("Error while parsing the input file penis");
				System.exit(0);
				return null;
			}
		} catch (IOException e) {
			System.out.println("Error while parsing the input file");
			System.exit(0);
			return null;
		}
		return line;
	}

	private String readMachinePenalties(BufferedReader br, String line) {
		
		try {
			int i = 0;
			String lastLine = "";
			String exitString = "too-near penalities";
			while (!(line = br.readLine()).equals(exitString)) {
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

				Matcher matcher = Pattern
						.compile(("([0-9]*) ([0-9]*) ([0-9]*) ([0-9]*) ([0-9]*) ([0-9]*) ([0-9]*) ([0-9]*)[\\s]*"))
						.matcher(line);

				if (matcher.matches()) {
					// System.out.println(lineCopy);
					lineCopy = line;
					String[] splitter = lineCopy.split("\\s");

					for (int j = 0; j < 8; j++) {
						// System.out.println(splitter.length);
						MParray[i][j] = Integer.parseInt(splitter[j]);
						// System.out.println(MParray[i][j]);

					}

					i++;
				} else {
					System.out.println("Error while parsing the input file");
					System.exit(0);
					return null;

				}

				lastLine = line;
			}
			if (!(lastLine.matches("([\\s]*)"))) {
				System.out.println("Error while parsing the input file");
				System.exit(0);
				return null;
			}

			System.out.println(Arrays.deepToString(MParray) + "\n");
			if (i != 8) {
				System.out.println("machine penalty error");
				System.exit(0);
				return null;

			}
		} catch (IOException e) {
			System.out.println("Error while parsing the input file");
			System.exit(0);
			return null;
		} catch (ArrayIndexOutOfBoundsException exception) {
			System.out.print("machine penalty error");
			System.exit(0);
			return null;
		}

		return line;
	}

	private String readForbiddenMachine(BufferedReader br, FMelement fmElement, String line) {
		int i = 0;
		try {
			String lastLine = "";
			String exitString = "too-near tasks:";
			while (!(line = br.readLine()).equals(exitString)) {

				Matcher emptyCheck = Pattern.compile("[\\s]*").matcher(line);
				if (emptyCheck.matches()) {
					lastLine = line;
					continue;
				}

				Matcher matcher = Pattern.compile(("\\(([1-8]),([A-H])\\)[\\s]*")).matcher(line);

				if (matcher.matches()) {

					fmElement = new FMelement(Integer.parseInt(matcher.group(1)), matcher.group(2));
					fpArray.add(fmElement);
					System.out.println(fpArray.get(i).getFMachine() + " " + fpArray.get(i).getFMtask());
					i++;
				} else {
					// TODO partial assignment
					System.out.println("Error while parsing the input file");
					System.exit(0);
					return null;
				}
				lastLine = line;
			}
			if (!(lastLine.matches("([\\s]*)"))) {
				System.out.println("Error while parsing the input file");
				System.exit(0);
				return null;
			}
		} catch (IOException e) {
			System.out.println("Error while parsing the input file");
			System.exit(0);
			return null;
		}
		return line;
	}

	private String readForcedPartialAssignmet(BufferedReader br, String line) {

		try {
			int key;
			String value;
			String lastLine = "";
			String exitString = "forbidden machine:";
			while (!(line = br.readLine()).equals(exitString)) {

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
					} else {

						System.out.println("partial assignment error");
						System.exit(0);
						return null;
					}

				} else {
					// TODO partial assignment
					System.out.println("Error while parsing the input file");
					System.exit(0);
					return null;
				}
				lastLine = line;
			}
			if (!(lastLine.matches("([\\s]*)"))) {
				System.out.println("Error while parsing the input file");
				System.exit(0);
				return null;
			}
		} catch (IOException e) {
			System.out.println("Error while parsing the input file");
			System.exit(0);
			return null;
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
