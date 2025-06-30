import java.util.Random;

public class SudokuGenerator {

    public static SudokuCell[][] generateBoard(int difficultyLevel) {
        SudokuCell[][] board = new SudokuCell[9][9];

        // تعبئة الخلايا بصفر (قيمة افتراضية)
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = new SudokuCell(0, false);
            }
        }

        int cellsToFill;

        // تحديد عدد الخلايا حسب الصعوبة
        switch (  difficultyLevel) {
             case 1: cellsToFill = 50; break; // Easy
            case 2: cellsToFill = 30; break; // Medium
            case 3: cellsToFill = 20; break; // Hard
            default: cellsToFill = 30;
        }

        Random rand = new Random();
        int filled = 0;

        while (filled < cellsToFill) {
            int row = rand.nextInt(9);
            int col = rand.nextInt(9);
            int num = rand.nextInt(9) + 1;

            if (board[row][col].getValue() == 0 && isValid(board, row, col, num)) {
                board[row][col] = new SudokuCell(num, true); // ثابتة
                filled++;
            }
        }

        return board;
    }

    public static boolean isValid(SudokuCell[][] board, int row, int col, int num) {
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
}
