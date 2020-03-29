package management;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * Represents a key press stoppable animation.
 */
public class KeyPressStoppableAnimation implements Animation {
    private KeyboardSensor sens;
    private String key;
    private boolean stop;
    private Animation decoratedAnimation;
    private boolean isAlreadyPressed;

    /**
     * Constructor.
     * @param sensor Keyboard sensor.
     * @param k Key.
     * @param animation Animation.
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String k,
                                      Animation animation) {
        this.sens = sensor;
        this.key = k;
        this.decoratedAnimation = animation;
        this.stop = false;
        this.isAlreadyPressed = true;

    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        this.decoratedAnimation.doOneFrame(d, dt);
        if (this.sens.isPressed(this.key)) {
            if (!this.isAlreadyPressed) {
                this.stop = true;
            }
        }
        if (!this.sens.isPressed(this.key)) {
            this.isAlreadyPressed = false;
        }
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}

