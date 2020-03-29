package management;

import driver.GameFlow;
import java.util.List;

public class TaskRunGame implements Task<Void> {
    private GameFlow flow;
    private List<LevelInformation> levels;

    /**
     * Run game.
     * @param flow The game flow.
     * @param levels The levels.
     */
    public TaskRunGame(GameFlow flow, List<LevelInformation> levels) {
        this.flow = flow;
        this.levels = levels;
    }

    /**
     * Run game.
     * @param flow The game flow.
     * @param key Key.
     */
    public TaskRunGame(GameFlow flow, String key) {
        this.flow = flow;
        this.levels = levels;
    }

    @Override
    public Void run() throws Exception {
        try {
            this.flow.runLevels(this.levels);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }

        return null;
    }
}
