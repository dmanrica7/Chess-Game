package Main;
import java.awt.*;

public class Board {
    // Class attributes
    /** Board field to indicate the maximum number of columns of the board */
    private final int MAX_COL = 8;
    /** Board field to indicate the maximum number of rows of the board */
    private final int MAX_ROW = 8;
    /** Board field to indicate the size of a square of the board */
    public static final int SQUARE_SIZE = 50;
    /** Board field to indicate the size of the half of a square of the board */
    public static final int HALF_SQUARE_SIZE = 25;

    // Constructor
    /** Default constructor */
    public Board() { }

    // Methods
    /** Method to draw the color of the board */
    public void draw(Graphics2D graphics2D) {
        int color = 0;
        for (int row = 0; row < MAX_ROW; row++) {
            for (int col = 0; col < MAX_COL; col++) {
                if (color == 0) {
                    graphics2D.setColor(new Color(210, 200, 200));
                    color = 1;
                } else {
                    graphics2D.setColor(new Color(15, 75, 150));
                    color = 0;
                }
                graphics2D.fillRect(col*SQUARE_SIZE, row*SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
            }
            if (color == 1) color = 0;
            else color = 1;
        }
    }
}
