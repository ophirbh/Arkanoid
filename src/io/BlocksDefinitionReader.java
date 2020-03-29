package io;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;

/**
 * Represents a block definitions reader.
 */
public class BlocksDefinitionReader {
    private static final String EMPTY_LINE = "";
    private static final String COMMENT_LINE = "#";
    private static final String BLOCK_DEFINITIONS = "bdef";
    private static final String DEFAULT_VALUES = "default";
    private static final String SPACER_DEFINITIONS = "sdef";
    private static final String SYMBOL = "symbol";
    private static final String HEIGHT = "height";
    private static final String WIDTH = "width";
    private static final String HIT_POINTS = "hit_points";
    private static final String FILL = "fill";
    private static final String FILL_1 = "fill-1";
    private static final String STROKE = "stroke";
    private static final String KEY_VAL_DELIMITER = ":";
    private static final String FIELD_DELIMITER = " ";

    private static final Set<String> REQUIRED_KEYS = new HashSet<>();

    /**
     * Constructor.
     */
    public BlocksDefinitionReader() {
        REQUIRED_KEYS.add(SYMBOL);
        REQUIRED_KEYS.add(HEIGHT);
        REQUIRED_KEYS.add(WIDTH);
        REQUIRED_KEYS.add(HIT_POINTS);
        //REQUIRED_KEYS.add(FILL);
        //REQUIRED_KEYS.add(STROKE);
    }

    /**
     * Blocks from symbols factory.
     * @param reader The reader.
     * @return Blocks factory.
     * @throws Exception Exception.
     */
    public static BlocksFromSymbolsFactory fromReader(java.io.Reader reader) throws Exception {
        BufferedReader bReader = new BufferedReader(reader);
        String strLine;

        Map<String, BlockCreator> blockCreators = new HashMap<>();
        Map<String, Integer> spacerWidths = new HashMap<>();
        Map<String, String> defaultValues = new HashMap<>();

        try {
            while ((strLine = bReader.readLine()) != null) {
                if (strLine.startsWith(DEFAULT_VALUES)) {
                    readLineDefinitions(strLine, defaultValues, DEFAULT_VALUES);

                } else if (strLine.startsWith(BLOCK_DEFINITIONS)) {
                    Map<String, String> blockDefs = new HashMap<>();

                    readLineDefinitions(strLine, blockDefs, BLOCK_DEFINITIONS);

                    addDefaultDefinitionsToBlock(defaultValues, blockDefs);

                    validateBlockDefinitions(blockDefs);

                    addBlockCreator(blockCreators, blockDefs);

                } else if (strLine.startsWith(SPACER_DEFINITIONS)) {
                    Map<String, String> spacerDefs = new HashMap<>();

                    readLineDefinitions(strLine, spacerDefs, SPACER_DEFINITIONS);

                    addSpacerWidths(spacerWidths, spacerDefs);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        BlocksFromSymbolsFactory blocksFactory = new BlocksFromSymbolsFactory(blockCreators, spacerWidths);
        return blocksFactory;
    }

    /**
     * Add spacer widths.
     * @param spacerWidths The spacer widths.
     * @param spacerDefs The spacer definitions.
     */
    private static void addSpacerWidths(Map<String, Integer> spacerWidths, Map<String, String> spacerDefs) {
        String symbol = spacerDefs.get(SYMBOL);
        int spacerWidth = Integer.valueOf(spacerDefs.get(WIDTH));
        spacerWidths.put(symbol, spacerWidth);
    }

    /**
     * Add block creators.
     * @param blockCreators The block creators.
     * @param blockDefs The block definitions.
     */
    private static void addBlockCreator(Map<String, BlockCreator> blockCreators, Map<String, String> blockDefs) {
        String blockSymbol = blockDefs.get(SYMBOL);
        BlockCreator creator = new ParsedBlockCreator(blockDefs);

        blockCreators.put(blockSymbol, creator);
    }

    /**
     * Validate block definitions.
     * @param blockDefs The block definitions.
     * @throws Exception Exception.
     */
    private static void validateBlockDefinitions(Map<String, String> blockDefs) throws Exception {
        boolean isFullyDefined = blockDefs.keySet().containsAll(REQUIRED_KEYS)
                && ((blockDefs.keySet().contains(FILL)) || (blockDefs.keySet().contains(FILL_1)));
        if (!isFullyDefined) {
            throw new Exception("The blocks definition file has some missing fields!");
        }
    }

    /**
     * Add default definitions to block.
     * @param defaultValues The default values.
     * @param blockDefs The block definitions.
     */
    private static void addDefaultDefinitionsToBlock(Map<String, String> defaultValues, Map<String, String> blockDefs) {
        for (String defaultKey: defaultValues.keySet()) {
            if (!blockDefs.containsKey(defaultKey)) {
                blockDefs.put(defaultKey, defaultValues.get(defaultKey));
            }
        }
    }

    /**
     * Read line definitions.
     * @param strLine Line.
     * @param definitionsTarget Definitions target.
     * @param definitionName Definitions name.
     */
    private static void readLineDefinitions(String strLine,
                                            Map<String, String> definitionsTarget,
                                            String definitionName) {
        String[] fields = strLine.split(FIELD_DELIMITER);

        for (String field : fields) {
            if (!field.contains(definitionName)) {
                String[] defs = field.split(KEY_VAL_DELIMITER);
                String key = defs[0];
                String value = defs[1];

                definitionsTarget.put(key, value);
            }
        }
    }
}
