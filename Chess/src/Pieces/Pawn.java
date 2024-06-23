package Pieces;
import Main.GamePanel;

public class Pawn extends Piece {
    // Constructor
    public Pawn(int color, int col, int row) {
        super(color, col, row);
        if (color == GamePanel.WHITE) {
            this._image = saveImage("C:/Users/dmcma/IntelliJ IDEA/Chess/res/piece/WP");
        } else {
            this._image = saveImage("C:/Users/dmcma/IntelliJ IDEA/Chess/res/piece/BP");
        }
    }

    /** Methods */
    // Method to check if the piece can move to the target position
    @Override
    public boolean canMove(int targetCol, int targetRow) {
        if (!isWithinBoard(targetCol, targetRow) && isTheSameCell(targetCol, targetRow)) return false;
        int mov = 1;
        if (this._color == GamePanel.WHITE) mov = -1;
        if (targetCol == this._preCol) {
            if (targetRow == this._preRow + mov) {
                Piece hPiece = getPieceInCell(targetCol, targetRow);
                return hPiece == null;
            }
            if (!this._moved && targetRow == this._preRow + (mov*2)) {
                return !pieceOnAStraightLine(targetCol, targetRow);
            }
        }
        if (Math.abs(targetCol - this._preCol) == 1 && targetRow == this._preRow + mov) {
            Piece hPiece = getPieceInCell(targetCol, targetRow);
            if (hPiece != null && hPiece.getColor() != this._color) {
                this._hitPiece = hPiece;
                return true;
            }
        }
        return false;
    }
}
