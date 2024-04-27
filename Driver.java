import java.io.*;
import java.util.*;
public class Driver extends PCB {

  static Scanner input = new Scanner(System.in);
  static List<PCB> processChart = new ArrayList<>();
	static List<PCB> Q1 = new ArrayList<>();
	static List<PCB> Q2 = new ArrayList<>();
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
                System.out.println(" Please enter a  valid number .");
                input.next(); // Clear the invalid input
                choice = 0; // Reset choice to avoid infinite loop
            }

            // Perform actions based on choice
            switch (choice) {
                case 1:
                infoOfProcess();
                    break;
                case 2:
                reportDetailedInformation();
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

    public static void infoOfProcess() {

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

    public static void RounRobin(StringBuilder n, PCB y) {
      PCB currentProcess = Q1.remove(0);
      n.append(currentProcess.getProcessID() + " | ");
      if (currentProcess.getStartTime() == -1) {
        currentProcess.setStartTime(y.currentTime);
      }
      if (currentProcess.getCopyCPUpuBurst() > 3) {
        y.currentTime += 3;
        currentProcess.setCopyArrivalTime(y.currentTime);
        currentProcess.setCopyCPUpuBurst(currentProcess.getCopyCPUpuBurst() - 3);
        Q1.add(currentProcess);
        Collections.sort(Q1, Comparator.comparingInt(process -> process.getCopyArrivalTime()));
      } else {
        y.currentTime += currentProcess.getCopyCPUpuBurst();
        currentProcess.setTerminationTime(y.currentTime);
        currentProcess.setTurnaroundTime(currentProcess.getTerminationTime() - currentProcess.getArrivalTime());
        currentProcess.setWaitingTime(currentProcess.getTurnaroundTime() - currentProcess.getCpuBurst());
        currentProcess.setResponseTime(currentProcess.getStartTime() - currentProcess.getArrivalTime());
        processChart.add(currentProcess);
      }
    }





    public static void sjf(StringBuilder TheOrder, PCB ProcessSJF, PCB e) {
      // Find the process with the shortest remaining CPU burst time among waiting processes in Q2
      ProcessSJF = null;
      for (PCB process : Q2) {
        if (process.getArrivalTime() <= e.currentTime && 
            (ProcessSJF == null || process.getCopyCPUpuBurst() <ProcessSJF.getCopyCPUpuBurst())) {
              ProcessSJF = process;
        }
      }
    
      // If a shortest job is found, execute it using SJF logic
      if (ProcessSJF != null) {
        TheOrder.append(ProcessSJF.getProcessID() + " | ");
        if (ProcessSJF.getStartTime() == -1) {
          ProcessSJF.setStartTime(e.currentTime);
        }
        
        // Execute the process until completion or time slice ends
        if (ProcessSJF.getCopyCPUpuBurst() <= 3) {
          e.currentTime += ProcessSJF.getCopyCPUpuBurst();
          ProcessSJF.setCopyCPUpuBurst(0);
          ProcessSJF.setTerminationTime(e.currentTime);
          ProcessSJF.setTurnaroundTime(ProcessSJF.getTerminationTime() - ProcessSJF.getArrivalTime());
          ProcessSJF.setWaitingTime(ProcessSJF.getTurnaroundTime() - ProcessSJF.getCpuBurst());
          ProcessSJF.setResponseTime(ProcessSJF.getStartTime() - ProcessSJF.getArrivalTime());
          processChart.add(ProcessSJF);
          Q2.remove(ProcessSJF); // Remove completed process from Q2
        } else {
          ProcessSJF.setCopyCPUpuBurst(ProcessSJF.getCopyCPUpuBurst() - 3);
          e.currentTime += 3;
        }
      } else {
        // No process found for SJF, mark idle time
        TheOrder.append("idle | ");
        e.currentTime++;
      }
    }
    public static StringBuilder printProcessInfo(StringBuilder Stringy) {
      // Sort processes by process ID
      processChart.sort(Comparator.comparing(PCB::getProcessID));
    
      // Print info for each process
      for (PCB process : processChart) {
        Stringy.append(process.toString());
      }
    
      // Calculate average time if there are processes
      if (!processChart.isEmpty()) {
        double totalProcesses = processChart.size();
        double sumTurnaround = processChart.stream()
            .mapToDouble(PCB::getTurnaroundTime)
            .sum();
        double sumWaiting = processChart.stream()
            .mapToDouble(PCB::getWaitingTime)
            .sum();
        double sumResponse = processChart.stream()
            .mapToDouble(PCB::getResponseTime)
            .sum();
    
        double averageTurnaroundTime =(double) sumTurnaround / totalProcesses;
        double averageWaitingTime = (double)sumWaiting / totalProcesses;
        double averageResponseTime = (double)sumResponse / totalProcesses;
        Stringy.append("Average[ Average Turnaround Time: ")
          .append(averageTurnaroundTime)
          .append(", Average Waiting Time: ")
          .append(averageWaitingTime)
          .append(", Average Response Time: ")
          .append(averageResponseTime)
          .append(" ]\n");
      } else {
        // Handle empty list case (optional: set averages to 0 or default values)
      }
      System.out.println(Stringy.toString());
      return Stringy;
    }
    

  
  public static void reportDetailedInformation() {
  if (Q1.isEmpty() && Q2.isEmpty()) {
      System.out.println("\nThere are no processes yet!");
  } else {
      // Sort Q1 & Q2 by arrival time to ensure they are in the appropriate order
      Collections.sort(Q1, Comparator.comparingInt(process -> process.getArrivalTime()));
      Collections.sort(Q2, Comparator.comparingInt(process -> process.getArrivalTime()));

      PCB sjfProcess = null;
      StringBuilder schedulingOrder = new StringBuilder();
      schedulingOrder.append("\nScheduling Order: [ ");
      PCB b = new PCB();

      while (!Q1.isEmpty() || !Q2.isEmpty()) {
          if (!Q1.isEmpty() && Q1.get(0).getArrivalTime() <= b.currentTime) {
            RounRobin(schedulingOrder, b);
          } else if (!Q2.isEmpty() && Q2.get(0).getArrivalTime() <= b.currentTime) {
              sjf(schedulingOrder, sjfProcess, b);
          } else {
              schedulingOrder.append("idle | ");
              b.currentTime++;
          }
      }

      // Remove the last '|' character if present
      int lastIndex = schedulingOrder.length() - 2;
      if (lastIndex >= 0 && schedulingOrder.charAt(lastIndex) == '|') {
          schedulingOrder.deleteCharAt(lastIndex);
      }
      schedulingOrder.append("]\n");

      // Print process info and write details to file
      printProcessInfo(schedulingOrder);
      writeDetailsToFile(schedulingOrder);
  }
}  

public static void writeDetailsToFile(StringBuilder Detailes) {
  try (PrintWriter printWriter = new PrintWriter(new FileWriter("Report.txt"))) {
      printWriter.println(Detailes);
  } catch (IOException e) {
      e.printStackTrace();
      // Handle file writing exception here
  }
}










    }// end class
    

