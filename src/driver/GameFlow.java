package driver;

import biuoop.DialogManager;
import biuoop.KeyboardSensor;
import management.Counter;
import management.AnimationRunner;
import management.LevelInformation;
import management.EndScreen;
import management.KeyPressStoppableAnimation;
import management.HighScoresAnimation;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Represents a game flow.
 */
public class GameFlow {
    public static final int LIVES = 7;
    public static final int START_SCORE = 0;
    private Counter remainingLives;
    private Counter score;
    private AnimationRunner animRunner;
    private KeyboardSensor keySensor;
    private HighScoresTable highScoresTable;

    /**
     * Create a new game flow.
     * @param ar Animation runner.
     * @param ks Keyboard sensor.
     * @param lives Number of lives.
     * @param table High scores table.
     */
    public GameFlow(AnimationRunner ar, KeyboardSensor ks, int lives, HighScoresTable table) {
        this.remainingLives = new Counter();
        this.remainingLives.increase(lives);
        this.score = new Counter();
        this.animRunner = ar;
        this.keySensor = ks;
        this.highScoresTable = table;
    }

    /**
     * Runs the argument levels.
     * @param levels The argument levels.
     * @throws Exception Exception.
     */
    public void runLevels(List<LevelInformation> levels) throws Exception {
        boolean playerWon = false;

        for (LevelInformation levelInfo : levels) {

            GameLevel level = new GameLevel(levelInfo, this.keySensor, this.animRunner,
                    this.remainingLives, this.score);

            level.initialize();

            while ((level.getRemainingLives() > 0) && (level.getRemainingBlocks() > 0)) {
                level.playOneTurn();
            }

            if (this.remainingLives.getValue() == 0) {
                break;
            }
        }
        if (this.remainingLives.getValue() > 0) {
            playerWon = true;
        }
        EndScreen endScreen = new EndScreen(playerWon, this.score.getValue());
        KeyPressStoppableAnimation keyPressStoppableAnimation =
                new KeyPressStoppableAnimation(this.keySensor,
                        KeyboardSensor.SPACE_KEY, endScreen);
        this.animRunner.run(keyPressStoppableAnimation);
        int rank = this.highScoresTable.getRank(this.score.getValue());
        if (rank <= this.highScoresTable.size()) {
            this.addHighScore(this.score.getValue());
        }
        //savetable
        HighScoresAnimation highScoresAnimation =
                new HighScoresAnimation(this.highScoresTable);
        keyPressStoppableAnimation = new KeyPressStoppableAnimation(this.keySensor,
                KeyboardSensor.SPACE_KEY, highScoresAnimation);
        this.animRunner.run(keyPressStoppableAnimation);
         this.resetFlow();
    }

    /**
     * Returns the high scores table.
     * @return The high scores table.
     */
    public HighScoresTable getHighScoresTable() {
        File highScoresTableFile = new File(HighScoresTable.FILE_NAME);
        HighScoresTable hScoresTable = new HighScoresTable();
        if (highScoresTableFile.exists() && !highScoresTableFile.isDirectory()) {
            hScoresTable = HighScoresTable.loadFromFile(highScoresTableFile);
        } else {
            try {
                hScoresTable.save(highScoresTableFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return hScoresTable;
    }

    /**
     * Add high score.
     * @param scr The new score.
     */
    public void addHighScore(int scr) {
        DialogManager manager = this.animRunner.getGui().getDialogManager();
        String playerName = manager.showQuestionDialog("High Scores!!!",
                "What is your name?", "Your name...");
        ScoreInfo scoreInfo = new ScoreInfo(playerName, scr);
        this.highScoresTable.add(scoreInfo);
        try {
            this.highScoresTable.save(new File(HighScoresTable.FILE_NAME));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Reset the game flow.
     */
    public void resetFlow() {
        this.remainingLives.setValue(7);
        this.score.setValue(START_SCORE);
    }
}
