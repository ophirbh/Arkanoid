package driver;

import java.util.Comparator;

/**
 * Represents a scores information comparator.
 */
public class ScoresInfoComparator implements Comparator<ScoreInfo> {

    @Override
    public int compare(ScoreInfo score1, ScoreInfo score2) {
        if (score1.getScore() < score2.getScore()) {
            return -1;
        } else if (score1.getScore() == score2.getScore()) {
            return 0;
        } else {
            return 1;
        }
    }
}
