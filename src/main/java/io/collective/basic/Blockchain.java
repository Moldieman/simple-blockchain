package io.collective.basic;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Implement a BlockChain
 */
public class Blockchain {
    private final ArrayList<Block> blocks;

    /**
     * Initialize the list of blocks
     */
    public Blockchain() {
        blocks = new ArrayList<>();
    }

    /**
     * Validate if the blockchain isEmpty
     */
    public boolean isEmpty() {
        return blocks.isEmpty();
    }

    /**
     * Add block to the chain
     */
    public void add(Block block) {
        blocks.add(block);
    }

    /**
     * Block size
     */
    public int size() {
        return blocks.size();
    }

    /**
     * Validate the Block
     */
    public boolean isValid() throws NoSuchAlgorithmException {

        // Check an empty chain
        if (blocks.isEmpty())
            return true;

        // Check a chain of one
        String previousHash = blocks.get(0).getPreviousHash();
        if (!previousHash.equals(Block.STARTING_PREVIOUS_HASH))
            return false;

        // Check a chain of many
        for (Block block : blocks) {
            if (!isMined(block) || !block.getHash().equals(block.calculatedHash())
                    || !block.getPreviousHash().equals(previousHash))
                return false;
            previousHash = block.getHash();
        }

        return true;
    }

    /**
     * Mine the block
     */
    public static Block mine(Block block) throws NoSuchAlgorithmException {
        Block mined = new Block(block.getPreviousHash(), block.getTimestamp(), block.getNonce());

        while (!isMined(mined)) {
            mined = new Block(mined.getPreviousHash(), mined.getTimestamp(), mined.getNonce() + 1);
        }
        return mined;
    }

    /**
     * If the block was mined
     */
    public static boolean isMined(Block minedBlock) {
        return minedBlock.getHash().startsWith("00");
    }
}
