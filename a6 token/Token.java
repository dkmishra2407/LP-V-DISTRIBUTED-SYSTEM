import java.util.*;

public class Bully {

    static boolean[] alive;
    static int n;
    static int coordinator = -1;
    static int messageCount = 0;

    // Ring Election Algorithm
    static void election(int p) {

        System.out.println("\nProcess " + p + " initiates election");

        int current = p;
        coordinator = p;

        do {

            int next = (current + 1) % n;

            // Skip dead processes
            while (!alive[next]) {
                next = (next + 1) % n;
            }

            System.out.println(current + " -> " + next);
            messageCount++;

            // Highest process ID becomes coordinator
            if (next > coordinator) {
                coordinator = next;
            }

            current = next;

        } while (current != p);

        System.out.println("\nProcess " + coordinator + " becomes COORDINATOR");

        // Send coordinator message
        for (int i = 0; i < n; i++) {

            if (alive[i] && i != coordinator) {

                System.out.println(
                        "COORDINATOR message: " +
                                coordinator + " -> " + i
                );

                messageCount++;
            }
        }
    }

    // Display status
    static void showStatus() {

        System.out.print("\nStatus: ");

        for (int i = 0; i < n; i++) {

            if (!alive[i]) {
                System.out.print(i + "(DOWN) ");
            }
            else if (i == coordinator) {
                System.out.print(i + "(COORDINATOR) ");
            }
            else {
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

        coordinator = n - 1;

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

                    if (up >= 0 && up < n) {

                        alive[up] = true;
                        System.out.println("Process " + up + " is now UP");

                    } else {
                        System.out.println("Invalid process number");
                    }

                    break;

                case 2:

                    System.out.print("Process to DOWN (0-" + (n - 1) + "): ");
                    int down = sc.nextInt();

                    if (down >= 0 && down < n) {

                        alive[down] = false;

                        if (down == coordinator) {
                            coordinator = -1;
                            System.out.println("Coordinator failed! Re-election needed.");
                        }

                        System.out.println("Process " + down + " is now DOWN");

                    } else {
                        System.out.println("Invalid process number");
                    }

                    break;

                case 3:

                    System.out.print("Start election from process (0-" + (n - 1) + "): ");
                    int p = sc.nextInt();

                    if (p < 0 || p >= n) {

                        System.out.println("Invalid process number");

                    }
                    else if (!alive[p]) {

                        System.out.println("Process is DOWN. Cannot start election.");

                    }
                    else {

                        messageCount = 0;

                        election(p);

                        System.out.println(
                                "\nTotal messages exchanged: " + messageCount
                        );
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