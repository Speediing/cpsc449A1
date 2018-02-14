/*
 * CPSC 449
 * Assignment 1
 * Hard Constraints
 * Jesus Cuadra
 * Version 1.02
 */
 
 import java.util.ArrayList;
 import java.util.HashMap;
 
 public class HardConstraints {
	 
	 //Constants
	 int MAX_ASSIGNMENT_LENGTH = 7;
	 
	 //Methods
	 
	 /*
	  * Name: check
	  * Input: an ArrayList by name assignment
	  * Ouptut: a boolean value
	  * Description: Method takes in the assignment needed to be checked and 
	  * sends it as well as the index to other boolean methods, to be returned
	  * and check if the assignment is valid.
	  */
	 public static boolean check (ArrayList<String> assignment) {
		 
		 boolean isValid = true;
		 
		 int getme = assignment.size() - 1;
		 System.out.println(getme);
		 
		 isValid = HardConstraints.forcedPartialAssignment(getme, assignment, isValid);
		 System.out.println("isvalid: " + isValid);
		 
		 if (isValid == false) return isValid;
		
		 isValid = HardConstraints.forbiddenMachine(getme, assignment, isValid);
		 System.out.println("isvalid: " + isValid);
		 
		 if (isValid == false) return isValid;
		 
		 isValid = HardConstraints.tooNearTasks(getme, assignment, isValid);
		 System.out.println("isvalid: " + isValid);
		 
		 return isValid;
		 
	 }
	 
	 /*
	  * Name: forcedPartialAssignment
	  * Input: an integer by name index, an ArrayList by name assignment
	  * Output: a boolean value
	  * Description: Method takes in the index of the assignment and the assignment needed to be 
	  * checked and checks if it includes the forced assignments, returns false if it does not.
	  */
	 public static boolean forcedPartialAssignment (int GETME, ArrayList<String> assignment, boolean isValid) {
		 System.out.println("fp");
		 String taskGiven = assignment.get(GETME);
		 //System.out.println("getme: " + GETME + " , taskGiven: " + taskGiven);
		 
		 if (Parser.fpa.containsKey(GETME+1)) {
			 //System.out.println("hi");
			 if (!taskGiven.equals(Parser.fpa.get(GETME+1))) {
				 //System.out.println("hi");
				 isValid = false;
			 }
		 }
		 
		 return isValid;
	 }
	 
	 /*
	  * Name: forbiddenMachine
	  * Input: an integer by name index, an ArrayList by name assignment
	  * Output: a boolean value
	  * Description: Method takes in the index of the assignment and the assignment needed to be 
	  * checked and checks if it includes the forbidden machines, returns false if it does.
	  */
	 public static boolean forbiddenMachine (int getme, ArrayList<String> assignment, boolean isValid) {
		 System.out.println("fm");
		 String taskGiven = assignment.get(getme);
		 System.out.println("taskGiven: " + taskGiven + " getme: " + getme);

		 for (FMelement element : Parser.fmArray) {
			 //System.out.println("kms");
			 String task = element.getFMtask();
			 int machine = element.getFMachine();
			 //System.out.println("task: " + task + ", machine: " + machine);
			 
			 if (task.equals(taskGiven)) {
				 //System.out.println("ho");
				 if (machine == (getme+1)) {
					 //System.out.println("ho");
					 isValid =  false;
				 }
			 }
		 }
		 
		 return isValid;
	 }
	 
	 /*
	  * Name: tooNearTasks
	  * Input: an integer by name index, an ArrayList by name assignment
	  * Output: a boolean value
	  * Description: Method takes in the index of the assignment and the assignment needed to be 
	  * checked and checks if it includes the too near tasks, returns false if it does.
	  */
	 public static boolean tooNearTasks (int getme, ArrayList<String> assignment, boolean isValid) {
		 System.out.println("tnt");
		 String taskGiven2 = assignment.get(getme);
		 
		 int prevIndex = getme - 1;
		 if (prevIndex == -1) {
			 System.out.println("he");
			 return isValid;
		 }
		 
		 String taskGiven1 = assignment.get(prevIndex);
		 
		 for (TNTelement element : Parser.tntArray) {
			 String task1 = element.getTNTtaskOne();
			 String task2 = element.getTNTtaskTwo();
			 //System.out.println("task1: " + task1 + ", task2: " + task2);
			 
			 if (task2.equals(taskGiven2)) {
				 System.out.println("kms");
				 if (task1.equals(taskGiven1)) { 
					 System.out.println("kys");
					 isValid = false;
				 }
			 }
		 }		 
		 return isValid;
	 }

 }
		 

