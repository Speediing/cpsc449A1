import java.util.ArrayList;
import java.util.HashMap;

public class Scenario {
    private ArrayList machines;
    private  ArrayList<FMelement> fpArray;
    private HashMap<Integer, String> fpa;
    private ArrayList<TNTelement> tntArray;

    public Scenario(ArrayList machines, ArrayList<FMelement> fpArray, HashMap<Integer, String> fpa, ArrayList<TNTelement> tntArray ){
        this.fpArray = fpArray;
        this.machines = fpArray;
        this.fpa = fpa;
        this.tntArray = tntArray;
    }
}
