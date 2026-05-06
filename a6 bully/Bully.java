//import java.util.*;
//
//public class Bully {
//
//    static boolean[] alive;
//    static int n;
//
//    static void election(int p) {
//        System.out.print("Start Election from " + p + " ");
//
//        for (int i = p + 1; i < n; i++) {
//            if (alive[i]) {
//                System.out.println("(" + p + " -> " + i + ")");
//                election(i);
//                return;
//            }
//        }
//
//        System.out.println("\nCoordinator (Bully Leader) = " + p);
//    }
//
//    static void showStatus() {
//        System.out.print("Status: ");
//        for (int i = 0; i < n; i++) {
//            System.out.print(i + (alive[i] ? "(UP) " : "(DOWN) "));
//        }
//        System.out.println();
//    }
//
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//
//        System.out.print("Enter total number of processes: ");
//        n = sc.nextInt();
//
//        alive = new boolean[n];
//        Arrays.fill(alive, true);
//
//        int choice;
//
//        System.out.println("\n========= MENU =========");
//        System.out.println("1. UP a process: activate a deactivated Process");
//        System.out.println("2. DOWN a process: Simulate failure (of a Process");
//        System.out.println("3. ELECT leader: Starts Bully election from a process");
//        System.out.println("4. SHOW STATUS: Displays which processes are UP/DOWN");
//        System.out.println("5. EXIT");
//
//        do {
//
//            System.out.print("\nEnter your choice: ");
//            choice = sc.nextInt();
//
//            switch (choice) {
//
//                case 1:
//                    System.out.print("Process to UP (0-" + (n - 1) + "): ");
//                    int x = sc.nextInt();
//                    alive[x] = true;
//                    break;
//
//                case 2:
//                    System.out.print("Process to DOWN (0-" + (n - 1) + "): ");
//                    alive[sc.nextInt()] = false;
//                    break;
//
//                case 3:
//                    System.out.print("Start election from process (0-" + (n - 1) + "): ");
//                    int p = sc.nextInt();
//
//                    if (!alive[p]) {
//                        System.out.println("This Process is DOWN");
//                    } else {
//                        election(p);
//                    }
//                    break;
//
//                case 4:
//                    showStatus();
//                    break;
//
//                case 5:
//                    System.out.println("Exit");
//                    break;
//
//                default:
//                    System.out.println("Invalid choice");
//            }
//
//        } while (choice != 5);
//    }
//}

import java.util.*;

public class Bully {

    static boolean[] alive;
    static int n;
    static int coordinator = -1;
    static int messageCount = 0;

    // Bully Election Algorithm
    static void election(int p) {
        System.out.println("\nProcess " + p + " initiates election");

        boolean higherExists = false;

        for (int i = p + 1; i < n; i++) {
            if (alive[i]) {
                // Send ELECTION message
                System.out.println("ELECTION message: " + p + " -> " + i);
                messageCount++;

                // Receive OK message
                System.out.println("OK message: " + i + " -> " + p);
                messageCount++;

                higherExists = true;

                // Higher process takes over election
                election(i);
                return;
            }
        }

        // No higher process alive → becomes coordinator
        coordinator = p;
        System.out.println("\nProcess " + p + " becomes COORDINATOR");

        // Send COORDINATOR message to all
        for (int i = 0; i < n; i++) {
            if (i != p && alive[i]) {
                System.out.println("COORDINATOR message: " + p + " -> " + i);
                messageCount++;
            }
        }
    }

    // Display system status
    static void showStatus() {
        System.out.print("Status: ");
        for (int i = 0; i < n; i++) {
            if (!alive[i]) {
                System.out.print(i + "(DOWN) ");
            } else if (i == coordinator) {
                System.out.print(i + "(COORDINATOR) ");
            } else {
                System.out.print(i + "(UP) ");
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter total number of processes: ");
        n = sc.nextInt();

        alive = new boolean[n];
        Arrays.fill(alive, true);

        int choice;

        do {
            System.out.println("\n========= MENU =========");
            System.out.println("1. UP a process");
            System.out.println("2. DOWN a process");
            System.out.println("3. ELECT leader");
            System.out.println("4. SHOW STATUS");
            System.out.println("5. EXIT");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.print("Process to UP (0-" + (n - 1) + "): ");
                    int up = sc.nextInt();
                    alive[up] = true;
                    System.out.println("Process " + up + " is now UP");
                    break;

                case 2:
                    System.out.print("Process to DOWN (0-" + (n - 1) + "): ");
                    int down = sc.nextInt();
                    alive[down] = false;

                    if (down == coordinator) {
                        coordinator = -1;
                        System.out.println("Coordinator failed! Re-election needed.");
                    }

                    System.out.println("Process " + down + " is now DOWN");
                    break;

                case 3:
                    System.out.print("Start election from process (0-" + (n - 1) + "): ");
                    int p = sc.nextInt();

                    if (!alive[p]) {
                        System.out.println("Process is DOWN. Cannot start election.");
                    } else {
                        messageCount = 0;
                        election(p);
                        System.out.println("\nTotal messages exchanged: " + messageCount);
                    }
                    break;

                case 4:
                    showStatus();
                    break;

                case 5:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice");
            }

        } while (choice != 5);

        sc.close();
    }
}