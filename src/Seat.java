public class Seat {
    private final String code;
    private final int row;
    private final int column;
    private boolean isAvailable;

    public Seat(int row, int column) {
        this.code = row + "-" + column;
        this.row = row;
        this.column = column;
        this.isAvailable = true;
    }
    public String getCode() {
        return code;
    }
    public int getRow() {
        return row;
    }
    public int getColumn() {
        return column;
    }
    public boolean isAvailable() {
        return isAvailable;
    }
    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
