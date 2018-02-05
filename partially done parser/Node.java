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
        unused_tasks.add("a");
        unused_tasks.add("b");
        unused_tasks.add("c");
        //unused_tasks.add("d");
        //unused_tasks.add("e");
        //unused_tasks.add("f");
        //unused_tasks.add("g");
        //unused_tasks.add("h");

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
        penalty = input_penalty;

        //PRINT STATEMENT HERE. THIS IS WHERE STUFF IS PRINTED
        System.out.print("assigned: ");
        printlist(assignment);
        System.out.print("remaining: ");
        printlist(unused_tasks);
        System.out.println("Node: " + depth + "," + assignment.toString());


        for (String element : unused_tasks) {

            //CHECKS HDRE A;SLKFJAL;SKJDFGASDG;OAIHER;OIGRW;HIOWGROHIUWGROHGRWOHRWGOHWRGRWG;OWAR;OIH
            System.out.println("TESTEST: " + assignment.toString());

            ArrayList<String> pass_assignment = (ArrayList<String>) assignment.clone();
            pass_assignment.add(element);
            ArrayList<String> pass_unused = (ArrayList<String>) unused_tasks.clone();
            pass_unused.remove(element);

            branches.add(new Node(pass_assignment, pass_unused, depth + 1, penalty));
        }


    }

    //Method just to help with printing out the node contents
    private void printlist(ArrayList<String> input) {
        for (String element : input) {
            System.out.print(element + ",");
        }
        System.out.println("");
    }
public static void main(String[] args){
    Parser parser = new Parser();
    parser.inputReader("inputFile.txt");
    Node n = new Node();
}
}


