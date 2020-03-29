
import biuoop.GUI;
import driver.GameFlow;
import driver.HighScoresTable;
import io.LevelSet;
import io.LevelSpecificationReader;
import io.ParsedLevelSet;
import management.MenuAnimation;
import management.AnimationRunner;
import management.SubMenu;
import management.Task;
import management.TaskHighScores;
import management.TaskQuit;
import management.LevelInformation;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.IOException;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Represents a game.
 */
public class Ass6Game {
    private HighScoresTable table;

    /**
     * Runs a game.
     * @param args User arguments.
     * @throws Exception Exception.
     */
    public static void main(String[] args) throws Exception {

        String levelSetsFile;

        if (args.length > 0) {
            levelSetsFile = args[0];
        } else {
            levelSetsFile = "level_sets.txt";
        }

        LevelSet levelSet = getLevelSet(levelSetsFile);

        GUI gui = new GUI("Arkanoid", 800, 600);
        AnimationRunner runner = new AnimationRunner(gui);
        HighScoresTable highScoresTable = HighScoresTable.loadFromFile(new File(HighScoresTable.FILE_NAME));
        //GameFlow flow = new GameFlow(runner, gui.getKeyboardSensor(), 7, highScoresTable);
        MenuAnimation<Void> menuAnimation = new MenuAnimation<Void>(gui.getKeyboardSensor());
        Task<Void> highScores = new TaskHighScores(highScoresTable,
                gui.getKeyboardSensor(), gui);
        Task<Void> quit = new TaskQuit();

        MenuAnimation<Void> levelsMenu = new MenuAnimation<Void>(gui.getKeyboardSensor());
        SubMenu levelsSubMenu = new SubMenu(levelsMenu, runner);

        Map<String, String> levelsMap = levelSet.getLevels();
//        for (String levelKey: levelsMap.keySet()) {
//            levelsMenu.addSelection(levelKey, levelsMap.get(levelKey),
//                    (Task<Void>) () -> {
//                        String levelFile = levelSet.getLevelFile(levelKey);
//                        List<LevelInformation> levels = getLevelInformations(levelFile);
//
//                        flow.runLevels(levels);
//                        return null;
//                    });
//        }


        for (String levelKey : levelsMap.keySet()) {
            Task<Void> t = new Task<Void>() {
                @Override
                public Void run() throws Exception {
                    String levelFile = levelSet.getLevelFile(levelKey);
                    List<LevelInformation> levels = getLevelInformations(levelFile);
                    GameFlow flow = new GameFlow(runner, gui.getKeyboardSensor(), 7, highScoresTable);
                    flow.runLevels(levels);
                    return null;
                }
            };
            levelsMenu.addSelection(levelKey, levelsMap.get(levelKey), t);
        }
        //*//

        menuAnimation.addSelection("s", "Start game", levelsSubMenu);
        menuAnimation.addSelection("h", "Show high scores", highScores);
        menuAnimation.addSelection("q", "Quit game", quit);

        while (true) {

            runner.run(menuAnimation);
            Task<Void> selectedTask = (Task<Void>) menuAnimation.getStatus();
            selectedTask.run();
            menuAnimation.resetStop();
        }

    }

    /**
     * Get the level sets.
     * @param levelSetsFile the level sets file.
     * @return The level sets.
     * @throws IOException IO exception.
     */
    private static LevelSet getLevelSet(String levelSetsFile) throws IOException {
        InputStream fileStream = ClassLoader.getSystemClassLoader().getResourceAsStream(levelSetsFile);
        InputStreamReader reader = new InputStreamReader(fileStream);

        return new ParsedLevelSet(reader);
    }

    /**
     * Get level info list.
     * @param levelsFile The levels file.
     * @return The level info list..
     */
    private static List<LevelInformation> getLevelInformations(String levelsFile) {
        InputStream fileStream = ClassLoader.getSystemClassLoader().getResourceAsStream(levelsFile);
        InputStreamReader reader = new InputStreamReader(fileStream);

        List<LevelInformation> levels = null;
        try {
            LevelSpecificationReader levelsReader = new LevelSpecificationReader();
            levels = levelsReader.fromReader(reader);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
        return levels;
    }
}
