/**
 * Abstract class to be used by Zone and ZoneGroup
 */
public abstract class ZoneAdapter {
    public int x, y, w, h, prevX, prevY;

    public void onCursorMove(Cursor cursor){

    }

    public void onCursorPress(Cursor cursor){

    }

    public void onCursorRelease(Cursor cursor){

    }

    public void onCursorDrag(Cursor cursor){

    }

    public void moveAbsolute(int newX, int newY){

    }

    public void moveRelative(int newX, int newY){

    }

    public void draw(){
        
    }
}
