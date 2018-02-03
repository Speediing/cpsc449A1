public class TNPelement{
  private  String taskOne;
  private  String taskTwo;
  private  int penalty;

  public TNPelement(String taskOne, String taskTwo, int penalty){
    this.taskOne = taskOne;
    this.taskTwo = taskTwo;
    this.penalty = penalty;
  }
  public String getTaskOne(){
    return this.taskOne;
  }
  public String getTaskTwo(){
    return this.taskTwo;
  }
  public int getPenalty(){
    return this.penalty;
  }
}
