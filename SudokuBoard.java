import java.util.Scanner;

public class SudokuBoard {
    private SudokuCell[][] board;

    public SudokuBoard(int difficultyLevel) {
        this.board = SudokuGenerator.generateBoard(difficultyLevel);
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("🎮 Let's start the game 🎮");
        printBoard();  // ✅ طباعة أولية

        while (true) {
            System.out.print("Enter row (0-8) or -1 to quit: ");
            int row = scanner.nextInt();
            if (row == -1) break;

            System.out.print("Enter column (0-8): ");
            int col = scanner.nextInt();

            if (board[row][col].isFixed()) {
                System.out.println("❌ This cell is fixed and cannot be changed.");
                continue;
            }

            System.out.print("Enter number (1-9): ");
            int num = scanner.nextInt();

            if (placeNumber(row, col, num)) {
                System.out.println("✅ Number placed at (" + row + ", " + col + ")");
                printBoard();  // ✅ إعادة طباعة اللوحة بعد كل إدخال صحيح
            } else {
                System.out.println("❌ Invalid number at this position.");
            }

            if (isFull()) {
                System.out.println("🎉 Congratulations! You completed the board.");
                printBoard();
                break;
            }
        }

        System.out.println("👋 Game exited.");
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

    public int getValue(int row, int col) {
        return board[row][col].getValue();
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
        System.out.println("\n Sudoku Board:");
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0) System.out.println("-------------------------");
            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0) System.out.print("| ");
                int value = board[i][j].getValue();
                System.out.print((value == 0 ? "." : value) + " ");
            }
            System.out.println("|");
        }
        System.out.println("-------------------------");
    }

    public SudokuCell[][] getBoard() {
        return board;
    }
}
