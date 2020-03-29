package io;

import geometryprimitives.Point;
import geometryprimitives.Rectangle;
import sprites.Block;
import sprites.Fill;

import java.awt.Color;
import java.util.Map;

public class ParsedBlockCreator implements BlockCreator {
    private static final String HEIGHT = "height";
    private static final String WIDTH = "width";
    private static final String HIT_POINTS = "hit_points";
    private static final String FILL = "fill";
    private static final String FILL_K = "fill-";
    private static final String STROKE = "stroke";

    private Map<String, String> blockDefinitions;

    private int height = 0;
    private int width = 0;
    private int hitPoints = 0;
    private Fill[] fills;
    private Color stroke = null;

    /**
     * Create a parsed block creator.
     * @param blockDefinitions Block defs.
     */
    public ParsedBlockCreator(Map<String, String> blockDefinitions) {
        this.blockDefinitions = blockDefinitions;

        this.height = Integer.valueOf(blockDefinitions.get(HEIGHT));
        this.width = Integer.valueOf(blockDefinitions.get(WIDTH));
        this.hitPoints = Integer.valueOf(blockDefinitions.get(HIT_POINTS));
        this.fills = getFills();
        if (this.blockDefinitions.containsKey(STROKE)) {
            this.stroke = getStroke(blockDefinitions.get(STROKE));
        }
    }

    /**
     * Get stroke.
     * @param colorPattern Color.
     * @return Stroke.
     */
    private Color getStroke(String colorPattern) {
        ColorsParser parser = new ColorsParser();
        Color color = parser.colorFromString(colorPattern);

        return color;
    }

    /**
     * Get fills.
     * @return Fills array.
     */
    private Fill[] getFills() {
        Fill[] fils = new Fill[this.hitPoints];
        if (blockDefinitions.containsKey(FILL)) {
            String normallFillPattern = blockDefinitions.get(FILL);
            Fill normalFill = FillParser.parseFill(normallFillPattern);

            for (int fillIndex = 0; fillIndex < this.hitPoints; fillIndex++) {
                fils[fillIndex] = normalFill;
            }
        }

        for (String key: blockDefinitions.keySet()) {
            if (key.startsWith(FILL_K)) {
                String value = blockDefinitions.get(key);
                Fill fillK = FillParser.parseFill(value);

                int fillIndex = Integer.valueOf(key.split(FILL_K)[1]) - 1;
                fils[fillIndex] = fillK;
            }
        }

        return fils;
    }

    @Override
    public Block create(int xpos, int ypos) {
        Rectangle rect = new Rectangle(new Point(xpos, ypos), this.width, this.height);
        Block block = new Block(rect, this.fills, this.stroke, this.hitPoints);

        return block;
    }
}
