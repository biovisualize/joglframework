
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import javax.media.opengl.GLCapabilities;
import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(
						new Runnable() {

							public void run() {
								GLCapabilities caps = new GLCapabilities();
								caps.setDoubleBuffered(true);
								caps.setHardwareAccelerated(true);

								Window gW = new Window(caps);
								JFrame frame = new JFrame("Generator");
								frame.setPreferredSize(new Dimension(Config.width, Config.height));
								frame.setLocation(0, 0);
								frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
								frame.setVisible(true);

								Container pane = frame.getContentPane();
								pane.setLayout(new BorderLayout());
								pane.add(gW, BorderLayout.CENTER);

								frame.pack();
							}
						});
	}
}
