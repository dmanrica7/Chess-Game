package Pieces;
import Main.GamePanel;

public class Bishop extends Piece {
    /** Constructor */
    public Bishop(int color, int col, int row) {
        super(color, col, row);
        if (color == GamePanel.WHITE) {
            this._image = saveImage("C:/Users/dmcma/IntelliJ IDEA/Chess/res/piece/WB");
        } else {
            this._image = saveImage("C:/Users/dmcma/IntelliJ IDEA/Chess/res/piece/BB");
        }
    }

    // Methods
    /** Method to check if the piece can move to the target position */
    @Override
    public boolean canMove(int targetCol, int targetRow) {
        if (!isWithinBoard(targetCol, targetRow) && isTheSameCell(targetCol, targetRow)) return false;
        // The move is allowed if it's in the same diagonal, it's a different cell and there's not a piece in the way
        if (Math.abs(targetCol - this._preCol) == Math.abs(targetRow - this._preRow)) {
            return isValidCell(targetCol, targetRow) && !pieceOnDiagonalLine(targetCol, targetRow);
        }
        return false;
    }
}
