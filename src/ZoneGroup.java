
/**
 * A group of Zone object for easier control of group position, exclusive click
 * within the group and encapsulation of a Zone array.
 */
public class ZoneGroup extends ZoneAdapter {

    Zone[] zones;
    int numZone = 0;
    boolean exclusive = true;
    int pressIdx = -1;

    public ZoneGroup(Draw _draw, int _id, int _numZone) {
        numZone = _numZone;
        zones = new Zone[_numZone];
        for (int i = 0; i < _numZone; i++) {
            zones[i] = new Zone(_draw, "zone" + i + "_group1", 0, 0, 0, 0);
        }
    }

    public Zone getZoneByIdx(int idx) {
        return zones[idx];
    }

    @Override
    public void moveAbsolute(int newX, int newY) {
        for (int i = 0; i < zones.length; i++) {
            zones[i].moveAbsolute(newX, newY);
        }
    }

    //public void moveWith(ZoneInterface zone) {
    public void moveWith(ZoneAdapter zone) {
        for (int i = 0; i < zones.length; i++) {
            zones[i].moveRelative(zone.x-zone.prevX, zone.y-zone.prevY);
        }
    }

    @Override
    public void onCursorMove(Cursor cursor) {
        for (int i = 0; i < zones.length; i++) {
            zones[i].onCursorMove(cursor);
        }
    }

    @Override
    public void onCursorPress(Cursor cursor) {
        pressIdx = -1;
        for (int i = 0; i < zones.length; i++) {
            zones[i].onCursorPress(cursor);
            if (zones[i].isPressed && pressIdx == -1) {
                pressIdx = i;
                zones[i].inhibited = false;
            } else {
                zones[i].inhibited = true;
            }
        }
    }

    @Override
    public void onCursorRelease(Cursor cursor) {
        for (int i = 0; i < zones.length; i++) {
            zones[i].onCursorRelease(cursor);
            zones[i].inhibited = false;
        }
    }

    @Override
    public void onCursorDrag(Cursor cursor) {
        for (int i = 0; i < zones.length; i++) {
            zones[i].onCursorDrag(cursor);
        }
    }

    @Override
    public void draw() {
        for (int i = 0; i < zones.length; i++) {
            zones[i].draw();
        }
    }
}
