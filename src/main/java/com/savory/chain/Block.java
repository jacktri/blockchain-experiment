package com.savory.chain;

import static com.savory.chain.ChainApplication.DIFFICULTY;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.savory.chain.currency.Transaction;

public class Block {

    private String hash;
    private String previousHash;
    public String merkleRoot;
    public List<Transaction> transactions = new ArrayList<>(); //our data will be a simple message.
    private long timeStamp;
    private int count;

    public Block() {
    }

    public Block(String previousHash) {
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash();
    }

    public String calculateHash() {
        String calculatedhash = StringUtil.applySha256(
            previousHash +
                Long.toString(timeStamp) +
                Integer.toString(count) +
                merkleRoot
        );
        return calculatedhash;
    }

    public void mineBlock() {
        merkleRoot = StringUtil.getMerkleRoot(transactions);
        String target = new String(new char[DIFFICULTY]).replace('\0', '0'); //Create a string with difficulty * "0"
        while(!hash.substring( 0, DIFFICULTY).equals(target)) {
            count ++;
            hash = calculateHash();
        }
        System.out.println("Block Mined!!! : " + hash);
    }

    //Add transactions to this block
    public boolean addTransaction(Transaction transaction) {
        //process transaction and check if valid, unless block is genesis block then ignore.
        if(transaction == null) return false;
        if((previousHash != "0")) {
            if((transaction.processTransaction() != true)) {
                System.out.println("Transaction failed to process. Discarded.");
                return false;
            }
        }
        transactions.add(transaction);
        System.out.println("Transaction Successfully added to Block");
        return true;
    }

    public String getHash() {
        return hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String getMerkleRoot() {
        return merkleRoot;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public int getCount() {
        return count;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public void setMerkleRoot(String merkleRoot) {
        this.merkleRoot = merkleRoot;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Block)) {
            return false;
        }
        Block block = (Block) o;
        return timeStamp == block.timeStamp &&
            count == block.count &&
            Objects.equals(hash, block.hash) &&
            Objects.equals(previousHash, block.previousHash) &&
            Objects.equals(merkleRoot, block.merkleRoot) &&
            Objects.equals(transactions, block.transactions);
    }

    @Override
    public int hashCode() {

        return Objects.hash(hash, previousHash, merkleRoot, transactions, timeStamp, count);
    }
}
