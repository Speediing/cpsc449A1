public class TNPelement {
	private String taskOne;
	private String taskTwo;
	private int penalty;

	public TNPelement(String taskOne, String taskTwo, int penalty) {
		this.taskOne = taskOne;
		this.taskTwo = taskTwo;
		this.penalty = penalty;
	}

	public String getTNPtaskOne() {
		return this.taskOne;
	}

	public String getTNPtaskTwo() {
		return this.taskTwo;
	}

	public int getTNPpenalty() {
		return this.penalty;
	}

	public void setTNPpenalty(int newPenalty) {
		this.penalty = newPenalty;
	}
}
