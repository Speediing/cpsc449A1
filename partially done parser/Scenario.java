import java.util.ArrayList;
import java.util.HashMap;

public class Scenario {
    private ArrayList<Integer> machines;
    private  ArrayList<FMelement> fpArray;
    private HashMap<Integer, String> fpa;
    private ArrayList<TNTelement> tntArray;
    private HashMap<Integer, String> taskConversion;

    public Scenario(ArrayList<Integer> machines, ArrayList<FMelement> fpArray, HashMap<Integer, String> fpa, ArrayList<TNTelement> tntArray ){
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
    public boolean isValid(){
        for (int task =1; task<machines.size() + 1; task++){
            int machine = this.machines.get(task);
            FMelement assignment = new FMelement(machine, this.taskConversion.get(task));
            if (this.fpArray.contains(assignment)){
                return false;

            }

        }
    return true;
    }
}
