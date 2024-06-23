package Pieces;
import Main.GamePanel;

public class Rook extends Piece {
    /** Constructors */
    public Rook(int color, int col, int row) {
        super(color, col, row);
        if (color == GamePanel.WHITE) {
            this._image = saveImage("C:/Users/dmcma/IntelliJ IDEA/Chess/res/piece/WR");
        } else {
            this._image = saveImage("C:/Users/dmcma/IntelliJ IDEA/Chess/res/piece/BR");
        }
    }

    // Methods
    /** Method to check if the piece can move to the target position */
    @Override
    public boolean canMove(int targetCol, int targetRow) {
        if (!isWithinBoard(targetCol, targetRow) && isTheSameCell(targetCol, targetRow)) return false;
        // The move is allowed if it's in the same column or row, it's a different cell and there's not a piece in the way
        if (targetCol == this._preCol || targetRow == this._preRow) {
            return isValidCell(targetCol, targetRow) && !pieceOnAStraightLine(targetCol, targetRow);
        }
        return false;
    }
}
