import java.awt.event.*;

import TUIO.TuioClient;
import TUIO.TuioCursor;
import TUIO.TuioListener;
import TUIO.TuioObject;
import TUIO.TuioTime;

/**
 * Mixes mouse and TUIO multitouch events under the same event system.
 *
 */
public abstract class MultiTouchAdapter implements TuioListener {

    //CursorController cursorCtrl = new CursorController();
    //Cursor mouse = new Cursor();
    Cursor mouse;
    TuioClient client;
    Draw draw;
    JoglFramework framework;

    public MultiTouchAdapter(JoglFramework _framework) {
        framework = _framework;
        draw = framework.getDrawingBoard();

        CursorController.addCursor();
        mouse = CursorController.getCursor(0);
        client = new TuioClient(3333);
        client.addTuioListener(this);
        client.connect();
    }

    public MultiTouchAdapter(){

        CursorController.addCursor();
        mouse = CursorController.getCursor(0);
        client = new TuioClient(3333);
        client.addTuioListener(this);
        client.connect();

    }

    public void addTuioObject(TuioObject tuioObj) {
    }

    public void updateTuioObject(TuioObject tuioObj) {
    }

    public void removeTuioObject(TuioObject tuioObj) {
    }

    public void addTuioCursor(TuioCursor tuioCur) {
        Cursor newCursor = CursorController.addCursor();
        newCursor.id = tuioCur.getCursorID();
        newCursor.x = tuioCur.getScreenX(draw.getWidth());
        newCursor.y = tuioCur.getScreenY(draw.getHeight());
        newCursor.prevX = newCursor.x;
        newCursor.prevY = newCursor.y;
        newCursor.isPressed = true;
        newCursor.timeStamp = tuioCur.getTuioTime().getTotalMilliseconds();
        onCursorPress(newCursor);
        draw.repaint();
    }

    public void updateTuioCursor(TuioCursor tuioCur) {
        Cursor cursorToUpdate = CursorController.getCursorById(tuioCur.getCursorID());
        cursorToUpdate.prevX = cursorToUpdate.x;
        cursorToUpdate.prevY = cursorToUpdate.y;
        cursorToUpdate.x = tuioCur.getScreenX(draw.getWidth());
        cursorToUpdate.y = tuioCur.getScreenY(draw.getHeight());
        cursorToUpdate.timeStamp = tuioCur.getTuioTime().getTotalMilliseconds();
        onCursorDrag(cursorToUpdate);
        draw.repaint();
    }

    public void removeTuioCursor(TuioCursor tuioCur) {
        Cursor cursorToRemove = CursorController.getCursorById(tuioCur.getCursorID());
        CursorController.removeCursor(cursorToRemove);
        onCursorRelease(cursorToRemove);
        draw.repaint();
    }

    public void refresh(TuioTime arg0) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        mouse.x = e.getX();
        mouse.y = e.getY();
        mouse.prevX = mouse.x;
        mouse.prevY = mouse.y;
        mouse.isPressed = true;
        //onCursorPress(e);
        onCursorPress(mouse);
        draw.repaint();
    }

    //public void onCursorPress(MouseEvent e) {
    public void onCursorPress(Cursor cursor) {
    }

    public void mouseReleased(MouseEvent e) {
        mouse.isPressed = false;
        onCursorRelease(mouse);
        draw.repaint();
    }

    public void onCursorRelease(Cursor cursor) {
    }

    public void mouseClicked(MouseEvent e) {
        onCursorClick(mouse);
    }

    public void onCursorClick(Cursor cursor) {
    }

    public void mouseDragged(MouseEvent e) {
        mouse.prevX = mouse.x;
        mouse.prevY = mouse.y;
        mouse.x = e.getX();
        mouse.y = e.getY();
        onCursorDrag(mouse);
        draw.repaint();
    }

    public void onCursorDrag(Cursor cursor) {
    }

    public void mouseMoved(MouseEvent e) {
        mouse.prevX = mouse.x;
        mouse.prevY = mouse.y;
        mouse.x = e.getX();
        mouse.y = e.getY();
        onCursorMove(mouse);
        draw.repaint();
    }

    public void onCursorMove(Cursor cursor) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    void draw() {
    }

    void setFramework(JoglFramework _framework) {
        framework = _framework;
        draw = framework.getDrawingBoard();
    }
}
