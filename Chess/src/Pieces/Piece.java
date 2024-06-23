package Pieces;
import Main.Board;
import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Piece {
    /** Class attributes */
    protected BufferedImage _image; // Image to show in the board
    protected int _x, _y; // Coordinates in the screen window
    protected int _row, _col, _preRow, _preCol; // Coordinates in the board
    protected int _color; // Color of the piece
    protected Piece _hitPiece;
    protected boolean _moved;

    /** Constructor */
    public Piece(int color, int col, int row) {
        this._color = color;
        this._col = col;
        this._row = row;
        this._x = getX(col);
        this._y = getY(row);
        this._preCol = col;
        this._preRow = row;
        this._moved = false;
    }

    /** Getters */
    public BufferedImage getImage() {
        return this._image;
    }
    public int getColor() {
        return this._color;
    }

    public int getRow() {
        return this._row;
    }

    public int getCol() {
        return this._col;
    }

    public int getPreRow() {
        return this._preRow;
    }

    public int getPreCol() {
        return this._preCol;
    }

    public int getX(int col) {
        return col * Board.SQUARE_SIZE;
    }

    public int getY(int row) {
        return row * Board.SQUARE_SIZE;
    }

    public Piece getHitPiece() {
        return _hitPiece;
    }

    public boolean getMoved() {
        return this._moved;
    }

    /** Setters */
    public void setRow(int row) {
        this._row = row;
    }

    public void setCol(int col) {
        this._col = col;
    }

    public void setPreRow(int preRow) {
        this._preRow = preRow;
    }

    public void setPreCol(int preCol) {
        this._preCol = preCol;
    }

    public void setColor(int color) {
        this._color = color;
    }

    public void setX(int x) {
        this._x = x;
    }

    public void setY(int y) {
        this._y = y;
    }

    public void setMoved(boolean moved) {
        this._moved = moved;
    }

    // Methods
    /** Method that gets the corresponding piece image from a path */
    public BufferedImage saveImage(String imagePath) {
        BufferedImage image = null;
        try {
            File file = new File(imagePath + ".png");
            image = ImageIO.read(file);
        } catch (Exception ex) {
            System.out.println("There's a problem with the image " + imagePath);
            System.out.println("ERROR: " + ex.getMessage());
        }
        return image;
    }

    /** Method that retrieves the board column where the piece is located */
    public int getBoardCol() {
        return (this._x + Board.HALF_SQUARE_SIZE)/Board.SQUARE_SIZE;
    }

    /** Method that retrieves the board row where the piece is located */
    public int getBoardRow() {
        return (this._y + Board.HALF_SQUARE_SIZE)/Board.SQUARE_SIZE;
    }

    /** Method that update the position of the piece */
    public void updatePosition() {
        this._x = getX(this._col);
        this._y = getY(this._row);
        this._preCol = getBoardCol();
        this._preRow = getBoardRow();
    }

    /** Method to reset the position of the piece to the position it was before selecting it */
    public void resetPosition() {
        this._col = this._preCol;
        this._row = this._preRow;
        this._x = getX(this._col);
        this._y = getY(this._row);
    }

    /** Method to check if the piece can move to the target position, it will be implemented in each piece */
    public boolean canMove(int targetCol, int targetRow) {
        return false;
    }

    /** Method to check if the target cell is within the board */
    public boolean isWithinBoard(int targetCol, int targetRow) {
        return targetRow >= 0 && targetRow < 8 && targetCol >= 0 && targetCol < 8;
    }

    /** Method that returns the piece that is located in the target cell, returns null if there isn't a piece there */
    public Piece getPieceInCell(int targetCol, int targetRow) {
        for (Piece piece : GamePanel.simPieces) {
            if (piece.getCol() == targetCol && piece.getRow() == targetRow && piece != this) return piece;
        }
        return null;
    }

    /** Method that returns the index of the piece in the board's pieces list. Returns -1 if isn't in the list */
    public int getIndex() {
        for (int i=0; i<GamePanel.simPieces.size(); i++) {
            if (GamePanel.simPieces.get(i) == this) return i;
        }
        return -1;
    }

    /** Method that returns true if the target cell is valid and false otherwise */
    public boolean isValidCell(int targetCol, int targetRow) {
        this._hitPiece = getPieceInCell(targetCol, targetRow);
        if (this._hitPiece == null) return true; // The cell is empty
        if (this._hitPiece.getColor() != this._color) {
            return true; // The cell is occupied by an opposite color piece
        } else {
            this._hitPiece = null;
            return false;
        }
    }

    /** This method checks whether the target cell is the same or not */
    public boolean isTheSameCell(int targetCol, int targetRow) {
        return targetCol == this._preCol && targetRow == this._preRow;
    }

    /** Method to check if there's a piece in the way when we move diagonally */
    public boolean pieceOnDiagonalLine(int targetCol, int targetRow) {
        // When the piece is moving up-left
        for (int c=this._preCol-1, r=this._preRow-1; c>targetCol && r>targetRow; c--, r--) {
            for (Piece piece : GamePanel.simPieces) {
                if (piece.getCol() == c && piece.getRow() == r) {
                    return true;
                }
            }
        }
        // When the piece is moving up-right
        for (int c=this._preCol+1, r=this._preRow-1; c<targetCol && r>targetRow; c++, r--) {
            for (Piece piece : GamePanel.simPieces) {
                if (piece.getCol() == c && piece.getRow() == r) {
                    return true;
                }
            }
        }
        // When the piece is moving down-right
        for (int c=this._preCol+1, r=this._preRow+1; c<targetCol && r<targetRow; c++, r++) {
            for (Piece piece : GamePanel.simPieces) {
                if (piece.getCol() == c && piece.getRow() == r) {
                    return true;
                }
            }
        }
        // When the piece is moving down-left
        for (int c=this._preCol-1, r=this._preRow+1; c>targetCol && r<targetRow; c--, r++) {
            for (Piece piece : GamePanel.simPieces) {
                if (piece.getCol() == c && piece.getRow() == r) {
                    return true;
                }
            }
        }
        return false;
    }

    /** Method to check if there's a piece in the way when we move in a straight line */
    public boolean pieceOnAStraightLine(int targetCol, int targetRow) {
        // When the piece is moving to the left
        for (int i=this._preCol-1; i>targetCol; i--) {
            for (Piece piece : GamePanel.simPieces) {
                if (piece.getCol() == i && piece.getRow() == targetRow) {
                    return true;
                }
            }
        }
        // When the piece is moving to the right
        for (int i=this._preCol+1; i<targetCol; i++) {
            for (Piece piece : GamePanel.simPieces) {
                if (piece.getCol() == i && piece.getRow() == targetRow) {
                    return true;
                }
            }
        }
        // When the piece is moving up
        for (int i=this._preRow-1; i>targetRow; i--) {
            for (Piece piece : GamePanel.simPieces) {
                if (piece.getCol() == targetCol && piece.getRow() == i) {
                    return true;
                }
            }
        }
        // When the piece is moving down
        for (int i=this._preRow+1; i<targetRow; i++) {
            for (Piece piece : GamePanel.simPieces) {
                if (piece.getCol() == targetCol && piece.getRow() == i) {
                    return true;
                }
            }
        }
        return false;
    }

    /** Method to print the image related to the piece */
    public void draw(Graphics2D g) {
        g.drawImage(this._image, this._x, this._y, Board.SQUARE_SIZE, Board.SQUARE_SIZE, null);
    }
}
