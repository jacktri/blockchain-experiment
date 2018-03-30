package com.savory.chain;

import java.util.Date;
import java.util.Objects;

public class Block {

    private String hash;
    private String previousHash;
    private String data;
    private long timeStamp;
    private int count;

    public Block() {
    }

    public Block(String data, String previousHash) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash();
    }

    public String calculateHash() {
        String calculatedhash = StringUtil.applySha256(
            previousHash +
                Long.toString(timeStamp) +
                Integer.toString(count) +
                data
        );
        return calculatedhash;
    }

    public String getHash() {
        return hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String getData() {
        return data;
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

    public void setData(String data) {
        this.data = data;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void mineBlock(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0'); //Create a string with difficulty * "0"
        while(!hash.substring( 0, difficulty).equals(target)) {
            count ++;
            hash = calculateHash();
        }
        System.out.println("Block Mined!!! : " + hash);
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
            Objects.equals(data, block.data);
    }

    @Override
    public int hashCode() {

        return Objects.hash(hash, previousHash, data, timeStamp, count);
    }
}
