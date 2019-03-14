
/**
 * Wrap the mouse and the TUIO multitouch cursors under the same object.
 *
 *
 */
public class Cursor {

  int x = -1, y = -1;
  int prevX = -1, prevY = -1;
  int id = -1;
  boolean isPressed = false;
  float timeStamp = 0;
}
