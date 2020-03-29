package management;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import java.awt.Color;

/**
 * Represents an end screen.
 */
public class EndScreen implements Animation {
    private KeyboardSensor sensor;
    private boolean playerWon;
    private int totalScore;
    private boolean shouldStop;

    /**
     * Create a new end screen object.
     * @param playerWins True if player won, false otherwise.
     * @param score The total game score.
     */
    public EndScreen(boolean playerWins, int score) {
        this.playerWon = playerWins;
        this.totalScore = score;
        this.shouldStop = false;
    }

    /**
     * A very simple animation, that will display a screen with the message:
     * paused -- press space to continue until a key is pressed.
     * @param d The draw surface.
     * @param dt The amount of seconds passed since the last call.
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.BLACK);
        if (this.playerWon) {
            d.drawText(50, 200, "You Win! Your score is "
                    + String.valueOf(this.totalScore), 40);
        } else {
            d.drawText(50, 200, "Game Over. Your score is "
                    + String.valueOf(this.totalScore), 40);
        }
        d.drawText(100, 500, "Press space to exit.", 40);
    }

    /**
     * Returns true if animation should stop.
     * @return True if animation should stop, false otherwise.
     */
    @Override
    public boolean shouldStop() {
        return this.shouldStop;
    }
}
