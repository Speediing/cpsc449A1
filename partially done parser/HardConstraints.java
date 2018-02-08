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
	 MAXASSIGNMENTLENGTH = 7;
	 
	 //Variables
	 boolean isValid = true;
	 
	 //Methods
	 
	 /*
	  * Name: check
	  * Input: an ArrayList by name assignment
	  * Ouptut: a boolean value
	  * Description: Method takes in the assignment needed to be checked and 
	  * sends it as well as the index to other boolean methods, to be returned
	  * and check if the assignment is valid.
	  */
	 public static boolean check (ArrayList assignment) {
		 
		 int index = assignment.size() - 1;
		 
		 isValid = HardConstraints.forcedPartialAssignment(index, assignment);
		 
		 if (isValid == false) return isValid;
		
		 isValid = HardConstraints.forbiddenMachine(index, assignment);
		 
		 if (isValid == false) return isValid;
		 
		 isValid = HardConstraints.tooNearTasks(index, assignment);
		 
		 return isValid;
		 
	 }
	 
	 /*
	  * Name: forcedPartialAssignment
	  * Input: an integer by name index, an ArrayList by name assignment
	  * Ouptut: a boolean value
	  * Description: Method takes in the index of the assignment and the assignment needed to be 
	  * checked and checks if it includes the forced assignments, returns false if it does not.
	  */
	 public static boolean forcedPartialAssignment (int index, ArrayList assignment) {
		 
		 char taskGiven = assignment.get(index);
		 
		 if (fpa.containsValue(index)) {
			 if (index != fpa.get(taskGiven)) isValid = false;
		 }
		 
		 return isValid;
	 }
	 
	 /*
	  * Name: forbiddenMachine
	  * Input: an integer by name index, an ArrayList by name assignment
	  * Ouptut: a boolean value
	  * Description: Method takes in the index of the assignment and the assignment needed to be 
	  * checked and checks if it includes the forbidden machines, returns false if it does.
	  */
	 public static boolean forbiddenMachine (int index, ArrayList assignment) {
		 
		 char taskGiven = assignment.get(index);
		 
		 for (FMelement element : fmArray) {
			 task = element.getFMtask();
			 machine = element.getFMachine();
			 
			 if (task == taskGiven) {
				 if (machine == index) isValid = false;
			 }
		 }
		 
		 return isValid;
	 }
	 
	 /*
	  * Name: tooNearTasks
	  * Input: an integer by name index, an ArrayList by name assignment
	  * Ouptut: a boolean value
	  * Description: Method takes in the index of the assignment and the assignment needed to be 
	  * checked and checks if it includes the too near tasks, returns false if it does.
	  */
	 public static boolean tooNearTasks (int index, ArrayList assignment) {
		 
		 char taskGiven2 = assignment.get(index);
		 
		 int prevIndex = index - 1;
		 if (prevIndex = -1) return isValid;
		 
		 char taskGiven1 = assignment.get(prevIndex);
		 
		 for (TNTelement element : tntArray) {
			 task1 = element.getTNTtaskOne();
			 task2 = element.getTNTtaskTwo();
			 
			 if (task2 == taskGiven2) {
				 if (task1 == taskGiven1) isValid = false;
			 }
		 }
		 
		 return isValid;
	 }
		 
		 
		 		 