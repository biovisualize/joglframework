import java.awt.Color;
import javax.media.opengl.GL;

/**
 * A reactive rectangle zone that can be hovered, pressed and dragged.
 * Works with any cursor (multitouch or mouse). 
 */
public class Zone extends ZoneAdapter {

    //public int x, y, w, h;
    String id;
    public boolean isPressed, isOver;
    public boolean isPressedLock;
    public boolean isDragged;
    public boolean isDraggable;
    public int pressOffsetX, pressOffsetY;
    Color baseColor = new Color(250, 250, 250, 255);
    Color selectedColor = new Color(0, 255, 0, 255);
    Cursor activeCursor = new Cursor();
    Draw draw;
    boolean inhibited = false;

    public Zone(Draw _draw, String _id, int _x, int _y, int _w, int _h) {
        draw = _draw;
        id = _id;
        x = _x;
        y = _y;
        w = _w;
        h = _h;
    }

    public void setAttributes(int _x, int _y, int _w, int _h) {
        x = _x;
        y = _y;
        w = _w;
        h = _h;
    }

    public void inhibit(boolean _inhibited) {
        inhibited = _inhibited;
    }

    @Override
    public void onCursorMove(Cursor cursor) {
        if (cursor.x > x && cursor.x < x + w //
                && cursor.y > y && cursor.y < y + h) {
            isOver = true;
        } else {
            isOver = false;
        }
    }

    //-> le premier detecte comme onpress est le curseur actif
    @Override
    public void onCursorPress(Cursor cursor) {
        if (cursor.x > x && cursor.x < x + w //
                && cursor.y > y && cursor.y < y + h) {
            if (isOver && !inhibited) {
                isPressed = true;
                pressOffsetX = cursor.x - x;
                pressOffsetY = cursor.y - y;
                System.out.println("pressed: " + id);
            } else {
                isPressed = false;
            }
        } else {
            isPressed = false;
        }
    }

    @Override
    public void onCursorRelease(Cursor cursor) {
        isPressed = false;
    }

    //void onCursorDrag(MouseEvent e) {
    @Override
    public void onCursorDrag(Cursor cursor) {
        prevX = x;
        prevY = y;
        
        if (isPressed && !inhibited) {
            x = cursor.x - pressOffsetX;
            y = cursor.y - pressOffsetY;
        }
    }

    @Override
    public void moveAbsolute(int newX, int newY) {
        prevX = x;
        prevY = y;
        x = newX;
        y = newY;
    }
    
    @Override
    public void moveRelative(int deltaX, int deltaY) {
        prevX = x;
        prevY = y;
        x += deltaX;
        y += deltaY;
    }

    @Override
    public void draw() {
        GL gl = draw.getGlContext();
        gl.glPolygonMode(GL.GL_BACK, GL.GL_FILL);
        gl.glBegin(GL.GL_QUADS);
        gl.glColor3fv(baseColor.getColorComponents(null), 0);
        gl.glVertex3f(x, y, 0);
        gl.glVertex3f(x + w - 1, y, 0);
        gl.glVertex3f(x + w - 1, y + h - 1, 0);
        gl.glVertex3f(x, y + h - 1, 0);
        gl.glEnd();

        gl.glLineWidth(1);
        gl.glPolygonMode(GL.GL_BACK, GL.GL_LINE);
        gl.glBegin(GL.GL_QUADS);
        if (isOver && !inhibited) {
            if (isPressed) {
                gl.glColor3fv(selectedColor.getColorComponents(null), 0);
            } else {
                //gl.glColor3fv(selectedColor.darker().getColorComponents(null), 0);
                gl.glColor3f(255f, 0f, 0f);
            }
        } else {
            gl.glColor3fv(baseColor.darker().getColorComponents(null), 0);
        }
        gl.glVertex3f(x, y, 0);
        gl.glVertex3f(x + w - 1, y, 0);
        gl.glVertex3f(x + w - 1, y + h - 1, 0);
        gl.glVertex3f(x, y + h - 1, 0);
        gl.glEnd();
    }
}
