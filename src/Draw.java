
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import com.sun.opengl.util.GLUT;
import java.awt.Color;
import javax.media.opengl.glu.GLU;
import com.sun.opengl.util.j2d.TextRenderer;
import java.awt.Font;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.media.opengl.glu.GLUtessellator;
import javax.media.opengl.glu.GLUtessellatorCallback;
import javax.media.opengl.glu.GLUtessellatorCallbackAdapter;

/**
 * The drawing framework encapsulating Jogl functions.
 */
public class Draw {

	int w, h;
	private int fontHeight = 14;
	private static final float G_FONT_ASCENT = 119.05f;
	private static final float G_FONT_DESCENT = 33.33f;
	private static final float G_CHAR_WIDTH = 104.76f;
	//->rendre generique
	public TextRenderer textRenderer = new TextRenderer(new Font("Georgia", Font.BOLD, 12), true, false, null, true);
	public TextRenderer textRenderer2 = new TextRenderer(new Font("Georgia", Font.BOLD, 12), true, false, null, true);
	JoglFramework framework = null;
	public GL gl = null;
	public GLUT glut = null;
	public GLU glu = null;

	public Draw(JoglFramework _jogl) {
		framework = _jogl;
	}

	public JoglFramework getFramework() {
		return framework;
	}

	public int getWidth() {
		return framework.width;
	}

	public int getHeight() {
		return framework.height;
	}

	public int getFPS() {
		return framework.height;
	}

	/*public float getTimePassed(){
	return framework.getTimePassed();
	}

	public void stopwatchStart() {
	framework.stopwatchStart();
	}

	public void stopwatchStop() {
	framework.stopwatchStop();
	}

	public long stopwatchGetTimePassed() {
	return framework.stopwatchGetTimePassed();
	}*/
	public void set(GLAutoDrawable drawable) {
		gl = drawable.getGL();
		glut = new GLUT();
		glu = new GLU();
		gl.setSwapInterval(0);
		gl.glEnable(GL.GL_POINT_SMOOTH);
		gl.glAlphaFunc(GL.GL_GREATER, 0);
		gl.glEnable(GL.GL_BLEND);
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
		gl.glEnable(GL.GL_LINE_SMOOTH);
		gl.glHint(GL.GL_LINE_SMOOTH_HINT, GL.GL_FASTEST);
		gl.glDepthFunc(GL.GL_LESS);
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glEnable(GL.GL_NORMALIZE);
		//gl.glShadeModel(GL.GL_SMOOTH);
		gl.glEnable(GL.GL_CULL_FACE); //save 10 fps
		//gl.glCullFace(2);

		//->has to be easily set in the code
		gl.glPushMatrix();
		gl.glTranslatef(getWidth() / 2-200, getHeight() / 2, -5.0f);
		//gl.glScalef(60f, 60f, 60f);

		//float[] lightColorAmbient = {0.2f, 0.2f, 0.2f, 1f};
		float[] lightColorAmbient = {0.6f, 0.6f, 0.6f, 1f};
		float[] lightColorSpecular = {0.2f, 0.2f, 0.2f, 1f};
		//float[] lightColorSpecular = {1f, 1f, 1f, 1f};
		float[] position = {0.0f, 0.0f, 100.0f, 100.0f};
		//gl.glPushMatrix();
		//gl.glRotated(0.0, 1.0, 0.0, 0.0);
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, position, 0);
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, lightColorAmbient, 0);
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, lightColorSpecular, 0);
		//gl.glPopMatrix();
		gl.glPopMatrix();
		gl.glEnable(GL.GL_LIGHTING);
		gl.glEnable(GL.GL_LIGHT0);



		float[] rgba1 = {0.3f, 0.5f, 1f};
		//float[] rgba1 = {0f, 0f, 0f};
    float[] rgba2 = {0.1f, 0.1f, 1f};
    //float[] rgba2 = {1f, 1f, 1f};
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, rgba1, 0);
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, rgba2, 0);
		gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, 0.1f);

	}

	public GL getGlContext() {
		return gl;
	}

	public void setFontHeight(int h) {
		fontHeight = h;
	}

	public int getFontHeight() {
		return fontHeight;
	}

	/*public Vector3D getScreenCoordinates(Vector3D pos) {
	double[] screenCoord = new double[4];
	double[] model = new double[16];
	double[] projection = new double[16];
	int[] viewport = new int[4];
	gl.glGetDoublev(GL.GL_MODELVIEW_MATRIX, model, 0);
	gl.glGetDoublev(GL.GL_PROJECTION_MATRIX, projection, 0);
	gl.glGetIntegerv(GL.GL_VIEWPORT, viewport, 0);
	float vX = pos.x();
	float vY = pos.y();
	glu.gluProject(vX, vY, 0f, model, 0, projection, 0, viewport, 0, screenCoord, 0);
	return new Vector3D((float) screenCoord[0], (float) screenCoord[1], (float) screenCoord[2]);
	}*/
	public void repaint() {
		framework.requestRedraw();
	}

	public void setBackground(float r, float g, float b) {
		gl.glClearColor(r, g, b, 0);
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
	}

	/*public void setupForDrawing() {
	gl.glMatrixMode(GL.GL_MODELVIEW);
	gl.glLoadIdentity();
	gl.glDepthFunc(GL.GL_LEQUAL);
	gl.glDisable(GL.GL_DEPTH_TEST);
	gl.glDisable(GL.GL_CULL_FACE);
	gl.glDisable(GL.GL_LIGHTING);
	gl.glShadeModel(GL.GL_FLAT);
	gl.glEnable(GL.GL_POINT_SMOOTH);
	}

	//-->incomplet
	public void resize(int _w, int _h) {
	w = _w;
	h = _h;
	gl.glMatrixMode(GL.GL_PROJECTION);
	gl.glLoadIdentity();
	gl.glOrtho(0, _w, _h, 0, -1000, 1000);
	gl.glMatrixMode(GL.GL_MODELVIEW);
	gl.glLoadIdentity();
	}*/
	
	public void enableAlphaBlending() {
		gl.glEnable(GL.GL_LINE_SMOOTH);
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
		gl.glEnable(GL.GL_BLEND);
	}

	public void disableAlphaBlending() {
		gl.glDisable(GL.GL_LINE_SMOOTH);
		gl.glDisable(GL.GL_BLEND);
	}

	public void enableLineStippling() {
		gl.glEnable(GL.GL_LINE_STIPPLE);
		gl.glLineStipple(2, (short) 0xAAAA);
	}

	public void disableLineStippling() {
		gl.glDisable(GL.GL_LINE_STIPPLE);
	}

	public void rotate(
					float rotationCenterX,
					float rotationCenterY,
					float rotationCenterZ,
					float angleX,
					float angleY,
					float angleZ) {
		gl.glPushMatrix();
		gl.glTranslatef(rotationCenterX, rotationCenterY, rotationCenterZ);
		gl.glRotatef(angleX, 1.0f, 0.0f, 0.0f);
		gl.glRotatef(angleY, 0.0f, 1.0f, 0.0f);
		gl.glRotatef(angleZ, 0.0f, 0.0f, 1.0f);
		gl.glTranslatef(-rotationCenterX, -rotationCenterY, -rotationCenterZ);
	}

	public void endRotate() {
		gl.glPopMatrix();
	}

	public void beginTranslate(
					float translationX,
					float translationY,
					float translationZ) {
		gl.glPushMatrix();
		gl.glTranslatef(translationX, translationY, translationZ);
	}

	public void endTranslate() {
		gl.glPopMatrix();
	}

	public void setColor(float r, float g, float b) {
		gl.glColor3f(r, g, b);
	}

	public void setColor(float r, float g, float b, float alpha) {
		gl.glColor4f(r, g, b, alpha);
	}

	public void setColor(Color c) {
		setColor(c.getRed() / 255.0f, c.getGreen() / 255.0f, c.getBlue() / 255.0f, c.getAlpha() / 255.0f);
	}

	public void setColor(Color c, float alpha) {
		setColor(c.getRed() / 255.0f, c.getGreen() / 255.0f, c.getBlue() / 255.0f, alpha);
	}

	public void setLineWidth(float width) {
		gl.glLineWidth(width);
	}

	public void drawLine(float x1, float y1, float x2, float y2) {
		gl.glBegin(GL.GL_LINES);
		gl.glVertex3f(x1, y1, 0);
		gl.glVertex3f(x2, y2, 0);
		gl.glEnd();
		/*gl.glBegin(GL.GL_POINTS);
		gl.glVertex2f(x2, y2);
		gl.glEnd();*/
	}

	public void drawPolyline(ArrayList points, boolean isClosed, boolean isFilled) {

		if (isFilled) {
			gl.glBegin(GL.GL_POLYGON);
		} else {
			gl.glBegin(isClosed ? GL.GL_LINE_LOOP : GL.GL_LINE_STRIP);
		}
		for (int i = 0; i < points.size(); ++i) {
			Vector2D p = (Vector2D) points.get(points.size() - i - 1);
			gl.glVertex2f((float) p.x(), (float) p.y());
		}
		gl.glEnd();
	}

	public void drawPolyline3D(ArrayList points, boolean isClosed, boolean isFilled) {

		if (isFilled) {
			gl.glBegin(GL.GL_POLYGON);
		} else {
			gl.glBegin(isClosed ? GL.GL_LINE_LOOP : GL.GL_LINE_STRIP);
		}
		for (int i = 0; i < points.size(); ++i) {
			Vector3D p = (Vector3D) points.get(points.size() - i - 1);
			gl.glVertex3f((float) p.x(), (float) p.y(), (float) p.z());
		}
		gl.glEnd();
	}

	public void drawQuads(ArrayList points, boolean isClosed, boolean isFilled) {

		if (isFilled) {
			gl.glBegin(GL.GL_QUADS);
		} else {
			gl.glBegin(isClosed ? GL.GL_LINE_LOOP : GL.GL_LINE_STRIP);
		}
		for (int i = 0; i < points.size(); ++i) {
			Vector3D p = (Vector3D) points.get(points.size() - i - 1);
			gl.glVertex3f((float) p.x(), (float) p.y(), (float) p.z());
		}
		gl.glEnd();
	}

	public void drawQuadStrips(ArrayList points, boolean isClosed, boolean isFilled) {

		if (isFilled) {
			gl.glBegin(GL.GL_QUAD_STRIP);
		} else {
			gl.glBegin(isClosed ? GL.GL_LINE_LOOP : GL.GL_LINE_STRIP);
		}
		for (int i = 0; i < points.size(); ++i) {
			Vector3D p = (Vector3D) points.get(points.size() - i - 1);
			gl.glVertex3f((float) p.x(), (float) p.y(), (float) p.z());
		}
		gl.glEnd();
	}

	public void drawTriStrips(ArrayList points, boolean isClosed, boolean isFilled) {

		if (isFilled) {
			gl.glBegin(GL.GL_TRIANGLE_STRIP);
		} else {
			gl.glBegin(isClosed ? GL.GL_LINE_LOOP : GL.GL_LINE_STRIP);
		}
		for (int i = 0; i < points.size(); ++i) {
			Vector3D p = (Vector3D) points.get(points.size() - i - 1);
			gl.glVertex3f((float) p.x(), (float) p.y(), (float) p.z());
		}
		gl.glEnd();
	}

	public void drawTesselatePolyline(ArrayList<Point> points) {
		GLUtessellator tess = glu.gluNewTess();
		GLUtesselatorCallbackImpl tessCallback = new GLUtesselatorCallbackImpl(gl);

		glu.gluTessCallback(tess, GLU.GLU_TESS_VERTEX, tessCallback);// vertexCallback);
		glu.gluTessCallback(tess, GLU.GLU_TESS_BEGIN, tessCallback);// beginCallback);
		glu.gluTessCallback(tess, GLU.GLU_TESS_END, tessCallback);// endCallback);
		glu.gluTessCallback(tess, GLU.GLU_TESS_ERROR, tessCallback);// errorCallback);
		glu.gluTessCallback(tess, GLU.GLU_TESS_COMBINE, tessCallback);// combineCallback);

		//if (isFilled) {
		//    gl.glBegin(GL.GL_POLYGON);
		//} else {
		//    gl.glBegin(isClosed ? GL.GL_LINE_LOOP : GL.GL_LINE_STRIP);
		//}
		glu.gluTessBeginPolygon(tess, null);
		glu.gluTessBeginContour(tess);
		for (int i = 0; i < points.size(); ++i) {
			Point p = points.get(points.size() - i - 1);
			double[] pArray = {p.getX(), p.getY(), 0};
			//gl.glVertex2f((float) p.getX(), (float) p.getY());
			glu.gluTessVertex(tess, pArray, 0, pArray);
		}
		glu.gluTessEndContour(tess);
		glu.gluTessEndPolygon(tess);

		gl.glDepthMask(true);
		//gl.glEnd();
	}

	/*public void drawPolyline(ArrayList<Point> points, boolean isClosed, boolean isFilled) {
	if (points.size() <= 1) {
	return;
	}
	if (isFilled) {
	//gl.glBegin(GL.GL_POLYGON);
	gl.glBegin(GL.GL_TRIANGLES);
	} else {
	gl.glBegin(isClosed ? GL.GL_LINE_LOOP : GL.GL_LINE_STRIP);
	}
	for (int i = 0; i < points.size(); ++i) {

	Point p = points.get(points.size() - i - 1);
	gl.glVertex2f((float) p.getX(), (float) p.getY());
	Point p2 = new Point();
	if (i > 0) {
	p2 = points.get(points.size() - i);
	}else{
	p2 = points.get(0);
	}
	gl.glVertex2f(100, 120);
	gl.glVertex2f((float) p2.getX(), (float) p2.getY());
	//gl.glVertex2f((float) p.getX(), (float) p.getY());
	}
	gl.glEnd();
	}*/
	public void drawPolyline(ArrayList<Point> points, boolean isClosed) {
		drawPolyline(points, isClosed, false);
	}

	public void fillPolygon(ArrayList<Point> points) {
		drawPolyline(points, true, true);
	}

	public void drawRect(float x, float y, float w, float h, boolean isFilled) {
		if (isFilled) {
			fillRect(x, y, w, h);
		} else {
			drawRect(x, y, w, h);
		}
	}

	public void drawRect(float x, float y, float w, float h) {
		gl.glBegin(GL.GL_LINE_LOOP);
		gl.glVertex2f(x, y);
		gl.glVertex2f(x, y + h);
		gl.glVertex2f(x + w, y + h);
		gl.glVertex2f(x + w, y);
		gl.glEnd();
	}

	public void fillRect(float x, float y, float w, float h) {
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex2f(x, y);
		gl.glVertex2f(x, y + h);
		gl.glVertex2f(x + w, y + h);
		gl.glVertex2f(x + w, y);
		gl.glEnd();
	}

	void drawRectdBottomAligned(GL gl, float x, float y, float w, float h) {
		gl.glColor4f(0f, 0.5f, 1f, 1.0f);
		gl.glBegin(GL.GL_FILL);
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex3f(x, y, 0);
		gl.glVertex3f(x, y - h, 0);
		gl.glVertex3f(x + w, y - h, 0);
		gl.glVertex3f(x + w, y, 0);
		gl.glEnd();

		gl.glColor4f(1f, 1f, 1f, 1.0f);
		gl.glBegin(GL.GL_LINE_LOOP);
		gl.glBegin(GL.GL_QUADS);
		gl.glVertex3f(x, y, 0);
		gl.glVertex3f(x, y - h, 0);
		gl.glVertex3f(x + w, y - h, 0);
		gl.glVertex3f(x + w, y, 0);
		gl.glEnd();
	}

	public void drawCircle(float x, float y, float radius, boolean isFilled) {
		x += radius;
		y += radius;
		if (isFilled) {
			gl.glBegin(GL.GL_TRIANGLE_FAN);
			gl.glVertex2f(x, y);
		} else {
			gl.glLineWidth(2);
			gl.glBegin(GL.GL_LINE_LOOP);
		}

		int numSides = (int) (2 * Math.PI * radius + 1);
		float deltaAngle = 2 * (float) Math.PI / numSides;

		for (int i = 0; i <= numSides; ++i) {
			float angle = i * deltaAngle;
			gl.glVertex2f(x + radius * (float) Math.cos(angle) + 0.5f, y + radius * (float) Math.sin(angle) + 0.5f);
		}
		gl.glEnd();
		gl.glLineWidth(1);

	}

	public void drawCircle(float x, float y, float radius) {
		drawCircle(x, y, radius, false);
	}

	public void fillCircle(float x, float y, float radius) {
		drawCircle(x, y, radius, true);
	}

	public void drawCenteredCircle(float x, float y, float radius, boolean isFilled) {
		x -= radius;
		y -= radius;
		drawCircle(x, y, radius, isFilled);
	}

	public void drawCenteredRect(float x, float y, float w, float h, boolean isFilled) {
		x -= w / 2;
		y -= h / 2;
		drawRect(x, y, w, h, isFilled);
	}

	//autre version
	void drawCircle(float cx, float cy, float r, int num_segments) {
		gl.glBegin(GL.GL_LINE_LOOP);
		for (int i = 0; i < num_segments; i++) {
			float theta = 2.0f * 3.1415926f * (float) i / (float) num_segments;
			float x = r * (float) Math.cos(theta);//calculate the x component
			float y = r * (float) Math.sin(theta);//calculate the y component
			gl.glVertex2f(x + cx, y + cy);//output vertex
		}
		gl.glEnd();
	}

	public void drawPoint(float x, float y, float pointSize) {
		gl.glPointSize(pointSize);
		gl.glBegin(GL.GL_POINTS);
		gl.glVertex2f(x, y);
		gl.glEnd();
	}

	void drawFastBroadArc(float cx, float cy, float r, float start_angle, float arc_angle) {
		int num_segments = 10 * (int) Math.sqrt(r);
		float theta = arc_angle / (float) num_segments - 1;
		float tangential_factor = (float) Math.tan(theta);
		float radial_factor = (float) Math.cos(theta);
		float x = r * (float) Math.cos(start_angle);
		float y = r * (float) Math.sin(start_angle);
		gl.glBegin(GL.GL_LINE_STRIP);
		for (int ii = 0; ii < num_segments; ii++) {
			gl.glVertex2f(x + cx, y + cy);
			float tx = -y;
			float ty = x;
			x += tx * tangential_factor;
			y += ty * tangential_factor;
			x *= radial_factor;
			y *= radial_factor;
		}
		gl.glEnd();
	}

	public void drawArc(float x, float y, float radius, float startAngle, float stopAngle, boolean isFilled) {
		int numSides = (int) (2 * Math.PI * radius + 1);
		float deltaAngle = 2 * (float) Math.PI / numSides;
		if (isFilled) {
			gl.glBegin(GL.GL_POLYGON);
		} else {
			gl.glBegin(GL.GL_LINE_STRIP);
		}
		for (float angle = startAngle; angle < stopAngle; angle += deltaAngle) {
			gl.glVertex3f(x + radius * (float) Math.cos(angle), y + radius * (float) Math.sin(angle), 0f);
		}
		gl.glEnd();
	}

	public void drawPie(float x, float y, float radius, float startAngle, float stopAngle, boolean isFilled) {
		int numSides = (int) (2 * Math.PI * radius + 1);
		float deltaAngle = 2 * (float) Math.PI / numSides;
		if (isFilled) {
			gl.glBegin(GL.GL_POLYGON);
		} else {
			gl.glBegin(GL.GL_LINE_STRIP);
		}
		gl.glVertex3f(x, y, 0f);
		for (float angle = startAngle; angle < stopAngle; angle += deltaAngle) {
			gl.glVertex3f(x + radius * (float) Math.cos(angle), y + radius * (float) Math.sin(angle), 0f);
		}
		gl.glVertex3f(x, y, 0f);
		gl.glEnd();
	}

	public void drawPiePart(float x, float y, float radius, float innerRadius, float height, float bevel, float startAngle, float stopAngle, boolean isFilled) {
		int numSides = (int) (2 * Math.PI * radius + 1);
		//bevel = 0;
		float deltaAngle = 2 * (float) Math.PI / numSides;
		if (isFilled) {
			gl.glBegin(GL.GL_POLYGON);
		} else {
			gl.glBegin(GL.GL_LINE_LOOP);
		}
		for (float angle = startAngle; angle < stopAngle; angle += deltaAngle) {
			gl.glVertex3f(x + radius * (float) Math.cos(angle), y + radius * (float) Math.sin(angle), 0f);
		}
		for (float angle = stopAngle - deltaAngle * bevel; angle >= startAngle + deltaAngle * bevel; angle -= deltaAngle) {
			gl.glVertex3f(x + (radius - height) * (float) Math.cos(angle), y + (radius - height) * (float) Math.sin(angle), 0f);
		}
		gl.glEnd();
	}

	public float textWidth(String text) {
		Rectangle2D bounds = textRenderer.getBounds(text);
		return (float) bounds.getWidth();
	}

	public float textHeight(String text) {
		Rectangle2D bounds = textRenderer.getBounds(text);
		return (float) bounds.getHeight();
	}

	public void drawText2D(String text, int x, int y) {
		textRenderer.beginRendering(w, h);
		textRenderer.draw(text, x, y);
		//textRenderer.draw(text, (int)(getWidth()-convertFromPixelX(getWidth() - x)), (int)(getHeight()-convertFromPixelY(y)));
		//textRenderer.draw(text, (int) (getWidth() - getWidth() - x), (int) getHeight() - y);
		textRenderer.endRendering();
		textRenderer.flush();
	}

	public void drawText3D(String text, int x, int y, int z) {
		gl.glPushMatrix();
		gl.glTranslatef(x, y, z);

		gl.glScalef(1.0f, -1.0f, 1.0f);
		gl.glTranslatef(-x, -y, -z);
		gl.glPolygonMode(GL.GL_BACK, GL.GL_FILL);
		textRenderer.begin3DRendering();
		//Rectangle2D bounds1 = textRenderer.getBounds(text);
		//float text1W = (float) bounds1.getWidth();
		//float text1H = (float) bounds1.getHeight();
		textRenderer.draw3D(text, x, y, z, 1f);
		textRenderer.end3DRendering();
		textRenderer.flush();
		gl.glPopMatrix();
	}
	//à compléter

	public void drawText3D(String text, int textRendererNum, int x, int y, int z) {
		gl.glPushMatrix();
		gl.glTranslatef(x, y, z);

		gl.glScalef(1.0f, -1.0f, 1.0f);
		gl.glTranslatef(-x, -y, -z);
		gl.glPolygonMode(GL.GL_BACK, GL.GL_FILL);
		textRenderer2.begin3DRendering();
		textRenderer2.draw3D(text, x, y, z, 1f);
		textRenderer2.end3DRendering();
		textRenderer2.flush();
		gl.glPopMatrix();
	}

	void drawRotatedText3D(String text, int x, int y, int z, int rotX, int rotY, int rotZ) {
		gl.glPushMatrix();
		gl.glTranslatef(x, y, z);
		gl.glRotatef(rotX, 1.0f, 0.0f, 0.0f);
		gl.glRotatef(rotY, 0.0f, 1.0f, 0.0f);
		gl.glRotatef(rotZ, 0.0f, 0.0f, 1.0f);
		gl.glScalef(1.0f, -1.0f, 1.0f);
		gl.glTranslatef(-x, -y, -z);
		gl.glPolygonMode(GL.GL_BACK, GL.GL_FILL);
		textRenderer.begin3DRendering();
		//Rectangle2D bounds1 = textRenderer.getBounds(text);
		//float text1W = (float) bounds1.getWidth();
		//float text1H = (float) bounds1.getHeight();
		textRenderer.draw3D(text, x, y, z, 1f);
		textRenderer.end3DRendering();
		textRenderer.flush();
		gl.glPopMatrix();
	}

	public void setTextColor(Color color) {
		textRenderer.setColor(color);
	}

	// returns the width of a string
	public float stringWidth(String s) {
		if (s == null || s.length() == 0) {
			return 0;
		}

		float h, w_over_h;

		h = G_FONT_ASCENT /* + G_FONT_DESCENT + G_FONT_VERTICAL_SPACE */;
		w_over_h = G_CHAR_WIDTH / h;

		return fontHeight * s.length() * w_over_h;
	}

	public void drawString(
					float x, float y, // lower left corner of the string
					String s // the string
					) {
		if (s == null || s.length() == 0) {
			return;
		}

		float ascent = fontHeight * G_FONT_ASCENT
						/ (G_FONT_ASCENT /* + G_FONT_DESCENT + G_FONT_VERTICAL_SPACE */);
		y += fontHeight - ascent;

		gl.glPushMatrix();
		gl.glTranslatef(x, y, 0);
		gl.glScalef(1, -1, 1); // to flip the y axis

		// We scale the text to make its height that desired by the caller.
		float sf = ascent / G_FONT_ASCENT; // scale factor
		gl.glScalef(sf, sf, 1);
		for (int j = 0; j < s.length(); ++j) {
			glut.glutStrokeCharacter(GLUT.STROKE_MONO_ROMAN, s.charAt(j));
		}
		gl.glPopMatrix();
	}

	private class GLUtesselatorCallbackImpl extends GLUtessellatorCallbackAdapter {

		private GL gl;

		public GLUtesselatorCallbackImpl(GL gl) {
			this.gl = gl;
		}

		public void begin(int type) {
			gl.glBegin(type);
			//gl.glBegin(GL.GL_LINE_LOOP);
		}

		public void vertex(java.lang.Object vertexData) {
			double[] coords = (double[]) vertexData;

			gl.glVertex3dv(coords, 0);
		}

		public void end() {
			gl.glEnd();
		}
	}

	class tessellCallBack implements GLUtessellatorCallback {

		private GL gl;
		private GLU glu;

		public tessellCallBack(GL gl, GLU glu) {
			this.gl = gl;
			this.glu = glu;
		}

		public void begin(int type) {
			gl.glBegin(type);
		}

		public void end() {
			gl.glEnd();
		}

		public void vertex(Object vertexData) {
			double[] pointer;
			if (vertexData instanceof double[]) {
				pointer = (double[]) vertexData;
				if (pointer.length == 6) {
					gl.glColor3dv(pointer, 3);
				}
				gl.glVertex3dv(pointer, 0);
			}

		}

		public void vertexData(Object vertexData, Object polygonData) {
		}

		/*
		 * combineCallback is used to create a new vertex when edges intersect.
		 * coordinate location is trivial to calculate, but weight[4] may be used to
		 * average color, normal, or texture coordinate data. In this program, color
		 * is weighted.
		 */
		public void combine(double[] coords, Object[] data, //
						float[] weight, Object[] outData) {
			double[] vertex = new double[6];
			int i;

			vertex[0] = coords[0];
			vertex[1] = coords[1];
			vertex[2] = coords[2];
			for (i = 3; i < 6/* 7OutOfBounds from C! */; i++) {
				vertex[i] = weight[0] //
								* ((double[]) data[0])[i] + weight[1]
								* ((double[]) data[1])[i] + weight[2]
								* ((double[]) data[2])[i] + weight[3]
								* ((double[]) data[3])[i];
			}
			outData[0] = vertex;
		}

		public void combineData(double[] coords, Object[] data, //
						float[] weight, Object[] outData, Object polygonData) {
		}

		public void error(int errnum) {
			String estring;

			estring = glu.gluErrorString(errnum);
			System.err.println("Tessellation Error: " + estring);
			System.exit(0);
		}

		public void beginData(int type, Object polygonData) {
		}

		public void endData(Object polygonData) {
		}

		public void edgeFlag(boolean boundaryEdge) {
		}

		public void edgeFlagData(boolean boundaryEdge, Object polygonData) {
		}

		public void errorData(int errnum, Object polygonData) {
		}
	}// tessellCallBack
}// tess

