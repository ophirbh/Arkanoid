package management;

import biuoop.GUI;
import biuoop.KeyboardSensor;
import driver.HighScoresTable;

/**
 * Represent a high scores task.
 */
public class TaskHighScores implements Task<Void> {
    private HighScoresTable table;
    private KeyboardSensor sensor;
    private GUI gui;

    /**
     * Constructor.
     * @param table High scores table.
     * @param sensorr Keyboard sensor.
     * @param gui GUI.
     */
    public TaskHighScores(HighScoresTable table, KeyboardSensor sensorr, GUI gui) {
        this.table = table;
        this.sensor = sensorr;
        this.gui = gui;
    }

    @Override
    public Void run() {
        KeyPressStoppableAnimation stoppableAnimation =
                new KeyPressStoppableAnimation(this.sensor,
                        KeyboardSensor.SPACE_KEY, new HighScoresAnimation(this.table));
        AnimationRunner runner = new AnimationRunner(this.gui);
        runner.run(stoppableAnimation);
        return null;
    }
}
