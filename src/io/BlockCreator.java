package io;

import sprites.Block;

public interface BlockCreator {
    /**
     * Create a block at the specified location.
     * @param xpos X position.
     * @param ypos X position.
     * @return Block.
     */
    Block create(int xpos, int ypos);
}
