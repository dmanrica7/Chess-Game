package Pieces;
import Main.GamePanel;

public class King extends Piece {
    /** Constructor */
    public King(int color, int col, int row) {
        super(color, col, row);
        if (color == GamePanel.WHITE) {
            this._image = saveImage("C:/Users/dmcma/IntelliJ IDEA/Chess/res/piece/WK");
        } else {
            this._image = saveImage("C:/Users/dmcma/IntelliJ IDEA/Chess/res/piece/BK");
        }
    }

    /** Methods */
    // Method to check if the piece can move to the target position
    @Override
    public boolean canMove(int targetCol, int targetRow) {
        if (!isWithinBoard(targetCol, targetRow) && isTheSameCell(targetCol, targetRow)) return false;
        // The move is allowed if it's just one cell in the vertical or horizontal axis
        if (!this._moved) {
            // Right castling
            if (targetCol == this._preCol + 2 && targetRow == this._preRow && !pieceOnAStraightLine(targetCol, targetRow)) {
                for (Piece piece : GamePanel.simPieces) {
                    if (piece.getCol() == this._preCol + 3 && piece.getRow() == this._preRow && !piece.getMoved()) {
                        GamePanel.castlingPiece = piece;
                        return true;
                    }
                }
            }
            // Left castling
            if (targetCol == this._preCol - 2 && targetRow == this._preRow && !pieceOnAStraightLine(targetCol, targetRow)) {
                Piece pieceMiddle = getPieceInCell(this._preCol - 3, this._preRow);
                if (pieceMiddle == null) {
                    for (Piece piece : GamePanel.simPieces) {
                        if (piece.getCol() == this._preCol - 4 && piece.getRow() == this._preRow && !piece.getMoved()) {
                            GamePanel.castlingPiece = piece;
                            return true;
                        }
                    }
                }
            }
        }
        boolean verticalOrHorizontal = Math.abs(targetCol - this._preCol) + Math.abs(targetRow - this._preRow) == 1;
        // The move is also allowed if it's just one cell in the diagonal axis
        boolean diagonal = Math.abs(targetCol - this._preCol)*Math.abs(targetRow - this._preRow) == 1;
        boolean validCell = isValidCell(targetCol, targetRow);
        return (verticalOrHorizontal || diagonal) && validCell;
    }
}
