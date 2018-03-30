package com.savory.chain;

import java.util.List;

public class BlockChainValidator {

    public static Boolean isChainValid(int difficulty,  List<Block> blockChain) {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        //loop through blockchain to check hashes:
        for(int i=1; i < blockChain.size(); i++) {
            currentBlock = blockChain.get(i);
            previousBlock = blockChain.get(i-1);
            //compare registered hash and calculated hash:
            if(!currentBlock.getHash().equals(currentBlock.calculateHash()) ){
                System.out.println("Current Hashes not equal");
                return false;
            }
            //compare previous hash and registered previous hash
            if(!previousBlock.getHash().equals(previousBlock.getHash()) ) {
                System.out.println("Previous Hashes not equal");
                return false;
            }
            if(!currentBlock.getHash().substring( 0, difficulty).equals(hashTarget)) {
                System.out.println("This block hasn't been mined");
                return false;
            }
        }
        return true;
    }
}
