package io;

import sprites.Block;

import java.util.Map;


/**
 * Represents a blocks from symbols factory.
 */
public class BlocksFromSymbolsFactory {
    private Map<String, BlockCreator> blockCreators;
    private Map<String, Integer> spacerWidths;

    /**
     * Constructor.
     * @param blockCreators  Block creators.
     * @param spacerWidths Spacer widths.
     */
    public BlocksFromSymbolsFactory(Map<String, BlockCreator> blockCreators, Map<String, Integer> spacerWidths) {
        this.blockCreators = blockCreators;
        this.spacerWidths = spacerWidths;
    }

    /**
     * returns true if 's' is a valid space symbol.
     * @param s String.
     * @return True if 's' is a valid space symbol.
     */
    public boolean isSpaceSymbol(String s) {
        return this.spacerWidths.containsKey(s);
    }

    /**
     * returns true if 's' is a valid block symbol.
     * @param s String.
     * @return True if 's' is a valid block symbol.
     */
    public boolean isBlockSymbol(String s) {
        return this.blockCreators.containsKey(s);
    }

    /**
     * Return a block according to the definitions associated
     * with symbol s. The block will be located at position (xpos, ypos).
     * @param s String.
     * @param xpos X position.
     * @param ypos Y position.
     * @return Block.
     */
    public Block getBlock(String s, int xpos, int ypos) {
        return blockCreators.get(s).create(xpos, ypos);
    }

    /**
     * Returns the width in pixels associated with the given spacer-symbol.
     * @param s Symbol.
     * @return Width.
     */
    public int getSpaceWidth(String s) {
        return this.spacerWidths.get(s);
    }
}
