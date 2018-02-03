public class FPAelement{

  private int machine;
  private String task;

  public FPAelement(int machine, String task){
    this.machine = machine;
    this.task = task;
  }
  public int getFPAmachine(){
    return this.machine;
  }
  public String getFPAtask(){
    return this.task;
  }

}
