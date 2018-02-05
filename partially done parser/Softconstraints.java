import java.util.ArrayList;
import java.util.HashMap;

public class Softconstraints {
    private ArrayList<String> machines;
    private HashMap< String, Integer> taskConversion;

    //initialize based on contraints and given assignment
    public Softconstraints(ArrayList<String> machines){
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



    //Check penalty value based on penalty matrix and too near penalties
    //THIS IS NOT DONE
    public Long penaltyValue() {
        Long totalPenalty = null;
        for (int machine = 0; machine < this.machines.size(); machine++) {
            String taskIndex = this.machines.get(machine);
            ArrayList<Integer> row = Parser.penaltyMatrix.get(this.taskConversion.get(taskIndex));
            Integer penalty = row.get(machine);
            totalPenalty = totalPenalty + penalty;
        }
        for(int p = 0; p< Parser.tnpArray.size(); p++){
            TNPelement penalty = Parser.tnpArray.get(p);
            for (int task = 0; task<this.machines.size(); task++){
                String first;
                String second;
                if (task == this.machines.size()){
                    first = this.machines.get(this.machines.size());
                    second = this.machines.get(0);

                }else{
                    first = this.machines.get(task);
                    second = this.machines.get(task+1);

                }
                if ((penalty.getTNPtaskOne() == first && penalty.getTNPtaskTwo() == second) || ((penalty.getTNPtaskOne() == second && penalty.getTNPtaskTwo() == first))){
                    totalPenalty = penalty.getTNPpenalty() + totalPenalty;
                }

            }

        }
        return totalPenalty;
    }
}
