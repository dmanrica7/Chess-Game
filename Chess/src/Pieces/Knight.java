package Pieces;
import Main.GamePanel;

public class Knight extends Piece {
    /** Constructor */
    public Knight(int color, int col, int row) {
        super(color, col, row);
        if (color == GamePanel.WHITE) {
            this._image = saveImage("C:/Users/dmcma/IntelliJ IDEA/Chess/res/piece/WKn");
        } else {
            this._image = saveImage("C:/Users/dmcma/IntelliJ IDEA/Chess/res/piece/BKn");
        }
    }

    /** Methods */
    // Method to check if the piece can move to the target position
    @Override
    public boolean canMove(int targetCol, int targetRow) {
        if (!isWithinBoard(targetCol, targetRow) && isTheSameCell(targetCol, targetRow)) return false;
        // The move is allowed if it's two cells to the right or left and one cell above or below
        // The move is also allowed if it's two cells above or below and one cell to the right or left
        boolean move = Math.abs(targetCol - this._preCol)*Math.abs(targetRow - this._preRow) == 2;
        boolean validCell = isValidCell(targetCol, targetRow);
        return move && validCell;
    }
}
