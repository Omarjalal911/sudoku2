import java.util.Random;

public class SudokuGenerator {

    // المتغيّر لحساب عدد الحلول
    private static int solutionCount;

    public static SudokuCell[][] generateBoard(int difficultyLevel) {
        SudokuCell[][] board = new SudokuCell[9][9];

        // تهيئة الخلايا
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = new SudokuCell(0, false);
            }
        }

        // تعبئة اللوحة بالكامل
        fillBoard(board, 0, 0);

        // تثبيت كل القيم مبدئياً
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j].setFixed(true);
            }
        }

        // عدد الخلايا المراد إزالتها حسب الصعوبة
        int toRemove = switch (difficultyLevel) {
            case 1 -> 31;
            case 2 -> 41;
            case 3 -> 51;
            default -> 41;
        };

        removeCells(board, toRemove);

        // فحص إذا كان للّغز حل وحيد
        if (!hasUniqueSolution(board)) {
            // إعادة المحاولة إذا لم يكن فريدًا
            return generateBoard(difficultyLevel);
        }

        return board;
    }

    private static boolean fillBoard(SudokuCell[][] board, int row, int col) {
        if (row == 9) return true;
        if (col == 9) return fillBoard(board, row + 1, 0);

        int[] numbers = new Random().ints(1, 10).distinct().limit(9).toArray();

        for (int num : numbers) {
            if (isValid(board, row, col, num)) {
                board[row][col].setValue(num);
                if (fillBoard(board, row, col + 1)) return true;
                board[row][col].setValue(0); // تراجع
            }
        }
        return false;
    }

    private static void removeCells(SudokuCell[][] board, int countToRemove) {
        Random rand = new Random();
        int removed = 0;

        while (removed < countToRemove) {
            int row = rand.nextInt(9);
            int col = rand.nextInt(9);

            if (board[row][col].getValue() != 0) {
                board[row][col].setValue(0);
                board[row][col].setFixed(false);
                removed++;
            }
        }
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

    //  دوال فحص الحل الوحيد
    private static boolean hasUniqueSolution(SudokuCell[][] board) {
        solutionCount = 0;
        solve(board, 0, 0);
        return solutionCount == 1;
    }

    private static void solve(SudokuCell[][] board, int row, int col) {
        if (row == 9) {
            solutionCount++;
            return;
        }

        if (col == 9) {
            solve(board, row + 1, 0);
            return;
        }

        if (board[row][col].getValue() != 0) {
            solve(board, row, col + 1);
            return;
        }

        for (int num = 1; num <= 9; num++) {
            if (isValid(board, row, col, num)) {
                board[row][col].setValue(num);
                solve(board, row, col + 1);
                board[row][col].setValue(0);

                if (solutionCount > 1) return;
            }
        }
    }
}
