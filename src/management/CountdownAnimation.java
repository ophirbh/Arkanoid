package management;

import biuoop.DrawSurface;
import biuoop.Sleeper;

import java.awt.Color;

/**
 * The CountdownAnimation will display the given gameScreen,
 * for numOfSeconds seconds, and on top of them it will show
 * a countdown from countFrom back to 1, where each number will
 * appear on the screen for (numOfSeconds / countFrom) secods, before
 * it is replaced with the next one.
 */
public class CountdownAnimation implements Animation {
    private double numberOfSeconds;
    private int cntFrom;
    private SpriteCollection gameScreen;
    private Counter timeLeftInSeconds;
    private boolean shouldStop;

    /**
     * Creates a countdown animation.
     * @param numOfSeconds The number of seconds to count.
     * @param countFrom The number to count from.
     * @param gameScreen The game screen.
     * @param levelInfo The level informaion.
     */
    public CountdownAnimation(double numOfSeconds, int countFrom,
                              SpriteCollection gameScreen, LevelInformation levelInfo) {
        this.numberOfSeconds = numOfSeconds;
        this.cntFrom = countFrom;
        this.gameScreen = gameScreen;
        this.timeLeftInSeconds = new Counter();
        this.shouldStop = false;
        this.timeLeftInSeconds.increase((int) this.numberOfSeconds);
    }

    /**
     * Do one fram of countdown animation.
     * @param d The draw surface.
     * @param dt The amount of seconds passed since the last call.
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        this.gameScreen.drawAllOn(d);
        if (!this.shouldStop()) {
            d.setColor(Color.BLACK);
            d.drawText(365, 380, String.valueOf(this.timeLeftInSeconds.getValue()),
                    120);
            d.setColor(Color.WHITE);
            d.drawText(380, 380, String.valueOf(this.timeLeftInSeconds.getValue()),
                    80);
        }
        Sleeper sleeper = new Sleeper();
        sleeper.sleepFor(650);
        timeLeftInSeconds.decrease(1);
    }

    /**
     * Return true if animation should stop.
     * @return True if animation should stop.
     */
    @Override
    public boolean shouldStop() {
        return this.timeLeftInSeconds.getValue() < 0;
    }
}
