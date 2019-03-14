/**
 * A simple sinusoidal counter. 
 */
public class SinCounter {

  double freq = 0;
  double amp = 0;
  double counter = 0;
  double offset = 0;

  public SinCounter(double _freq, double _amp, double _offset) {
    freq = _freq;
    amp = _amp;
    offset = _offset;
  }

  public void start() {
  }

  public void stop() {
  }

  public void step(){
    counter += freq;
  }

  public double getCount() {
    return Math.sin(counter)*amp+offset;
  }
}
