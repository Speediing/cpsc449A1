import java.util.ArrayList;
import java.util.HashMap;

public class Softconstraints {
	private static ArrayList<String> machines;
	private static HashMap< String, Integer> taskConversion;

    //initialize based on contraints and given assignment
 /*   public Softconstraints(ArrayList<String> machines){
        this.machines = machines;
        HashMap< String, Integer> taskConversion = new HashMap< String, Integer>();
        taskConversion.put("A", 1);
        taskConversion.put( "B", 2);
        taskConversion.put("C", 3);
        taskConversion.put("D", 4);
        taskConversion.put("E", 5);
        taskConversion.put("F", 6);
        taskConversion.put("G", 7);
        taskConversion.put("H", 8);
        this.taskConversion = taskConversion;

    }
*/


    //Check penalty value based on penalty matrix and too near penalties
    //THIS IS NOT DONE
    public static int penaltyValue(ArrayList<String> input_machines) {

        machines = input_machines;
        taskConversion = new HashMap< String, Integer>();
        
        taskConversion.put("A", 1);
        taskConversion.put( "B", 2);
        taskConversion.put("C", 3);
        taskConversion.put("D", 4);
        taskConversion.put("E", 5);
        taskConversion.put("F", 6);
        taskConversion.put("G", 7);
        taskConversion.put("H", 8);


        int totalPenalty = 0;
        for (int machine = 0; machine < machines.size(); machine++) {
            String taskIndex = machines.get(machine);
            //WTF
            int penalty = Parser.MParray[taskConversion.get(taskIndex)-1][machine];
            totalPenalty = totalPenalty + penalty;
        }
        for(int p = 0; p< Parser.tnpArray.size(); p++){
            TNPelement penalty = Parser.tnpArray.get(p);
            for (int task = 0; task<machines.size(); task++){
                String first;
                String second;
                if (task == machines.size()-1){
                	first = machines.get(machines.size()-1);
                    second = machines.get(0);

                }else{
                    first = machines.get(task);
                    second = machines.get(task+1);

                }
//                System.out.println("ENDME");
//                System.out.println(penalty.getTNPtaskOne());
//                System.out.println(first);
//                System.out.println(penalty.getTNPtaskTwo());
//                System.out.println(second);
                if ((penalty.getTNPtaskOne().equals(first) && penalty.getTNPtaskTwo().equals(second))){
//                    System.out.println("DOGGO");
                	totalPenalty = penalty.getTNPpenalty() + totalPenalty;
                }

            }

        }
        return totalPenalty;
    }
}
