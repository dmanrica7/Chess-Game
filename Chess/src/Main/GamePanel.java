package Main;
import Pieces.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {
    // Class attributes
    // Constants
    /** Pixel width of the window panel */
    public static final int WIDTH = 800;
    /** Pixel height of the window panel */
    public static final int HEIGHT = 400;
    /** Integer indicating the white color */
    public static final int WHITE = 0;
    /** Integer indicating the black color */
    public static final int BLACK = 1;
    private final int _FPS = 60;

    // Game items
    /** Game thread of the game */
    private Thread _gameThread;
    /** Game board */
    private Board _board = new Board();
    /** Variable to represent the mouse of the game */
    private Mouse _mouse = new Mouse();
    /** Variable to inform the color of the player's pieces whose turn is it */
    private int _currentColor = WHITE;
    /** Variable that informs if the movement of the current active piece is valid */
    private boolean _canMove;
    /** Variable that informs if the cell the current active piece wants to reach is valid */
    private boolean _validSquare;

    // Pieces items
    /** List of the pieces  */
    public static ArrayList<Piece> pieces = new ArrayList<>();
    /** List of the pieces to use in the simulation */
    public static ArrayList<Piece> simPieces = new ArrayList<>();
    /** Variable to represent the current active piece */
    private Piece activePiece;
    public static Piece castlingPiece;

    // Constructor
    /** Constructor of the Game Panel */
    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        addMouseMotionListener(this._mouse);
        addMouseListener(this._mouse);
        setPieces();
        copyPieces(pieces, simPieces);
    }

    // Methods
    /** Launch game method */
    public void launchGame() {
        this._gameThread = new Thread(this);
        this._gameThread.start(); // Calls the "run" method
    }

    /** Set the color and the position of the pieces in the beginning of the game */
    public void setPieces() {
        // White pieces
        for (int i=0; i<8; i++) pieces.add(new Pawn(WHITE, i, 6));
        pieces.add(new Rook(WHITE, 0, 7));
        pieces.add(new Knight(WHITE, 1, 7));
        pieces.add(new Bishop(WHITE, 2, 7));
        pieces.add(new Queen(WHITE, 3, 7));
        pieces.add(new King(WHITE, 4, 7));
        pieces.add(new Bishop(WHITE, 5, 7));
        pieces.add(new Knight(WHITE, 6, 7));
        pieces.add(new Rook(WHITE, 7, 7));
        // Black pieces
        for (int i=0; i<8; i++) pieces.add(new Pawn(BLACK, i, 1));
        pieces.add(new Rook(BLACK, 0, 0));
        pieces.add(new Knight(BLACK, 1, 0));
        pieces.add(new Bishop(BLACK, 2, 0));
        pieces.add(new Queen(BLACK, 3, 0));
        pieces.add(new King(BLACK, 4, 0));
        pieces.add(new Bishop(BLACK, 5, 0));
        pieces.add(new Knight(BLACK, 6, 0));
        pieces.add(new Rook(BLACK, 7, 0));
    }

    /** Copies the source list into the target list */
    private void copyPieces(ArrayList<Piece> source, ArrayList<Piece> target) {
        target.clear();
        target.addAll(source);
    }

    /** This method creates a Game loop that runs continuously during the game.
        It calls the "update" and "repaint" methods 60 times per second */
    @Override
    public void run() {
        // Game Loop
        double drawInterval = 1000000000./_FPS, delta = 0;
        long lastTime = System.nanoTime(), currentTime;

        while (_gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime)/drawInterval;
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    /** Method to check for any update in the board */
    private void update() {
        if (this._mouse.isPressed()) {
            if (this.activePiece == null) {
                // This means the player isn't holding a piece at the moment
                for (Piece piece : simPieces) {
                    boolean sameCol = this._mouse.getX()/Board.SQUARE_SIZE == piece.getCol();
                    boolean sameRow = this._mouse.getY()/Board.SQUARE_SIZE == piece.getRow();
                    if (piece.getColor() == this._currentColor && sameRow && sameCol) {
                        this.activePiece = piece;
                        break;
                    }
                }
            } else {
                // We enter here if the player is holding a piece
                simulate();
            }
        }
        else {
            if (this.activePiece != null) {
                if (this._validSquare) {
                    // Move Confirmed
                    // If we hit a piece, we remove it from the list
                    if (this.activePiece.getHitPiece() != null) simPieces.remove(this.activePiece.getHitPiece().getIndex());
                    copyPieces(simPieces, pieces);
                    this.activePiece.updatePosition();
                    this.activePiece.setMoved(true);
                    changePlayer();
                } else {
                    // Move Cancelled
                    copyPieces(pieces, simPieces);
                    this.activePiece.resetPosition();
                }
                this.activePiece = null;
            }
        }
    }

    private void simulate() {
        this._canMove = false;
        this._validSquare = false;
        // We update the position of the active piece, so it appears that the cursor is in the middle
        this.activePiece.setX(this._mouse.getX() - Board.HALF_SQUARE_SIZE);
        this.activePiece.setY(this._mouse.getY() - Board.HALF_SQUARE_SIZE);
        this.activePiece.setCol(this.activePiece.getBoardCol());
        this.activePiece.setRow(this.activePiece.getBoardRow());
        // We check if the square selected if a valid move
        if (this.activePiece.canMove(this.activePiece.getCol(), this.activePiece.getRow())) {
            this._canMove = true;
            this._validSquare = true;
        }
    }

    /** Method that changes the player when the turn is over */
    private void changePlayer() {
        if (this._currentColor == WHITE) {
            this._currentColor = BLACK;
        } else {
            this._currentColor = WHITE;
        }
    }

    /** Method to print any updates that happens in the game */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        this._board.draw(g2); // BOARD
        for (Piece piece : simPieces) piece.draw(g2); // PIECES
        if (this.activePiece != null) {
            if (this._canMove) {
                g2.setColor(Color.WHITE);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
                g2.fillRect(this.activePiece.getCol() * Board.SQUARE_SIZE, this.activePiece.getRow() * Board.SQUARE_SIZE,
                        Board.SQUARE_SIZE, Board.SQUARE_SIZE);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            }
            this.activePiece.draw(g2);
        }
    }
}
