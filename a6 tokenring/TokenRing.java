import java.util.*;

public class TokenRing {

    static boolean[] alive;
    static int n;

    static void election(int start) {
        System.out.println("Start Election from " + start);

        int i = start;
        int max = start;

        do {
            if (alive[i]) {
                System.out.println("(" + start + " -> " + i + ")");
                if (i > max) max = i;
            }
            i = (i + 1) % n;
        } while (i != start);

        System.out.println("\nCoordinator (Ring Leader) = " + max);
    }

    static void showStatus() {
        System.out.print("Status: ");
        for (int i = 0; i < n; i++) {
            System.out.print(i + (alive[i] ? "(UP) " : "(DOWN) "));
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

        System.out.println("\n========= MENU =========");
        System.out.println("1. UP a process: activate a deactivated Process");
        System.out.println("2. DOWN a process: Simulate failure of a Process");
        System.out.println("3. ELECT leader: Starts Token Ring election");
        System.out.println("4. SHOW STATUS: Displays which processes are UP/DOWN");
        System.out.println("5. EXIT");

        do {
            System.out.print("\nEnter your choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.print("Process to UP (0-" + (n - 1) + "): ");
                    alive[sc.nextInt()] = true;
                    break;

                case 2:
                    System.out.print("Process to DOWN (0-" + (n - 1) + "): ");
                    alive[sc.nextInt()] = false;
                    break;

                case 3:
                    System.out.print("Start election from process (0-" + (n - 1) + "): ");
                    int p = sc.nextInt();

                    if (!alive[p]) {
                        System.out.println("This Process is DOWN");
                    } else {
                        election(p);
                    }
                    break;

                case 4:
                    showStatus();
                    break;

                case 5:
                    System.out.println("Exit");
                    break;

                default:
                    System.out.println("Invalid choice");
            }

        } while (choice != 5);
    }
}