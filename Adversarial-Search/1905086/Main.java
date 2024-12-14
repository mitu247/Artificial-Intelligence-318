import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select game type - ");
        System.out.println("1. AI vs AI");
        System.out.println("2. Human vs AI");
        System.out.println("3. 100 Games between AI vs AI");
        System.out.print("Option: ");

        GameSimulation gameSimulation = new GameSimulation(true);
        Player player1 = new Player("player");
        Player player2 = new Player("computer");

        String response = scanner.nextLine();
        System.out.print("Specify iteration depth: ");
        int depth = scanner.nextInt();
        System.out.print("Specify heuristics (1) (2): ");
        int heuristic1 = scanner.nextInt();
        int heuristic2 = scanner.nextInt();

        gameSimulation.start(player1, player2, response, depth, heuristic1, heuristic2);

        System.out.println("Thank you for playing !");
    }
}
