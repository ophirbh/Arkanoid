package io;

import sprites.Fill;

import java.awt.Color;

/**
 * Represents a fill parser.
 */
public class FillParser {
    private static final String IMAGE_FILL = "image";
    private static final String COLOR_FILL = "color";

    /**
     * Parse fill.
     * @param fillPattern The fill pattern.
     * @return Parsed fill.
     */
    public static Fill parseFill(String fillPattern) {
        Fill fill = null;

        if (fillPattern.startsWith(COLOR_FILL)) {
            ColorsParser parser = new ColorsParser();
            Color color = parser.colorFromString(fillPattern);
            fill = new Fill(color);
        } else if (fillPattern.startsWith(IMAGE_FILL)) {
            String fileNamePattern = fillPattern.split(IMAGE_FILL, 2)[1];
            String imageFileName = fileNamePattern.substring(1, fileNamePattern.length() - 1);

            fill = new Fill(imageFileName);
        }

        return fill;
    }



}

