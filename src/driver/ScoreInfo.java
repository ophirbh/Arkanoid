package driver;

import java.io.Serializable;

/**
 * Represents a score information.
 */
public class ScoreInfo implements Serializable {
    private String name;
    private int score;

    /**
     * Create a new score information object.
     *
     * @param name  The player's name.
     * @param score The player's score.
     */
    public ScoreInfo(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * Get the player's name.
     *
     * @return The player's name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the player's score.
     *
     * @return The player's score.
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Return a cloned score information object.
     * @return Cloned score information object.
     */
    @Override
    public ScoreInfo clone() {
        ScoreInfo info = new ScoreInfo(this.getName(), this.getScore());
        return info;
    }

}
