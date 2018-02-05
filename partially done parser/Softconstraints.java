import java.util.ArrayList;
import java.util.HashMap;

public class Softconstraints {
    private ArrayList<Integer> machines;
    private  ArrayList<FMelement> fpArray;
    private HashMap<Integer, String> fpa;
    private ArrayList<TNTelement> tntArray;
    private HashMap<Integer, String> taskConversion;


    //initialize based on contraints and given assignment
    public Softconstraints(ArrayList<Integer> machines, ArrayList<FMelement> fpArray, HashMap<Integer, String> fpa, ArrayList<TNTelement> tntArray ){
        this.fpArray = fpArray;
        this.machines = machines;
        this.fpa = fpa;
        this.tntArray = tntArray;
        HashMap<Integer, String> taskConversion = new HashMap<Integer, String>();
        taskConversion.put(1, "A");
        taskConversion.put(2, "B");
        taskConversion.put(3, "C");
        taskConversion.put(4, "D");
        taskConversion.put(5, "E");
        taskConversion.put(6, "F");
        taskConversion.put(7, "G");
        taskConversion.put(8, "H");
        this.taskConversion = taskConversion;


    }
//    //Check if valid based on Forbidden Machines and Forced Partial assignments and Forced Machines
//    //THIS IS NOT DONE(EXAMPLE MOSTLY)
//    public boolean isValid(){
//        for (int task =0; task<machines.size(); task++){
//            int machine = this.machines.get(task);
//            FMelement assignment = new FMelement(machine, this.taskConversion.get(task+1));
//            if (this.fpArray.contains(assignment)){
//                return false;
//
//            }
//
//        }
//    return true;
//    }
    //Check penalty value based on penalty matrix and too near penalties
    //THIS IS NOT DONE
    public int penaltyValue(){
        int penalty = 0;
        return penalty;

    }
}
