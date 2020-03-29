package io;
import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a color parser.
 */
public class ColorsParser {
    private static final String RGB = "RGB";
    private static final int RED = 1;
    private static final int GREEN = 2;
    private static final int BLUE = 3;
    private static final int START = 6;
    private static final int END = -1;

    /**
     * Get a color from string.
     * @param s String.
     * @return Color.
     */
    public java.awt.Color colorFromString(String s) {
        Color color;
        s = StringSlicer.sliceRange(s, START, END);
        if (s.contains(RGB)) {
            color = parseRGB(s);
        } else {
            color = readNonRGB(s);
        }
        return color;
    }

    /**
     * Get a non RGB color.
     * @param s String.
     * @return Non RGB color.
     */
    private Color readNonRGB(String s) {
        switch (s) {
            case "black" : return Color.BLACK;
            case "blue" : return Color.BLUE;
            case "cyan" : return Color.CYAN;
            case "darkGray" : return Color.DARK_GRAY;
            case "gray" : return Color.GRAY;
            case "green" : return Color.GREEN;
            case "lightGray" : return Color.LIGHT_GRAY;
            case "magenta" : return Color.MAGENTA;
            case "orange" : return Color.ORANGE;
            case "pink" : return Color.PINK;
            case "red" : return Color.RED;
            case "white" : return Color.WHITE;
            case "yellow" : return Color.YELLOW;
            default: return null;
        }
    }

    /**
     * Get RGB color.
     * @param s String.
     * @return Color.
     */
    private Color parseRGB(String s) {
        Pattern c = Pattern.compile(RGB + " *\\( *([0-9]+), *([0-9]+), *([0-9]+) *\\)");
        Matcher m = c.matcher(s);

        if (m.matches()) {
            return new Color(Integer.valueOf(m.group(RED)),
                    Integer.valueOf(m.group(GREEN)),
                    Integer.valueOf(m.group(BLUE)));
        }

        return null;
    }
}
