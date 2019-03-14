
import java.util.ArrayList;
/**
 * Manage the cursors
 */
public class CursorController {

    static ArrayList<Cursor> cursor = new ArrayList<Cursor>();

    //public CursorController() {
        //cursor.add(new Cursor());
    //}

    static Cursor getCursor(int idx) {
        return cursor.get(idx);
    }

    static Cursor addCursor() {
        cursor.add(new Cursor());
        return cursor.get(getCursorNum() - 1);
    }

    static int getCursorNum() {
        return cursor.size();
    }

    static void removeCursorById(int id) {
        Cursor cursorToRemove = getCursorById(id);
        if (cursorToRemove != null) {
            cursor.remove(cursorToRemove);
        }
    }

    static void removeCursor(Cursor cursorToRemove) {
        cursor.remove(cursorToRemove);
    }

    static Cursor getCursorById(int id) {
        for (int i = 0; i < cursor.size(); i++) {
            if (id == cursor.get(i).id) {
                return cursor.get(i);
            }
        }
        return null;
    }
}
