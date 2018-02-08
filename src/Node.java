import java.util.*;

public class Node {

    ArrayList<Node> branches = new ArrayList<Node>();
    ArrayList<String> assignment = new ArrayList<String>();
    ArrayList<String> unused_tasks = new ArrayList<String>();
    int depth;
    int penalty;

    //constructor for the root node
    //sets up the default values and then automatically branches off automatically
    public Node() {
        depth = 0;
        penalty = 0;
        //currently the node code only makes three branches and achieves depth 3.
        //The equivalent of three machines and three tasks
        //uncomment the rest of the unused_tasks.add() methods to increase node propagation.
        unused_tasks.add("A");
        unused_tasks.add("B");
        unused_tasks.add("C");
        //unused_tasks.add("D");
        //unused_tasks.add("E");
        //unused_tasks.add("F");
        //unused_tasks.add("G");
        //unused_tasks.add("H");

        for (String element : unused_tasks) {


            //clones two lists to be passed that have the proper element removed or added as needed
            ArrayList<String> pass_assignment = (ArrayList<String>) assignment.clone();
            pass_assignment.add(element);
            ArrayList<String> pass_unused = (ArrayList<String>) unused_tasks.clone();
            pass_unused.remove(element);

            //creates new node here with the non-root constructor
            branches.add(new Node(pass_assignment, pass_unused, depth + 1, penalty));
        }
    }

    //constructor for the non-root node. Used for all node generation after the root.
    //behaves similarly to root constructor except it recieves it's values from the previous node
    //also currently prints out it's info as it's being created, so you can check it out.
    public Node(ArrayList<String> input_assignment, ArrayList<String> input_unused, int input_depth, int input_penalty) {
        assignment = input_assignment;
        unused_tasks = input_unused;
        depth = input_depth;
        penalty = Softconstraints.penaltyValue(assignment);
        
        if(depth>0) {
        	printlist(assignment);
        	System.out.println(penalty);
        }

        //PRINT STATEMENT HERE. THIS IS WHERE STUFF IS PRINTED
        //System.out.print("assigned: ");
        //printlist(assignment);
        //System.out.print("remaining: ");
        //printlist(unused_tasks);
        //System.out.println("Node: " + depth + "," + assignment.toString());

//i think the error is with your for loop 
//its getting to depth 3 then seeing that there is no unused tasks and basically  fucking off
        for (String element : unused_tasks) {

        	if(HardConstraints.check(assignment)&&(penalty < Shell.min_pen||Shell.min_pen<0)) {
        		
        		/*if(depth == 3) {
        			System.out.println("asdfasdgarhgaerghaerhaerhaerharhai");
        			Shell.min_pen = 27;
        			Shell.min_list = assignment;
        		}
        		*/
                ArrayList<String> pass_assignment = (ArrayList<String>) assignment.clone();
                pass_assignment.add(element);
                ArrayList<String> pass_unused = (ArrayList<String>) unused_tasks.clone();
                pass_unused.remove(element);

                branches.add(new Node(pass_assignment, pass_unused, depth ++, penalty));
        	}
        	
        }
        //if you put the if statement out here it will catch it. 
        if(depth == 3) {
			System.out.println("asdfasdgarhgaerghaerhaerhaerharhai");
			Shell.min_pen = 27;
			Shell.min_list = assignment;
		}

    }

    //Method just to help with printing out the node contents
    private void printlist(ArrayList<String> input) {
        for (String element : input) {
            System.out.print(element + ",");
        }
        System.out.println("");
    }

}


