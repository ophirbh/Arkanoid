package io;

import management.LevelInformation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Represents a level specification reader.
 */
public class LevelSpecificationReader {
    private static final String START_LEVEL = "START_LEVEL";
    private static final String END_LEVEL = "END_LEVEL";
    private static final String START_BLOCKS = "START_BLOCKS";
    private static final String END_BLOCKS = "END_BLOCKS";
    private static final String EMPTY_LINE = "";
    private static final String COMMENT_LINE = "#";
    private static final String KEY_VAL_DELIMITER = ":";
    private static final int KEY = 0;
    private static final int VALUE = 1;
    /**
     * Get list of level info.
     * @param reader Reader.
     * @return List of level info.
     * @throws Exception Exception.
     */
    public List<LevelInformation> fromReader(java.io.Reader reader) throws Exception {

        BufferedReader bReader = new BufferedReader(reader);
        String strLine;
        ArrayList<LevelInformation> levels = new ArrayList<LevelInformation>();

        try {
            while ((strLine = bReader.readLine()) != null)   {
                if (strLine.contains(START_LEVEL)) {
                    ParsedLevel pLevel = parseLevel(bReader);
                    if (pLevel != null) {
                        levels.add(pLevel);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            bReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return levels;
    }

    /**
     * Parse level.
     * @param bReader Buffered reader.
     * @return Parsed level.
     * @throws Exception Exception.
     */
    private ParsedLevel parseLevel(BufferedReader bReader) throws Exception {

        TreeMap<String, String> levelProperties = readLevelProperties(bReader);

        ArrayList<String> blocksPattern = readBlocks(bReader);

        readToEndOfLevel(bReader);

        return new ParsedLevel(levelProperties, blocksPattern);
    }

    /**
     * Read level properties.
     * @param bReader Buffered reader.
     * @return Level properties.
     */
    private TreeMap<String, String> readLevelProperties(BufferedReader bReader) {
        String strLine = null;
        TreeMap<String, String> levelProperties = new TreeMap<>();

        try {
            strLine = bReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (!strLine.contains(START_BLOCKS)) {

            if (!strLine.equals(EMPTY_LINE) && !strLine.contains(COMMENT_LINE)) {
                String[] parsedLine = strLine.split(KEY_VAL_DELIMITER);
                levelProperties.put(parsedLine[KEY], parsedLine[VALUE]);
            }

            try {
                strLine = bReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return levelProperties;
    }

    /**
     * Read to end of level.
     * @param bReader Buffered reader.
     */
    private void readToEndOfLevel(BufferedReader bReader) {
        String strLine = null;
        try {
            strLine = bReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (!strLine.contains(END_LEVEL)) {
            try {
                strLine = bReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Read blocks.
     * @param reader Reader.
     * @return Blocks.
     */
    private ArrayList<String> readBlocks(BufferedReader reader) {
        String strLine = null;
        ArrayList<String> blocksPattern = new ArrayList<String>();

        try {
            strLine = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (!(strLine == null) && !strLine.contains(END_BLOCKS)) {
            if (!strLine.equals(EMPTY_LINE) && !strLine.contains(COMMENT_LINE)) {
                blocksPattern.add(strLine);
            }
            try {
                strLine = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return blocksPattern;
    }

    /**
     * Get blocks.
     * @param blocksFileName Blocks file name.
     * @return Blocks.
     */
    private BufferedReader getBufferedReader(String blocksFileName) {
        BufferedReader blocksBufferedReader = null;
        try {
            FileInputStream blockDefinitionStream = new FileInputStream(blocksFileName);

            InputStreamReader blockDefinitionReader = new InputStreamReader(blockDefinitionStream);
            blocksBufferedReader = new BufferedReader(blockDefinitionReader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return blocksBufferedReader;
    }


}
