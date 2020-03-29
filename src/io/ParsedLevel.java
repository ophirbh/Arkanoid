package io;

import management.LevelInformation;
import sprites.Block;
import sprites.Velocity;
import sprites.Background;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import sprites.Sprite;
import sprites.Fill;

/**
 * Represents a parsed level.
 */
public class ParsedLevel implements LevelInformation {
    private Map<String, String> levelProperties;
    private ArrayList<String> blocksPattern;
    private boolean levelIsLegal = true;

    private static final String LEVEL_NAME = "level_name";
    private static final String BALL_VELOCITIES = "ball_velocities";
    private static final String BACKGROUND = "background";
    private static final String PADDLE_SPEED = "paddle_speed";
    private static final String PADDLE_WIDTH = "paddle_width";
    private static final String BLOCK_DEFINITIONS = "block_definitions";
    private static final String BLOCKS_START_X = "blocks_start_x";
    private static final String BLOCKS_START_Y = "blocks_start_y";
    private static final String ROW_HEIGHT = "row_height";
    private static final String NUM_BLOCKS = "num_blocks";

    private static final Set<String> REQUIRED_KEYS = new HashSet<>();

    /**
     * Constructor.
     * @param level Level.
     * @param blocks Blocks.
     * @throws Exception Exception.
     */
    public ParsedLevel(Map<String, String> level, ArrayList<String> blocks) throws Exception {
        REQUIRED_KEYS.add(LEVEL_NAME);
        REQUIRED_KEYS.add(BALL_VELOCITIES);
        REQUIRED_KEYS.add(BACKGROUND);
        REQUIRED_KEYS.add(PADDLE_SPEED);
        REQUIRED_KEYS.add(PADDLE_WIDTH);
        REQUIRED_KEYS.add(BLOCK_DEFINITIONS);
        REQUIRED_KEYS.add(BLOCKS_START_X);
        REQUIRED_KEYS.add(BLOCKS_START_Y);
        REQUIRED_KEYS.add(ROW_HEIGHT);
        REQUIRED_KEYS.add(NUM_BLOCKS);

        this.blocksPattern = blocks;
        this.levelProperties = level;

        validateDefinitions(levelProperties);
    }

    /**
     * Validates definitions.
     * @param defs Definitions
     * @throws Exception Exception.
     */
    private static void validateDefinitions(Map<String, String> defs) throws Exception {
        boolean isFullyDefined = defs.keySet().containsAll(REQUIRED_KEYS);

        if (!isFullyDefined) {
            throw new Exception("The level definition file has some missing fields!");
        }
    }

    @Override
    public int numberOfBalls() {
        List<Velocity> velocities = initialBallVelocities();

        int ballsCount = velocities.size();
        return ballsCount;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        String[] velocityPatterns = this.levelProperties.get(BALL_VELOCITIES).split(" ");

        List<Velocity> velocities = new ArrayList<>();

        for (String pattern: velocityPatterns) {
            String[] parts = pattern.split(",");

            double angle = Double.valueOf(parts[0]);
            double speed = Double.valueOf(parts[1]);

            Velocity velocity = Velocity.fromAngleAndSpeed(angle, speed);

            velocities.add(velocity);
        }

        return velocities;
    }

    @Override
    public int paddleSpeed() {
        if (this.levelProperties.containsKey(PADDLE_SPEED)) {
            return Integer.valueOf(this.levelProperties.get(PADDLE_SPEED));
        }
        this.levelIsLegal = false;
        return 0;
    }

    @Override
    public int paddleWidth() {
        if (this.levelProperties.containsKey(PADDLE_WIDTH)) {
            return Integer.valueOf(this.levelProperties.get(PADDLE_WIDTH));
        }
        this.levelIsLegal = false;
        return 0;
    }

    @Override
    public String levelName() {
        if (this.levelProperties.containsKey(LEVEL_NAME)) {
            return this.levelProperties.get(LEVEL_NAME);
        }
        this.levelIsLegal = false;
        return null;
    }

    @Override
    public Sprite getBackground() {
        if (this.levelProperties.containsKey(BACKGROUND)) {
            String fillPattern = levelProperties.get(BACKGROUND);
            Fill fill = FillParser.parseFill(fillPattern);

            Background background = new Background(fill);
            return background;

        } else {
            this.levelIsLegal = false;
            return null;
        }
    }

    /**
     * Get blocks start X.
     * @return Blocks start X.
     */
    private int getBlocksStartX() {
        int value = Integer.valueOf(levelProperties.get(BLOCKS_START_X));
        return value;
    }

    /**
     * Get blocks start Y.
     * @return Blocks start Y.
     */
    private int getBlocksStartY() {
        int value = Integer.valueOf(levelProperties.get(BLOCKS_START_Y));
        return value;
    }

    /**
     * Get row height.
     * @return Row height.
     */
    private int getRowHeight() {
        int value = Integer.valueOf(levelProperties.get(ROW_HEIGHT));
        return value;
    }

    @Override
    public List<Block> blocks() throws Exception {
        String definitionsFile = levelProperties.get(BLOCK_DEFINITIONS);

        InputStream fileStream = ClassLoader.getSystemClassLoader().getResourceAsStream(definitionsFile);
        InputStreamReader fileReader = new InputStreamReader(fileStream);
        //Reader fileReader = getBufferedReader(definitionsFile);

        BlocksDefinitionReader definitionReader = new BlocksDefinitionReader();
        BlocksFromSymbolsFactory blocksFactory = definitionReader.fromReader(fileReader);

        int posX = getBlocksStartX();
        int posY = getBlocksStartY();
        int rowHeight = getRowHeight();

        List<Block> blocksList = new ArrayList<>();

        for (String blockLine: this.blocksPattern) {
            for (int symbolIndex = 0; symbolIndex < blockLine.length(); symbolIndex++) {
                String symbol = String.valueOf(blockLine.charAt(symbolIndex));
                if (blocksFactory.isSpaceSymbol(symbol)) {
                    posX += blocksFactory.getSpaceWidth(symbol);
                } else if (blocksFactory.isBlockSymbol(symbol)) {
                    Block block = blocksFactory.getBlock(symbol, posX, posY);
                    blocksList.add(block);
                    posX += block.getWidth();
                }
            }

            posX = getBlocksStartX();
            posY += rowHeight;
        }

        return blocksList;
    }

    @Override
    public int numberOfBlocksToRemove() {

        int value = Integer.valueOf(levelProperties.get(NUM_BLOCKS));
        return value;
    }
}
