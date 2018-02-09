public class FMelement {
	private int machine;
	private String task;

	public FMelement(int machine, String task) {
		this.machine = machine;
		this.task = task;
	}

	public int getFMachine() {
		return this.machine;
	}

	public String getFMtask() {
		return this.task;
	}
}
