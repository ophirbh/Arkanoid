package management;


import biuoop.DrawSurface;

/**
 * Represents an animation object.
 */
public interface Animation {
    /**
     * Do one frame of the animation.
     * @param d The draw surface.
     * @param dt the amount of seconds passed since the last call.
     */
    void doOneFrame(DrawSurface d, double dt);

    /**
     * Checks game stopping condition.
     * @return True if should stop.
     */
    boolean shouldStop();
}
