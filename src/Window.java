
/**
 * to do:
 * -stopwatch to rotate and to cycle variables
 *
 */
import java.awt.Color;
import java.util.ArrayList;
import javax.media.opengl.GLCapabilities;

public class Window extends JoglFramework {

  Vector3D[][] verticies;
  Vector3D[][] verticies2DA;
  Vector3D[][] verticies2DB;
  double m, n1, n2, n3;
  double maxPhi = (float) Math.PI;
  double maxTheta = (float) Math.PI * 2;
  int phiSteps = 50;
  int thetaSteps = 100;
  int numVertices = phiSteps * thetaSteps;
  double phiStepSize = maxPhi / (phiSteps - 1);
  double thetaStepSize = maxTheta / (thetaSteps - 1);
  double phi;
  double theta;
  double[] r1 = new double[phiSteps * thetaSteps];
  double[] r2 = new double[phiSteps * thetaSteps];
  double t1, t2;
  double a = 1, b = 1;
  float x, y, z;
  double r1Max, r2Max;
  double rotationTheta;
  double[][] vertRadius;
  int rotX = -60;
  int rotY = -60;
  int zoom = 100;
  double counter = 0;
  ArrayList points = new ArrayList();
  SinCounter[] sinCounters;

  Window(GLCapabilities caps) {
    super(caps);
    m = 2;
    n1 = 5;
    n2 = 2;
    n3 = 2;
    verticies = new Vector3D[phiSteps][thetaSteps];
    verticies2DA = new Vector3D[phiSteps][thetaSteps];
    verticies2DB = new Vector3D[phiSteps][thetaSteps];
    vertRadius = new double[phiSteps][thetaSteps];
    sinCounters = new SinCounter[8];
    for (int i = 0; i < sinCounters.length; i++) {
      sinCounters[i] = new SinCounter(0.001d*(i+1), 10d, 11d);
    }
    stopwatchStart();
    updateShape();
  }

  public void draw() {
    for (int i = 0; i < sinCounters.length; i++) {
      sinCounters[i].step();
    }
    updateShape();
    draw.setBackground(1, 1, 1);
    //draw.setBackground(0, 0, 0);
    draw.beginTranslate((float) width / 2, (float) height / 2, 0f);
    draw.rotate(0f, 0f, 0f, rotX, rotY, 0);
    //->setligths in draw
    for (int i = 0; i < phiSteps - 1; i++) {
      for (int j = -1; j < thetaSteps; j++) {

        if (j == -1) {
          //points.add(verticies[i][1]);
          //points.add(verticies[i][0]);
          points.add(verticies[i + 1][1]);
          points.add(verticies[i + 1][0]);
        } else if (j < thetaSteps - 2) {
          points.add(verticies[i][j]);
          points.add(verticies[i][j + 1]);
          points.add(verticies[i + 1][j]);
          points.add(verticies[i + 1][j + 1]);
        } else {
          points.add(verticies[i][j]);
          points.add(verticies[i][0]);
          points.add(verticies[i + 1][j]);
          points.add(verticies[i + 1][0]);

        }
      }
      draw.drawTriStrips(points, false, true);
      points.clear();
    }
    draw.endRotate();
    draw.endTranslate();

    //draw.textRenderer2.setColor(Color.lightGray);
    //draw.drawText3D("fps: " + getFPS(), 2, 20, 200, 0);
  }

  private void updateShape() {
    int count = 0;

    for (int i = 0; i < phiSteps; i++) {
      for (int j = 0; j < thetaSteps; j++) {

        phi = i * phiStepSize - (Math.PI / 2d);
        theta = j * thetaStepSize - (Math.PI);
        r1Max = 0;
        r2Max = 0;
        //m = 12;
        m = sinCounters[0].getCount();
        //n1 = Math.sin(stopwatchGetTime()*10d)*10d+1;
        n1 = sinCounters[1].getCount();
        //n1 = 2;
        n2 = sinCounters[2].getCount();
        n3 = sinCounters[3].getCount();

        t1 = Math.cos(m * theta / 4d) / a;
        t1 = Math.abs(t1);
        t1 = Math.pow(t1, n2);
        t2 = Math.sin(m * theta / 4d) / b;
        t2 = Math.abs(t2);
        t2 = Math.pow(t2, n3);
        r1[count] = Math.pow(Math.abs(t1 + t2), -1 / n1);
        if (r1[count] > r1Max) {
          r1Max = r1[count];
        }

        m = sinCounters[4].getCount();
        n1 = sinCounters[5].getCount();
        n2 = sinCounters[6].getCount();
        n3 = sinCounters[7].getCount();

        t1 = (Math.cos(m * phi / 4d) / a);
        t1 = Math.abs(t1);
        t1 = Math.pow(t1, n2);
        t2 = (Math.sin(m * phi / 4d) / b);
        t2 = Math.abs(t2);
        t2 = Math.pow(t2, n3);
        r2[count] = Math.pow(Math.abs(t1 + t2), -1 / n1);
        if (r2[count] > r2Max) {
          r2Max = r2[count];
        }

        count++;
      }
    }

    int scaleFactor = 14;
    count = 0;
    for (int i = 0; i < phiSteps; i++) {
      for (int j = 0; j < thetaSteps; j++) {
        phi = i * phiStepSize - (Math.PI / 2d);
        theta = j * thetaStepSize - (Math.PI);
        //float r1Norm = norm(r1[count], 0, r1Max + 0.1);
        //float r2Norm = norm(r2[count], 0, r2Max + 0.1);
        //float r1Norm = (float) r1[count];
        //float r2Norm = (float) r2[count];
        double r1Norm = r1[count] / (r1Max + 0.1) * scaleFactor;
        double r2Norm = r2[count] / (r2Max + 0.1) * scaleFactor;
        //float r1Norm = r1[count]+0.1;
        //float r2Norm = r2[count]+0.1;
        verticies2DA[i][j] = new Vector3D(
                (float) (r1Norm * Math.cos(theta)),
                (float) (r1Norm * Math.sin(theta)),
                0f);
        verticies2DB[i][j] = new Vector3D(
                (float) (r2Norm * Math.cos(phi)),
                (float) (r2Norm * Math.sin(phi)),
                0f);

        //r1Norm = scaleFactor;
        //r2Norm = scaleFactor;
        x = (float) (r1Norm * Math.cos(theta) * r2Norm * Math.cos(phi));
        y = (float) (r1Norm * Math.sin(theta) * r2Norm * Math.cos(phi));
        z = (float) (r2Norm * Math.sin(phi)) * scaleFactor;
        //largeur, hauteur, profondeur
        verticies[i][j] = new Vector3D(x, y, z);
        //verticies[i][j] = Vector3D.mult(new Vector3D(x, y, z), 100);
        //***********************************************************
        //println(x+" "+y+" "+z+" "+r2[count]);
        vertRadius[i][j] = r2Norm;
        //System.out.println(vertRadius[i][j]);
        count++;
      }
    }

    //repaint();
  }

  void onCursorMove(Cursor cursor) {
  }

  void onCursorPress(Cursor cursor) {
  }

  void onCursorRelease(Cursor cursor) {
  }

  void onCursorDrag(Cursor cursor) {
    rotY += cursor.prevX - cursor.x;
    rotX -= cursor.prevY - cursor.y;
  }

  void onCursorClick(Cursor cursor) {
  }
}
