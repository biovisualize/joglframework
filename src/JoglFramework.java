
import TUIO.TuioClient;
import TUIO.TuioCursor;
import TUIO.TuioListener;
import TUIO.TuioObject;
import TUIO.TuioTime;
import com.sun.opengl.util.Animator;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.media.opengl.GL;

/**
 * The abstract class encapsulating a Jogl canvas, a Tuio client and other utilities.
 * The main class must extend this abstract class.
 */
public abstract class JoglFramework
        extends GLCanvas
        implements
        MouseListener, MouseMotionListener, KeyListener,
        GLEventListener,
        TuioListener {

    StopWatch fps = new StopWatch();
    Draw draw = null;
    int width = Config.width;
    int height = Config.height;
    Cursor mouse;
    TuioClient client;
    Animator animator;

    public void setPreferredWindowSize(int w, int h) {
        width = w;
        height = h;
    }

    public JoglFramework(GLCapabilities caps) {
        super(caps);
        draw = new Draw(this);
        //clientWindow = new Window(this);
        addGLEventListener(this);
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);

        CursorController.addCursor();
        mouse = CursorController.getCursor(0);
    }

    public void initTUIO() {
        client = new TuioClient(3333);
        client.addTuioListener(this);
        client.connect();
    }

    public Draw getDrawingBoard() {
        return draw;
    }

    public float getSPF() {
        return fps.getSPF();
    }

    public float getFPS() {
        return fps.getFPS();
    }

    void stopwatchStart() {
        fps.start();
    }

    public void stopwatchStop() {
        fps.stop();
    }

    public long stopwatchGetTime() {
        return fps.getTime();
    }

    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    public void requestRedraw() {
        repaint();
    }

    public void init(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        width = drawable.getWidth();
        height = drawable.getHeight();
        gl.setSwapInterval(1);
        gl.glEnable(GL.GL_POINT_SMOOTH);
        gl.glAlphaFunc(GL.GL_GREATER, 0);
        gl.glDisable(GL.GL_DEPTH_TEST);
        gl.glEnable(GL.GL_BLEND);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glClearColor(1, 1, 1, 0);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        animator = new Animator(drawable);
        animator.start();
    }

    public void reshape(
            GLAutoDrawable drawable,
            int x, int y, int width, int height) {
        GL gl = drawable.getGL();
        width = drawable.getWidth();
        height = drawable.getHeight();
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(0, width, height, 0, -1000, 1000);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void displayChanged(
            GLAutoDrawable drawable,
            boolean modeChanged,
            boolean deviceChanged) {
        // leave this empty
    }

    public void display(GLAutoDrawable drawable) {
        fps.updateTime();
        draw.set(drawable);
        draw();
        //->bad way to loop
        //repaint();
    }

    abstract void draw();

    /*private static void createUI() {
    GLCapabilities caps = new GLCapabilities();
    caps.setDoubleBuffered(true);
    caps.setHardwareAccelerated(true);
    JoglFramework gf = new JoglFramework(caps);

    JFrame frame = new JFrame("-");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);

    Container pane = frame.getContentPane();
    pane.setLayout(new BorderLayout());
    pane.add(gf, BorderLayout.CENTER);

    frame.pack();
    frame.setVisible(true);

    //        //Second panel
    //        JoglPanel jP = new JoglPanel(caps);
    //        JFrame frame2 = new JFrame("-");
    //        //frame2.setSize(300, 300);
    //        frame2.setLocation(650, 0);
    //        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //        frame2.setVisible(true);
    //
    //        Container pane2 = frame2.getContentPane();
    //        pane2.setLayout(new BorderLayout());
    //        pane2.add(jP, BorderLayout.CENTER);
    //
    //        frame2.pack();
    //        frame2.setVisible(true);
    }

    public static void main(String[] args) {
    javax.swing.SwingUtilities.invokeLater(
    new Runnable() {

    public void run() {
    createUI();
    }
    });
    }*/
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
        onCursorPress(mouse);
        draw.repaint();
    }

    abstract void onCursorPress(Cursor cursor);

    public void mouseReleased(MouseEvent e) {
        mouse.isPressed = false;
        onCursorRelease(mouse);
        draw.repaint();
    }

    abstract void onCursorRelease(Cursor cursor);

    public void mouseClicked(MouseEvent e) {
        onCursorClick(mouse);
    }

    abstract void onCursorClick(Cursor cursor);

    public void mouseDragged(MouseEvent e) {
        mouse.prevX = mouse.x;
        mouse.prevY = mouse.y;
        mouse.x = e.getX();
        mouse.y = e.getY();
        onCursorDrag(mouse);
        draw.repaint();
    }

    abstract void onCursorDrag(Cursor cursor);

    public void mouseMoved(MouseEvent e) {
        mouse.prevX = mouse.x;
        mouse.prevY = mouse.y;
        mouse.x = e.getX();
        mouse.y = e.getY();
        onCursorMove(mouse);
        draw.repaint();
    }

    abstract void onCursorMove(Cursor cursor);

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }
}
