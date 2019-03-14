/**
 * A simple timer also calculating fps.
 */
public class StopWatch {

    /*
     * Time passed calculation
     */
    private long timePassed = 0;
    private long lastTime = -1;      //Previous time

    /*
     * FPS calculation
     */
    private float fps = 0;
    private int frames = 0;
    private long firstFrameTime = 0;
    public int FPS_REFRESH_TIME = 500;
    public boolean enabled = true;
    public boolean isFPSEnabled = true;

    /*
     * Stopwatch
     */
    private long stopwatchTimePassed = 0;
    private long stopwatchLastTime = -1;
    private boolean stopwatchIsStarted = false;

    /*************************
     * NEW *   updateTime    *
     *******************************************************************************
     * This method calculates the time that OpenGl takes to draw frames.           *
     * This time should be used to increase the movement of the objects of the     *
     * scene.                                                                      *
     *******************************************************************************/
    public void updateTime() {
        //Counter enabled ?
        if (!enabled) {
            return;
        }

        if (lastTime == -1) {
            //Initialization of the counter
            lastTime = System.currentTimeMillis();
            timePassed = 0;

            //Initialization for FPS calculation
            fps = 0;
            frames = 0;
            firstFrameTime = lastTime;

            //Initialization for stopwatch
            stopwatchLastTime = System.currentTimeMillis();
            stopwatchTimePassed = 0;
        } else {
            //Get the current time
            long currentTime = System.currentTimeMillis();
            //Time passed
            timePassed = currentTime - lastTime;
            //Stopwatch
            if (stopwatchIsStarted) {
                stopwatchTimePassed = currentTime - stopwatchLastTime;
            }
            //Update last time, it is now the current for next frame calculation
            lastTime = currentTime;

            //FPS
            if (isFPSEnabled) {
                //update frame count for the fps counter
                frames++;

                //Calculate fps
                long dt = currentTime - firstFrameTime;
                if (dt >= FPS_REFRESH_TIME) {
                    fps = (float) (1000 * frames) / (float) dt;
                    frames = 0;
                    firstFrameTime = currentTime;
                }
            }
        }
    }

    /**
     * Get the time to draw last frame
     * @return the time in milliseconds that the last frame takes to be drawn
     */
    public long getSPF() {
        return timePassed;
    }

    /**
     * @return the number of frames per seconds
     * @see #isFPSEnabled
     */
    public float getFPS() {
        return fps;
    }

    public void start() {
        stopwatchIsStarted = true;
        stopwatchLastTime = System.currentTimeMillis();
        stopwatchTimePassed = 0;
    }

    public void stop() {
        stopwatchIsStarted = false;
        stopwatchLastTime = -1;
    }

    public long getTime() {
        return stopwatchTimePassed;
    }
}
