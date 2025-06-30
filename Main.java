import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("🔁 Do you want to start a new game?");
            System.out.print("Choose difficulty level (easy / medium / hard) or type 'exit' to quit: ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("exit")) {
                System.out.println("👋 Thanks for playing!");
                break;
            }

            int level;
            switch (input) {
                case  "easy": level = 1; break;
                case "medium": level = 2; break;
                case "hard": level = 3; break;
                default:
                    System.out.println("⚠️ Unknown difficulty. Defaulting to medium.");
                    level = 2;
            }

            SudokuBoard game = new SudokuBoard(level);
            game.play();  // after exiting the game, loop repeats
        }
    }
}
