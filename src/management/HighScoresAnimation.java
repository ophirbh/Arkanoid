package management;
import biuoop.DrawSurface;
import driver.HighScoresTable;
import driver.ScoreInfo;
import java.awt.Color;

/**
 * Represents a high scores animation.
 */
public class HighScoresAnimation implements Animation {
    private HighScoresTable table;
    private boolean stop;

    /**
     * Constructor.
     * @param scores Scores.
     */
    public HighScoresAnimation(HighScoresTable scores) {
        this.table = scores;
        this.stop = false;
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.BLUE);
        java.util.List<ScoreInfo> scores = table.getHighScores();
        for (int i = 0; i < scores.size(); i++) {
            d.drawText(50, 25 + (i * 35), String.valueOf(i + 1)
                    + ") Player Name: " + String.valueOf(this.table.getHighScores().
                    get(i).getName()) + "     Score: " + String.valueOf(this.table.
                    getHighScores().get(i).getScore()), 20);
        }
        d.setColor(Color.BLACK);
        d.drawText(50, 500, "Press Space to continue...", 50);
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
