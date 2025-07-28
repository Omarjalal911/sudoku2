import java.util.Scanner;

public class SudokuBoard {
    private SudokuCell[][] board;

    public SudokuBoard(int difficultyLevel) {
        this.board = SudokuGenerator.generateBoard(difficultyLevel);
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);

        int mistakeCount = 0;
        long startTime = System.currentTimeMillis();

        System.out.println(" Let's start the game ");
        printBoard();  //  طباعة أولية

        while (true) {
            int row;
            while (true) {
                System.out.print("Enter row (0-8) or -1 to quit: ");
                row = scanner.nextInt();
                if (row == -1) {
                    System.out.println("Game exited.");
                    return;
                }
                if (row >= 0 && row < 9) break;
                System.out.println("Invalid row. Please enter a number between 0 and 8.");
            }

            int col;
            while (true) {
                System.out.print("Enter column (0-8): ");
                col = scanner.nextInt();
                if (col >= 0 && col < 9) break;
                System.out.println("Invalid column. Please enter a number between 0 and 8.");
            }

            if (board[row][col].isFixed()) {
                System.out.println(" This cell is fixed and cannot be changed.");
                continue;
            }

            int num;
            while (true) {
                System.out.print("Enter number (1-9): ");
                num = scanner.nextInt();
                if (num >= 1 && num <= 9) break;
                System.out.println("Invalid number. Please enter a number between 1 and 9.");
            }

            if (placeNumber(row, col, num)) {
                System.out.println(" Number placed at (" + row + ", " + col + ")");
                printBoard();  //  إعادة طباعة اللوحة بعد كل إدخال صحيح
            } else {
                System.out.println(" Invalid number at this position.");
                mistakeCount++;
                System.out.println(" Mistakes: " + mistakeCount);
            }

            if (isFull()) {
                System.out.println(" Congratulations! You completed the board.");
                printBoard();
                break;
            }
        }

        long endTime = System.currentTimeMillis();
        long elapsedSeconds = (endTime - startTime) / 1000;

        System.out.println(" Time spent: " + formatTime(elapsedSeconds));
        System.out.println(" Total mistakes: " + mistakeCount);
        System.out.println(" Game exited.");
    }

    public boolean placeNumber(int row, int col, int num) {
        if (row < 0 || row >= 9 || col < 0 || col >= 9 || num < 1 || num > 9)
            return false;

        if (board[row][col].isFixed())
            return false;

        if (!isValid(row, col, num))
            return false;

        board[row][col].setValue(num);
        return true;
    }

    public boolean isValid(int row, int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (board[row][i].getValue() == num || board[i][col].getValue() == num)
                return false;
        }

        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[startRow + i][startCol + j].getValue() == num)
                    return false;
            }
        }

        return true;
    }

    public boolean isFull() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j].getValue() == 0)
                    return false;
            }
        }
        return true;
    }

    public void printBoard() {
        System.out.println("\n   Sudoku Board:");
        System.out.print("     ");
        for (int col = 0; col < 9; col++) {
            System.out.print(col + " ");
            if ((col + 1) % 3 == 0 && col != 8) System.out.print("  ");
        }
        System.out.println();
        System.out.println("   -------------------------");

        for (int i = 0; i < 9; i++) {
            System.out.print(" " + i + " | ");
            for (int j = 0; j < 9; j++) {
                int value = board[i][j].getValue();
                System.out.print((value == 0 ? "." : value) + " ");
                if ((j + 1) % 3 == 0 && j != 8) System.out.print("| ");
            }
            System.out.println("|");
            if ((i + 1) % 3 == 0 && i != 8)
                System.out.println("   -------------------------");
        }
        System.out.println("   -------------------------");
    }

    private String formatTime(long seconds) {
        long mins = seconds / 60;
        long secs = seconds % 60;
        return String.format("%02d:%02d", mins, secs);
    }
}
