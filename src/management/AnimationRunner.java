package management;

import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;

/**
 * Represent an animation runner.
 */
public class AnimationRunner {
    private GUI gui;
    private int framesPerSecond;
    private Sleeper sleeper;
    private double dt;

    public static final int MILLI_SECONDS_PER_SECOND = 1000;

    /**
     * Creates a new animation runner.
     * @param gui The GUI to run.
     */
    public AnimationRunner(GUI gui) {
        this.gui = gui;
        this.framesPerSecond = 60;
        this.sleeper = new Sleeper();
        this.dt = 1.0 / this.framesPerSecond;
    }

    /**
     * Runs an animation object it recieves as argument.
     * @param animation Animation object.
     */
    public void run(Animation animation) {
        int millisecondsPerFrame = MILLI_SECONDS_PER_SECOND
                / this.framesPerSecond;
        while (!animation.shouldStop()) {
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = gui.getDrawSurface();
            animation.doOneFrame(d, this.dt);
            gui.show(d);
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }

    /**
     * Get the animation's GUI.
     * @return The animation's GUI.
     */
    public GUI getGui() {
        return this.gui;
    }

    /**
     * Returns the amount of seconds passed since the last call.
     * @return the amount of second passed since the last call.
     */
    public double getDt() {
        return this.dt;
    }
}
