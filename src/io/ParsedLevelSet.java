package io;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a parsed level set.
 */
public class ParsedLevelSet implements LevelSet {
    private Map<String, String> levelFiles;
    private Map<String, String> levelDescription;

    /**
     * Constructor.
     * @param reader Reader.
     * @throws IOException Exception.
     */
    public ParsedLevelSet(Reader reader) throws IOException {
        this.levelDescription = new HashMap<>();
        this.levelFiles = new HashMap<>();

        LineNumberReader lineReader = new LineNumberReader(reader);

        String line = lineReader.readLine();
        String lastKey = null;
        while (line != null) {

            if (lineReader.getLineNumber() % 2 == 0) {
                this.levelFiles.put(lastKey, line);
            } else {
                String[] keyValuePair = line.split(":");
                lastKey = keyValuePair[0];
                String description = keyValuePair[1];

                levelDescription.put(lastKey, description);
            }

            line = lineReader.readLine();
        }
    }

    @Override
    public String getLevelFile(String levelKey) {
        return levelFiles.get(levelKey);
    }

    @Override
    public Map<String, String> getLevels() {
        return this.levelDescription;
    }
}
