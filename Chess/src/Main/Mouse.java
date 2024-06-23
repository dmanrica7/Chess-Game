package Main;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Mouse extends MouseAdapter {
    /** Class attributes */
    private int _x, _y;
    private boolean _pressed;

    /** Constructor */
    public Mouse() {}

    /** Getters */
    public int getX() {
        return _x;
    }

    public int getY() {
        return _y;
    }

    public boolean isPressed() {
        return _pressed;
    }

    /** Setters */
    public void setX(int _x) {
        this._x = _x;
    }

    public void setY(int _y) {
        this._y = _y;
    }

    public void setPressed(boolean _pressed) {
        this._pressed = _pressed;
    }

    /** Methods */
    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        this._pressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        this._pressed = false;
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        this._x = mouseEvent.getX();
        this._y = mouseEvent.getY();
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        this._x = mouseEvent.getX();
        this._y = mouseEvent.getY();
    }
}
