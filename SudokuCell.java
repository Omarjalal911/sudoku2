public class SudokuCell {
    private int value;
    private boolean fixed;

    public SudokuCell() {
        this.value = 0;
        this.fixed = false;
    }

    public SudokuCell(int value, boolean fixed) {
        this.value = value;
        this.fixed = fixed;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        if (!fixed)
            this.value = value;
    }

    public boolean isFixed() {
        return fixed;
    }
}
