public class TNPelement{
  private  String taskOne;
  private  String taskTwo;
  private  long penalty;

  public TNPelement(String taskOne, String taskTwo, long penalty){
    this.taskOne = taskOne;
    this.taskTwo = taskTwo;
    this.penalty = penalty;
  }
  public String getTNPtaskOne(){
    return this.taskOne;
  }
  public String getTNPtaskTwo(){
    return this.taskTwo;
  }
  public long getTNPpenalty(){
    return this.penalty;
  }
}
