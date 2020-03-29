package io;

import java.util.Map;

/**
 * Represents a level set.
 */
public interface LevelSet {
    /**
     * Get level file.
     * @param levelKey The level key.
     * @return The level file.
     */
    String getLevelFile(String levelKey);

    /**
     * Get levels.
     * @return The levels.
     */
    Map<String, String> getLevels();
}
