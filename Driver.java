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











    }// end class
    

