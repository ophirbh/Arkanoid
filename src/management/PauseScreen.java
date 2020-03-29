package management;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * Represents a pause screen in the game.
 */
public class PauseScreen implements Animation {
    private KeyboardSensor keyboard;
    private boolean stop;

    /**
     * Create a new pause screen object, that will respond to a keyboard
     * press.
     */
    public PauseScreen() {
        this.stop = false;
    }

    /**
     * A very simple animation, that will display a screen with the message:
     * paused -- press space to continue until a key is pressed.
     * @param d The draw surface.
     * @param dt The amount of seconds passed since the last call.
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        d.drawText(10, d.getHeight() / 2,
                "paused -- press space to continue", 32);
    }

    /**
     * Returns true if animation should stop.
     * @return True if animation should stop, false otherwise.
     */
    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
