import java.io.*;
import java.util.*;
public class Driver extends PCB {

    static Scanner input = new Scanner(System.in);
	static List<PCB> Q1 = new ArrayList<>();
	static List<PCB> Q2 = new ArrayList<>();
	static List<PCB> gantChart = new ArrayList<>();
	static int processID = 0;

    public static void main(String[] args) {

        int choice;

        do {
            // Display menu options
            System.out.println("This program simulates how a CPU schedules tasks.");
            System.out.println("1. Enter process information.");
            System.out.println("2. Report detailed process information.");
            System.out.println("3. Exit program.");
            System.out.print("Enter your choice: ");

            // Get user input and handle exceptions
            try {
                choice = input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number (1-3).");
                input.next(); // Clear the invalid input
                choice = 0; // Reset choice to avoid infinite loop
            }

            // Perform actions based on choice
            switch (choice) {
                case 1:
                processInfo();
                    break;
                case 2:
                //ReportDetailedInform();
                    break;
                case 3:
                    System.out.println("Goodbye.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 3);

        input.close();
    }

    public static void processInfo() {

        int num=0;

do {
  System.out.print("\nEnter the number of processes (positive integer): ");
  try {
    num = input.nextInt();
    if (num < 0) {
      throw new IllegalArgumentException("Number of processes cannot be negative.");
    }
  } catch (InputMismatchException e) {
    System.out.println("Only integers allowed. Please enter again.");
    input.next(); // Clear the invalid input
  } catch (IllegalArgumentException e) {
    System.out.println(e.getMessage());
  }
} while (num <= 0); // Repeat until a valid positive number is entered

for (int i = 0; i < num; i++) {
    System.out.println("\nEnter process" + (processID + 1) + " information:");
  
    boolean validInput = false;
    int priority = 0;
    int arrivalTime = 0;
    int cpuBurst = 0;
  
    do {
      try {
        // Priority (with clear options)
        System.out.print("Enter process" + (processID + 1) + " priority: ");
        priority = input.nextInt();
        if (priority != 1 && priority != 2) {
          System.out.println("Invalid priority. Please enter 1 or 2.");
          //priority = input.nextInt();
        } else {
          validInput = true; // Priority is valid
        }
  
        // Arrival Time (non-negative)
        System.out.print("Enter process" + (processID + 1) + " arrival time (non-negative integer): ");
        arrivalTime = input.nextInt();
        if (arrivalTime < 0) {
          System.out.println("Arrival time cannot be negative. Please enter a non-negative integer.");
          //arrivalTime = input.nextInt();
          validInput = false; // Reset validInput if arrival time is invalid
        }
  
        // CPU Burst (non-negative)
        System.out.print("Enter process" + (processID + 1) + " CPU burst (non-negative integer): ");
        cpuBurst = input.nextInt();
        if (cpuBurst < 0) {
          System.out.println("CPU burst cannot be negative. Please enter a non-negative integer.");
          //cpuBurst = input.nextInt();
          validInput = false; // Reset validInput if CPU burst is invalid
        }
      } catch (InputMismatchException e) {
        System.out.println("Some fields have input mismatch! Only integers allowed. Please enter again.");
        input.next(); // Clear the invalid input
        validInput = false; // Reset validInput on mismatch
      }
    } while (!validInput); // Repeat until all inputs are valid
  
    // Create and add process object if all inputs are valid
    PCB process = new PCB("P" + ++processID, priority, arrivalTime, cpuBurst);
    if (priority == 1) {
      Q1.add(process);
    } else if (priority == 2) {
      Q2.add(process);
    }
  }
  System.out.println();



    }//end processinfo
 public static void RounRobin(StringBuilder sb, PCB b) {
      PCB currentProcess = Q1.remove(0);
      sb.append(currentProcess.getProcessID()).append(" | ");
  
      if (currentProcess.getStartTime() == -1) {
          currentProcess.setStartTime(b.currentTime);
      }
  
      if (currentProcess.getCopyCPUpuBurst() > 3) {
          b.currentTime += 3;
          currentProcess.setCopyArrivalTime(b.currentTime);
          currentProcess.setCopyCPUpuBurst(currentProcess.getCopyCPUpuBurst() - 3);
          Q1.add(currentProcess);
          Collections.sort(Q1, Comparator.comparingInt(process -> process.getCopyArrivalTime()));
      } else {
          b.currentTime += currentProcess.getCopyCPUpuBurst();
          currentProcess.setTerminationTime(b.currentTime);
          currentProcess.setTurnaroundTime(currentProcess.getTerminationTime() - currentProcess.getArrivalTime());
          currentProcess.setWaitingTime(currentProcess.getTurnaroundTime() - currentProcess.getCpuBurst());
          currentProcess.setResponseTime(currentProcess.getStartTime() - currentProcess.getArrivalTime());
          gantChart.add(currentProcess);
      }
  }
  public static void sjfWithPreemptive(StringBuilder stringBuilder, PCB process, PCB currentProcess) {
    // If the current process is terminated, select the first process in Q2 Otherwise, select the process in Q2 with the shortest burst time that has arrived
     process =  (currentProcess.terminated == true) ?  Q2.get(0) : Q2.stream()
     .filter(p -> p.getArrivalTime() <= currentProcess.currentTime)
     .min(Comparator.comparingInt(p -> p.getCopyCPUpuBurst()))
     .orElse(null);
     
     
    // If a process was selected
    if (process != null) {
     // Remove the selected process from Q2
     Q2.remove(process);
     // Add the process ID to the schedule
     stringBuilder.append(process.getProcessID()).append(" | ");
     
     // If the process hasn't started yet, set its start time
     if (process.getStartTime() == -1) 
      process.setStartTime(currentProcess.currentTime);
    
     
     // Calculate the response time of the process
     process.setResponseTime(process.getStartTime() - process.getArrivalTime());
     
     // While there are no new arrivals and the process still has burst time
     while ((Q1.isEmpty() || Q1.get(0).getArrivalTime() > currentProcess.currentTime) && process.getCopyCPUpuBurst() > 0) {
      // Decrement the burst time of the process and increment the current time
      process.setCopyCPUpuBurst(process.getCopyCPUpuBurst() - 1);
      currentProcess.currentTime++;
     }
     
     // If the process still has burst time
     if (process.getCopyCPUpuBurst() > 0) {
      // The current process is not terminated and the selected process is added back to Q2
      currentProcess.terminated = false;
      Q2.add(0, process);
     } else {
      // Otherwise, the current process is terminated
      currentProcess.terminated = true;
      // Set the termination time of the process
      process.setTerminationTime(currentProcess.currentTime);
      // Calculate the turnaround time and waiting time of the process
      process.setTurnaroundTime(process.getTerminationTime() - process.getArrivalTime());
      process.setWaitingTime(process.getTurnaroundTime() - process.getCpuBurst());
      // Add the process to the gantt chart
      gantChart.add(process);
     }
    }
    // End of the sjfWithPreemptive method
   }
  public static StringBuilder printProcessInfo(StringBuilder sb) {
    // Sort processes by process ID
    gantChart.sort(Comparator.comparing(PCB::getProcessID));


    // Print info for each process
    for (PCB process : gantChart) {
        sb.append(process.toString());
    }

    // Calculate average time
    double averageTurnaroundTime = 0;
    double averageWaitingTime = 0;
    double averageResponseTime = 0;

    if (!gantChart.isEmpty()) {
        int sumTurnaround = 0;
        int sumWaiting = 0;
        int sumResponse = 0;

        for (PCB process : gantChart) {
            sumTurnaround += process.getTurnaroundTime();
            sumWaiting += process.getWaitingTime();
            sumResponse += process.getResponseTime();
        }

        // Calculate averages
        averageTurnaroundTime = (double) sumTurnaround / gantChart.size();
        averageWaitingTime = (double) sumWaiting / gantChart.size();
        averageResponseTime = (double) sumResponse / gantChart.size();
    }

    // Append average info to StringBuilder
    sb.append("Average[ Average Turnaround Time: ").append(averageTurnaroundTime)
            .append(", Average Waiting Time: ").append(averageWaitingTime)
            .append(", Average Response Time: ").append(averageResponseTime).append(" ]\n");

    // Print output
    System.out.println(sb.toString());
    return sb;
}

  










    }// end class
    

