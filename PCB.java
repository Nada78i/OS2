public class PCB {
    private String processID;
	private int priority;
	private int arrivalTime;
	private int copyArrivalTime;
	private int CpuBurst;
	private int copyCPUpuBurst;
	private int startTime;
	private int terminationTime;
	private int turnaroundTime;
	private int waitingTime;
	private int responseTime;
	int currentTime = 0;
	boolean terminated = true;

	public PCB(String processID, int priority, int arrivalTime, int cpuBurst) {
		this.processID = processID;
		this.priority = priority;
		this.arrivalTime = arrivalTime;
		this.copyArrivalTime = arrivalTime;
		this.CpuBurst = cpuBurst;
		this.copyCPUpuBurst = cpuBurst;
		this.startTime = -1;
		this.terminationTime = -1;
		this.turnaroundTime = 0;
		this.waitingTime = 0;
		this.responseTime = 0;
	}

	public PCB() {
	}

	public String getProcessID() {
		return processID;
	}

	public void setProcessID(String processID) {
		this.processID = processID;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public int getCopyArrivalTime() {
		return copyArrivalTime;
	}

	public void setCopyArrivalTime(int copyArrivalTime) {
		this.copyArrivalTime = copyArrivalTime;
	}

	public int getCpuBurst() {
		return CpuBurst;
	}

	public void setCpuBurst(int cpuBurst) {
		CpuBurst = cpuBurst;
	}

	public int getCopyCPUpuBurst() {
		return copyCPUpuBurst;
	}

	public void setCopyCPUpuBurst(int copyCPUpuBurst) {
		this.copyCPUpuBurst = copyCPUpuBurst;
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getTerminationTime() {
		return terminationTime;
	}

	public void setTerminationTime(int terminationTime) {
		this.terminationTime = terminationTime;
	}

	public int getTurnaroundTime() {
		return turnaroundTime;
	}

	public void setTurnaroundTime(int turnaroundTime) {
		this.turnaroundTime = turnaroundTime;
	}

	public int getWaitingTime() {
		return waitingTime;
	}

	public void setWaitingTime(int waitingTime) {
		this.waitingTime = waitingTime;
	}

	public int getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(int responseTime) {
		this.responseTime = responseTime;
	}

	public int getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(int currentTime) {
		this.currentTime = currentTime;
	}

	public boolean isTerminated() {
		return terminated;
	}

	public void setTerminated(boolean terminated) {
		this.terminated = terminated;
	}

	@Override
	public String toString() {
		return "Process ID:" + processID + "\nPriority:" + priority + "\nArrival time:" + arrivalTime + "\nCPU burst:"
				+ CpuBurst + "\nStart time:" + startTime + " \nTermination time:" + terminationTime
				+ "\nTurnAround time:" + turnaroundTime + " \nWaiting time:" + waitingTime + "\nResponse time:"
				+ responseTime + "\n\n";
	}
}
