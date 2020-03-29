package driver;

import java.io.File;
import java.io.Serializable;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a high scores table.
 */
public class HighScoresTable implements Serializable {
    private static final int TABLE_SIZE = 10;
    private ArrayList<ScoreInfo> highScoresTable;
    private int tableSize;
    public static final String FILE_NAME = "highscores";

    /**
     * Create an empty high-scores table with the specified size.
     * The size means that the table holds up to size top scores.
     * @param size The size means that the table holds up to size top scores.
     */
    public HighScoresTable(int size) {
        this.highScoresTable = new ArrayList<ScoreInfo>(size);
        this.tableSize = size;
    }

    /**
     * Default constructor.
     */
    public HighScoresTable() {
        this(TABLE_SIZE);
    }

    /**
     * Add a high-score.
     * @param score The score to add.
     */
    public void add(ScoreInfo score) {
        if (this.getRank(score.getScore()) <= this.tableSize) {
            if (this.highScoresTable.size() < this.tableSize) {
                this.highScoresTable.add(score);
            } else {
                this.highScoresTable.remove(this.tableSize - 1);
                this.highScoresTable.add(score);
            }
        }
        this.sortFromHighToLow();
    }

    /**
     * Return table size.
     * @return The table's size.
     */
    public int size() {
        return this.tableSize;
    }

    /**
     * Return the current high scores.
     * The list is sorted such that the highest
     * scores come first.
     * @return The current high scores list.
     */
    public List<ScoreInfo> getHighScores() {
        ArrayList<ScoreInfo> tempTable = new ArrayList<ScoreInfo>();
        for (ScoreInfo info : this.highScoresTable) {
            tempTable.add(info.clone());
        }
        return tempTable;
    }

    /**
     * Return the rank of the current score: where will it
     * be on the list if added?
     * Rank 1 means the score will be highest on the list.
     * Rank `size` means the score will be lowest.
     * Rank > `size` means the score is too low and will not
     * be added to the list.
     * @param score Current score.
     * @return The rank of the current score.
     */
    public int getRank(int score) {
        int cnt = 1;
        for (ScoreInfo info : this.highScoresTable) {
            if (info.getScore() >= score) {
                cnt += 1;
            }
        }
        return cnt;
    }

    /**
     * Clears the table.
     */
    public void clear() {
        this.highScoresTable = new ArrayList<ScoreInfo>();
    }

    /**
     * Load table data from file.
     * Current table data is cleared.
     * @param filename The file name.
     * @throws IOException Input output exception.
     */
    public void load(File filename) throws IOException {
        FileInputStream fileIn = null;
        ObjectInputStream inStream = null;
        try {
            fileIn = new FileInputStream(filename.getName());
            inStream = new ObjectInputStream(fileIn);
            HighScoresTable tempTable = (HighScoresTable) inStream.readObject();
            this.clear();
            this.tableSize = tempTable.size();
            for (ScoreInfo info : tempTable.getHighScores()) {
                this.add(info);
            }
            this.sortFromHighToLow();
        } catch (FileNotFoundException e) {
            save(filename);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (inStream != null) {
                inStream.close();
            }
            if (fileIn != null) {
                fileIn.close();
            }
        }
    }

    /**
     * Save table data to the specified file.
     * @param filename The file name.
     * @throws IOException Input output exception.
     */
    public void save(File filename) throws IOException {
        FileOutputStream fileOut = null;
        ObjectOutputStream outStream = null;
        try {
            fileOut = new FileOutputStream(filename.getName());
            outStream = new ObjectOutputStream(fileOut);
            outStream.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outStream != null) {
                outStream.close();
            }
            if (fileOut != null) {
                fileOut.close();
            }
        }
    }

    /**
     * Read a table from file and return it.
     * If the file does not exist, or there is a problem with
     * reading it, an empty table is returned.
     * @param filename The file name.
     * @return The table.
     */
    public static HighScoresTable loadFromFile(File filename) {
        HighScoresTable table = new HighScoresTable();
        try {
            table.load(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return table;
    }

    /**
     * Sort the high scores table from high to low.
     */
    public void sortFromHighToLow() {
        ScoresInfoComparator comp = new ScoresInfoComparator();
        this.highScoresTable.sort(comp);
        Collections.reverse(this.highScoresTable);
    }
}
